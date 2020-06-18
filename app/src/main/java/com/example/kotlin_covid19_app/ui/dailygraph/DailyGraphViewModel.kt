package com.example.kotlin_covid19_app.ui.dailygraph

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.kotlin_covid19_app.data.mapper.CovidDailyDataMapper
import com.example.kotlin_covid19_app.data.model.CovidDaily
import com.example.kotlin_covid19_app.data.repository.Repository
import com.example.kotlin_covid19_app.ui.adapter.viewholder.DailyItem
import com.example.kotlin_covid19_app.ui.base.BaseViewModel
import com.example.kotlin_covid19_app.util.SingleLiveEvent
import com.example.kotlin_covid19_app.util.ext.addTo
import com.example.kotlin_covid19_app.util.rx.SchedulerProvider

class DailyGraphViewModel(
    private val appRepository: Repository,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {
    private val _toastMessage = SingleLiveEvent<String>()
    val toastMessage: LiveData<String>
        get() = _toastMessage

    private val _loading = SingleLiveEvent<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _dailyItems = MutableLiveData<List<CovidDaily>>()
    val dailyItems: LiveData<List<CovidDaily>>
        get() = _dailyItems
    val dailyItemsVH: LiveData<List<DailyItem>>
        get() = Transformations.map(_dailyItems) {
            CovidDailyDataMapper.transform(it)
        }

    fun loadCacheDailyData() {
        /*Assume daily data just got fresh data from remote api on previous page
          for UX Purpose, we directly load cache
        */
        _dailyItems.postValue(appRepository.getCacheDaily().orEmpty())
    }

    fun loadRemoteDailyData() {
        appRepository.daily().subscribe({ response ->
            _loading.postValue(false)
            response.data?.let {
                _dailyItems.postValue(it)
            }
        }, {
            _loading.postValue(false)

        }).addTo(compositeDisposable)
    }
}
