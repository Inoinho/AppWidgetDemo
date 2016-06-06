package kr.goinho.appwidgetdemo;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AppWidgetReceiver extends AppWidgetProvider {

    private static final String ACTION_UPDATE = "kr.goinho.appwidgetdemo.action.WIDGET_UPDATE";
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public AppWidgetReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if(ACTION_UPDATE.equals(intent.getAction())) {
            Log.d("inho", "ACTION_UPDATE");
            update(context);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d("inho", "onUpdate");
        final int count = appWidgetIds.length;

        for(int i=0 ; i<count ; i++) {
            int appWidgetId = appWidgetIds[i];

            Intent intent = new Intent(context, AppWidgetConfigure.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.appwidget);
            views.setOnClickPendingIntent(R.id.btn_setting, pendingIntent);
            views.setOnClickPendingIntent(R.id.btn_refresh, getBroadcasePedingIntent(context, ACTION_UPDATE));

            appWidgetManager.updateAppWidget(appWidgetId, views);
            updateAppWidget(context, appWidgetManager, appWidgetId, views);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        Log.d("inho", "onDeleted");
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        Log.d("inho", "onEnabled");
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        Log.d("inho", "onDisabled");
        super.onDisabled(context);
    }

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, RemoteViews updateViews) {
        if(updateViews == null) {
            updateViews = new RemoteViews(context.getPackageName(), R.layout.appwidget);
        }

        updateViews.setTextViewText(R.id.tv_time, getCurrentTime());
        appWidgetManager.updateAppWidget(appWidgetId, updateViews);
    }

    private static String getCurrentTime() {
        return dateFormat.format(new Date());
    }

    private void update(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisAppWidget = new ComponentName(context.getPackageName(), getClass().getName());
        int ids[] = appWidgetManager.getAppWidgetIds(thisAppWidget);
        for(int appWidgetId : ids) {
            updateAppWidget(context, appWidgetManager, appWidgetId, null);
        }
    }

    private PendingIntent getBroadcasePedingIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }
}
