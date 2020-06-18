package com.example.kotlin_covid19_app.ui.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.Toast
import com.example.kotlin_covid19_app.R
import com.example.kotlin_covid19_app.data.mapper.CovidDataMapper
import com.example.kotlin_covid19_app.data.model.CovidDetail
import com.example.kotlin_covid19_app.data.repository.Repository
import com.example.kotlin_covid19_app.util.NumberUtils
import com.example.kotlin_covid19_app.util.rx.SchedulerProvider
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * rizmaulana 22/03/20.
 */
class LocationWidget : AppWidgetProvider(), KoinComponent {
    private val appRepository by inject<Repository>()
    private val schedulerProvider by inject<SchedulerProvider>()

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        appRepository.getCachePinnedRegion()?.let {
            updateWidget(context, appWidgetManager, appWidgetIds, it)
        }
    }

    private fun updateWidget(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?,
        covidDetail: CovidDetail
    ) {
        appWidgetIds?.forEachIndexed { index, i ->
            val appWidgetId = appWidgetIds[index]
            val intent = Intent(context, javaClass).apply {
                action = UPDATE
            }
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
            val views = RemoteViews(context?.packageName, R.layout.item_widget_pinned)
            views.setTextViewText(R.id.txt_location, covidDetail.locationName)
            views.setTextViewText(
                R.id.txt_update,
                context?.getString(
                    R.string.information_last_update,
                    NumberUtils.formatTime(covidDetail.lastUpdate)
                )
            )
            views.setTextViewText(R.id.txt_death, NumberUtils.numberFormat(covidDetail.deaths))
            views.setTextViewText(
                R.id.txt_data,
                NumberUtils.numberFormat(covidDetail.confirmed)
            )
            views.setTextViewText(
                R.id.txt_rcv,
                NumberUtils.numberFormat(covidDetail.recovered)
            )
            views.setOnClickPendingIntent(R.id.img_refresh, pendingIntent)
            appWidgetManager?.updateAppWidget(appWidgetId, views)
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if (intent?.action == UPDATE) {
            appRepository.getCachePinnedRegion()?.let { detail ->
                appRepository.country(detail.iso2.orEmpty())
                    .flatMapCompletable { overview ->
                        val updatedRegion =
                            CovidDataMapper.transformOverviewToUpdatedRegion(detail, overview)
                        return@flatMapCompletable appRepository.putPinnedRegion(updatedRegion)
                            .subscribeOn(schedulerProvider.ui())
                    }
                    .observeOn(schedulerProvider.ui())
                    .doOnSubscribe { showToast(context, R.string.update_widget_start) }
                    .subscribe({
                        showToast(context, R.string.update_widget_success)
                        updateData(context)
                    }, {
                        showToast(context, R.string.update_widget_fail)
                        it.printStackTrace()
                    }
                    )
            }
        }
    }

    private fun showToast(context: Context?, msg: Int) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    private fun updateData(context: Context?) {
        val appWidgetManager = AppWidgetManager.getInstance(context);
        val thisAppWidgetComponentName =
            ComponentName(context?.packageName.orEmpty(), javaClass.name)
        val appWidgetIds = appWidgetManager.getAppWidgetIds(
            thisAppWidgetComponentName
        )
        onUpdate(context, appWidgetManager, appWidgetIds);
    }

    companion object {
        const val UPDATE = "id.rizmaulana.covid19.ui.widget.UPDATE"
    }

}