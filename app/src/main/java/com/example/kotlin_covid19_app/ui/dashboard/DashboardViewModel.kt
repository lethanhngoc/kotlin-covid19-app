package com.example.kotlin_covid19_app.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlin_covid19_app.R
import com.example.kotlin_covid19_app.data.mapper.CovidDailyDataMapper
import com.example.kotlin_covid19_app.data.mapper.CovidOverviewDataMapper
import com.example.kotlin_covid19_app.data.mapper.CovidPinnedDataMapper
import com.example.kotlin_covid19_app.data.model.CovidDaily
import com.example.kotlin_covid19_app.data.model.CovidDetail
import com.example.kotlin_covid19_app.data.model.CovidOverview
import com.example.kotlin_covid19_app.data.repository.Repository
import com.example.kotlin_covid19_app.data.repository.Result
import com.example.kotlin_covid19_app.ui.adapter.viewholder.ErrorStateItem
import com.example.kotlin_covid19_app.ui.adapter.viewholder.LoadingStateItem
import com.example.kotlin_covid19_app.ui.adapter.viewholder.TextItem
import com.example.kotlin_covid19_app.ui.base.BaseViewItem
import com.example.kotlin_covid19_app.ui.base.BaseViewModel
import com.example.kotlin_covid19_app.util.Constant
import com.example.kotlin_covid19_app.util.SingleLiveEvent
import com.example.kotlin_covid19_app.util.ext.addTo
import com.example.kotlin_covid19_app.util.rx.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.functions.Function3

class DashboardViewModel(
    private val appRepository: Repository,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _toastMessage = SingleLiveEvent<String>()
    val toastMessage: LiveData<String>
        get() = _toastMessage

    private val _liveItems = MutableLiveData<List<BaseViewItem>>()
    val items: LiveData<List<BaseViewItem>>
        get() = _liveItems

    private fun showLoadingState(){
        if(_liveItems.value?.isEmpty() == null ||
            _liveItems.value?.firstOrNull { it is ErrorStateItem } != null){
            _liveItems.value = listOf(LoadingStateItem())
        }
    }

    private fun showErrorState(throwable: Throwable){
        _loading.value = false
        if(_liveItems.value?.isEmpty() == null ||
            _liveItems.value?.firstOrNull { it is ErrorStateItem || it is LoadingStateItem } != null){
            _liveItems.value = listOf(handleThrowable(throwable))
        }
    }

    fun loadDashboard() {
        showLoadingState()

        val overviewObservable = appRepository.overview()
            .observeOn(schedulerProvider.io()) //all stream below will be manage on io thread

        val dailyObservable = appRepository.daily()
            .observeOn(schedulerProvider.io())

        val pinnedObservable = appRepository.pinnedRegion()
            .observeOn(schedulerProvider.io())

        Observable.combineLatest(
            overviewObservable,
            dailyObservable,
            pinnedObservable,
            Function3<Result<CovidOverview>,
                    Result<List<CovidDaily>>,
                    Result<CovidDetail>,
                    Pair<List<BaseViewItem>, Throwable?>> { overview, daily, pinned ->

                val items: MutableList<BaseViewItem> = mutableListOf()
                var currentThrowable: Throwable? = null

                with(overview){
                    items.add(CovidOverviewDataMapper.transform(data))
                    error?.let { currentThrowable = it }
                }

                with(pinned){
                    CovidPinnedDataMapper.transform(data)?.let {
                        items.add(it)
                    }
                    error?.let { currentThrowable = it }
                }

                items.add(TextItem(R.string.per_country))
                items.addAll(appRepository.getPerCountryItem())

                with(daily){
                    val dailies = CovidDailyDataMapper.transform(data)
                    if(dailies.isNotEmpty()) {
                        items.add(TextItem(R.string.daily_updates, R.string.show_graph))
                        items.addAll(dailies)
                    }
                    error?.let { currentThrowable = it }
                }

                return@Function3 items.toList() to currentThrowable
            })
            .observeOn(schedulerProvider.ui()) //go back to ui thread
            .subscribe({ (result, throwable) ->
                _liveItems.postValue(result)

                //For now only check if there is a throwable
                if(throwable != null) _toastMessage.value = Constant.ERROR_MESSAGE
                _loading.value = false
            }, {
                _toastMessage.value = Constant.ERROR_MESSAGE
                showErrorState(it)
            }).addTo(compositeDisposable)
    }
}