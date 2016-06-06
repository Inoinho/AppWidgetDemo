package kr.goinho.appwidgetdemo;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

public class AppWidgetConfigure extends AppCompatActivity implements View.OnClickListener {

    private int mAppWidgetId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(RESULT_CANCELED);
        setContentView(R.layout.activity_app_widget_configure);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        ((Button)findViewById(R.id.btn_confirm)).setOnClickListener(this);
        ((Button)findViewById(R.id.btn_cancel)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch(id) {
            case R.id.btn_confirm:
                Log.d("inho", "confirm");
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
                RemoteViews views = new RemoteViews(this.getPackageName(), R.layout.appwidget);
                appWidgetManager.updateAppWidget(mAppWidgetId, views);
                Intent resultValue = new Intent();
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                setResult(RESULT_OK, resultValue);
                finish();
                break;
            case R.id.btn_cancel:
                finish();
                break;
        }
    }

    @Override
    public void finish() {
        //do something
        super.finish();
    }
}
