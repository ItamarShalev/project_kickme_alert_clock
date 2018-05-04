package alert.kickme.com.kickme_alert_clock.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

import alert.kickme.com.kickme_alert_clock.adapter.AdapterAlertRecycler;
import alert.kickme.com.kickme_alert_clock.data.AlertData;
import alert.kickme.com.kickme_alert_clock.utils.AlertClockManger;
import alertData.kickme.com.kickme_alert_clock.R;


public class ListAlertFragment extends Fragment {

    private RecyclerView listAlertRecycler;
    private AdapterAlertRecycler adapterAlertRecycler;
    private AlertClockManger alertClockManger;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_alert, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Context context = view.getContext();
        alertClockManger = new AlertClockManger(context);
        adapterAlertRecycler = new AdapterAlertRecycler(context, getAllAlert());
        listAlertRecycler = view.findViewById(R.id.list_alert_recycler);
        listAlertRecycler.setAdapter(adapterAlertRecycler);
        listAlertRecycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        listAlertRecycler.setHasFixedSize(true);
    }


    private List<AlertData> getAllAlert() {
        List<AlertData> alertDataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            alertDataList.add(new AlertData());
        }

        return alertDataList;

    }

}
