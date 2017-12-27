package app.codekiller.com.newsapp.UI.settings;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import app.codekiller.com.newsapp.R;

/**
 * Created by Lollipop on 2017/12/27.
 */

public class AutoThemeDialog extends AlertDialog {
    private TimePicker timePickerDay;
    private TimePicker timePickerNight;
    private TextView textDay;
    private TextView textNight;
    private Button button;

    private Context context;
    private SettingsContract.Presenter presenter;

    private int day = 1;
    private int night = 0;

    protected AutoThemeDialog(@NonNull Context context, SettingsContract.Presenter presenter) {
        super(context);
        this.context = context;
        this.presenter = presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auto_theme_dialog);
        initViews();
    }

    private void initViews() {
        textDay = findViewById(R.id.text_day);
        textNight = findViewById(R.id.text_night);
        timePickerDay = findViewById(R.id.time_picker_day);
        timePickerNight = findViewById(R.id.time_picker_night);
        button = findViewById(R.id.ok);

        textDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (day == 1){
                    day = 0;
                    night = 1;
                    textDay.setCompoundDrawablesWithIntrinsicBounds(null, null, getContext().getDrawable(R.drawable.ic_keyboard_arrow_left_white_24px), null);
                    textNight.setCompoundDrawablesWithIntrinsicBounds(null, null, getContext().getDrawable(R.drawable.ic_keyboard_arrow_down_white_24px), null);
                    timePickerDay.setVisibility(View.GONE);
                    timePickerNight.setVisibility(View.VISIBLE);
                }else {
                    day = 1;
                    night = 0;
                    textDay.setCompoundDrawablesWithIntrinsicBounds(null, null, getContext().getDrawable(R.drawable.ic_keyboard_arrow_down_white_24px), null);
                    textNight.setCompoundDrawablesWithIntrinsicBounds(null, null, getContext().getDrawable(R.drawable.ic_keyboard_arrow_left_white_24px), null);
                    timePickerDay.setVisibility(View.VISIBLE);
                    timePickerNight.setVisibility(View.GONE);
                }
            }
        });
        textNight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (night == 1){
                    day = 1;
                    night = 0;
                    textDay.setCompoundDrawablesWithIntrinsicBounds(null, null, getContext().getDrawable(R.drawable.ic_keyboard_arrow_down_white_24px), null);
                    textNight.setCompoundDrawablesWithIntrinsicBounds(null, null, getContext().getDrawable(R.drawable.ic_keyboard_arrow_left_white_24px), null);
                    timePickerDay.setVisibility(View.VISIBLE);
                    timePickerNight.setVisibility(View.GONE);
                }else {
                    day = 0;
                    night = 1;
                    textDay.setCompoundDrawablesWithIntrinsicBounds(null, null, getContext().getDrawable(R.drawable.ic_keyboard_arrow_left_white_24px), null);
                    textNight.setCompoundDrawablesWithIntrinsicBounds(null, null, getContext().getDrawable(R.drawable.ic_keyboard_arrow_down_white_24px), null);
                    timePickerDay.setVisibility(View.GONE);
                    timePickerNight.setVisibility(View.VISIBLE);
                }
            }
        });

        timePickerDay.setIs24HourView(true);
        timePickerNight.setIs24HourView(true);
        if (Build.VERSION.SDK_INT >= 23){
            timePickerDay.setHour(7);
            timePickerDay.setMinute(0);
            timePickerNight.setHour(20);
            timePickerNight.setMinute(0);
        }else {
            timePickerDay.setCurrentHour(7);
            timePickerDay.setCurrentMinute(0);
            timePickerNight.setCurrentHour(20);
            timePickerNight.setCurrentMinute(0);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int dayHour;
                int dayMinute;
                int nightHour;
                int nightMinute;
                if (Build.VERSION.SDK_INT >= 23) {
                    dayHour = timePickerDay.getHour();
                    dayMinute = timePickerDay.getMinute();
                    nightHour = timePickerNight.getHour();
                    nightMinute = timePickerNight.getMinute();
                } else {
                    dayHour = timePickerDay.getCurrentHour();
                    dayMinute = timePickerDay.getCurrentMinute();
                    nightHour = timePickerNight.getCurrentHour();
                    nightMinute = timePickerNight.getCurrentMinute();
                }
                Calendar calendarDay = Calendar.getInstance();
                calendarDay.set(Calendar.HOUR_OF_DAY, dayHour);
                calendarDay.set(Calendar.MINUTE, dayMinute);
                calendarDay.set(Calendar.SECOND, 0);
                long dayTime = calendarDay.getTimeInMillis();
                Calendar calendarNight = Calendar.getInstance();
                calendarNight.set(Calendar.HOUR_OF_DAY, nightHour);
                calendarNight.set(Calendar.MINUTE, nightMinute);
                calendarNight.set(Calendar.SECOND, 0);
                long nightTime = calendarNight.getTimeInMillis();

                presenter.autoThemeTimePicked(dayTime, nightTime);
            }
        });
    }
}
