package alert.kickme.com.kickme_alert_clock.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import java.util.Calendar;

import alertData.kickme.com.kickme_alert_clock.R;


public class TimePickerFragment extends Fragment{


    private static final int MAX_VALUE_HOUR = 23;
    private static final int MAX_VALUE_MINUTE = 59;
    private static final int MIN_VALUE = 0;
    private TimeListener timeListener;
    private int hour;
    private int minute;


    public TimeListener getTimeListener() {
        return timeListener;
    }

    public void setTimeListener(TimeListener timeListener) {
        this.timeListener = timeListener;
    }



    public interface TimeListener{
        void onUpdateTime(int hour, int minute);
    }


    private void updateListener(){
        if (timeListener != null){
            timeListener.onUpdateTime(hour,minute);
        }
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_time_picker,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        NumberPicker minuteNumberPicker = view.findViewById(R.id.minute_number_picker);
        NumberPicker hourNumberPicker = view.findViewById(R.id.hour_number_picker);

        minute = calendar.get(Calendar.MINUTE) +1;
        initNumberPicker(minuteNumberPicker,MAX_VALUE_MINUTE,MIN_VALUE,minute);
        hour = calendar.get(Calendar.HOUR);
        initNumberPicker(hourNumberPicker,MAX_VALUE_HOUR,MIN_VALUE,hour);

        hourNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                TimePickerFragment.this.hour = newVal;
                updateListener();
            }
        });
        minuteNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                minute = newVal;
                updateListener();
            }
        });
    }

    private void initNumberPicker(NumberPicker numberPicker, int maxValue, int minValue, int value){
        numberPicker.setMaxValue(maxValue);
        numberPicker.setMinValue(minValue);
        numberPicker.setValue(value);
    }





}
