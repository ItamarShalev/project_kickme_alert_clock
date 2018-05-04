package alert.kickme.com.kickme_alert_clock.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import alert.kickme.com.kickme_alert_clock.activities.AddAlertActivity;
import alert.kickme.com.kickme_alert_clock.data.AlertData;
import alert.kickme.com.kickme_alert_clock.global.Helper;
import alert.kickme.com.kickme_alert_clock.utils.AlertClockManger;
import alertData.kickme.com.kickme_alert_clock.R;


public class AdapterAlertRecycler extends Adapter<AdapterAlertRecycler.ListAlertHolder> {

    private Context context;
    private List<AlertData> alertDataList;
    private LayoutInflater inflater;
    private AlertClockManger alertClockManger;


    /**
     * Listener to click on the row of row view
     */
    private View.OnClickListener rowItemViewClickedListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, AddAlertActivity.class);
            intent.putExtra(AddAlertActivity.TAG_ALERT_ID, ((int) view.getTag()));
            context.startActivity(intent);
        }
    };

    /**
     * Listener to change on/off on the switch in row view
     */
    private CompoundButton.OnCheckedChangeListener onChangeChecked = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int position = ((int) buttonView.getTag());
            AlertData alertData = alertDataList.get(position);
            alertClockManger.setEnable(alertData,isChecked);
        }
    };


    public AdapterAlertRecycler(Context context, List<AlertData> alertDataList){
        this.context = context;
        this.alertDataList = alertDataList;
        alertClockManger = new AlertClockManger(context);
        inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ListAlertHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        View rowItemView = inflater.inflate(R.layout.row_data_alert,parent,false);
        ListAlertHolder listAlertHolder = new ListAlertHolder(rowItemView);
        rowItemView.setOnClickListener(rowItemViewClickedListener);
        listAlertHolder.onOffAlertSwitch.setOnCheckedChangeListener(onChangeChecked);
        Helper.Image.setImageViewCorner(listAlertHolder.logoCharacterImageView,20);
        return listAlertHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListAlertHolder holder, int position) {
        AlertData alertData = alertDataList.get(position);
        int id = alertData.getPrimaryId();
        holder.rowItemView.setTag(id);
        holder.onOffAlertSwitch.setTag(position);
        holder.onOffAlertSwitch.setChecked(alertData.isEnableAlert());
        Helper.Image.setImageViewCorner(holder.logoCharacterImageView,20);
    }

    @Override
    public int getItemCount() {
        return alertDataList.size();
    }

    @Override
    public long getItemId(int position) {
        return alertDataList.get(position).getPrimaryId();
    }

    static class ListAlertHolder extends RecyclerView.ViewHolder {

        private ImageView logoCharacterImageView;
        private Switch onOffAlertSwitch;
        private TextView daysAlertTextView;
        private TextView alertTimeTextView;
        private View rowItemView;
        ListAlertHolder(View rowItemView) {
            super(rowItemView);
            this.rowItemView = rowItemView;
            logoCharacterImageView = rowItemView.findViewById(R.id.logo_character_image_view);
            onOffAlertSwitch = rowItemView.findViewById(R.id.on_off_alert_switch);
            daysAlertTextView = rowItemView.findViewById(R.id.days_alert_text_view);
            alertTimeTextView = rowItemView.findViewById(R.id.alert_time_text_view);
        }


    }
}
