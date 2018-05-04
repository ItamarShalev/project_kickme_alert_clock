package alert.kickme.com.kickme_alert_clock.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import alert.kickme.com.kickme_alert_clock.data.CharacterData;
import alert.kickme.com.kickme_alert_clock.global.Helper;
import alert.kickme.com.kickme_alert_clock.utils.AlertClockManger;
import alertData.kickme.com.kickme_alert_clock.R;

public class AdapterCharacterRecycler extends RecyclerView.Adapter<AdapterCharacterRecycler.CharacterHolder> {

    private Context context;
    private List<CharacterData> characterDataList;
    private LayoutInflater inflater;
    private AlertClockManger alertClockManger;


    /**
     * Listener to icon clicked
     */
    private View.OnClickListener rowItemViewClickedListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // TODO: 29/04/2018  animation gif and sound
        }
    };

    public AdapterCharacterRecycler(Context context, List<CharacterData> characterDataList) {
        this.context = context;
        this.characterDataList = characterDataList;
        alertClockManger = new AlertClockManger(context);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CharacterHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        View rowItemView = inflater.inflate(R.layout.row_data_alert, parent, false);
        CharacterHolder characterHolder = new CharacterHolder(rowItemView);
        rowItemView.setOnClickListener(rowItemViewClickedListener);
        return characterHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterHolder holder, int position) {
        CharacterData characterData = characterDataList.get(position);
        int id = characterData.getPrimaryId();
        holder.rowItemView.setTag(id);
        holder.nameTextView.setText(characterData.getName());

        // If have no gif image so visible lock
        if (characterData.getGifImage() != null) {
            Helper.Image.makeImageBlackAndWhite(holder.iconImageView);
            holder.lockImageView.setVisibility(View.VISIBLE);
        } else {
            holder.iconImageView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return characterDataList.size();
    }

    @Override
    public long getItemId(int position) {
        return characterDataList.get(position).getPrimaryId();
    }

    static class CharacterHolder extends RecyclerView.ViewHolder {

        private ImageView iconImageView;
        private ImageView lockImageView;
        private TextView nameTextView;
        private View rowItemView;

        CharacterHolder(View rowItemView) {
            super(rowItemView);
            this.rowItemView = rowItemView;
            iconImageView = rowItemView.findViewById(R.id.icon_image_view);
            lockImageView = rowItemView.findViewById(R.id.lock_image_view);
            nameTextView = rowItemView.findViewById(R.id.name_text_view);
        }


    }
}
