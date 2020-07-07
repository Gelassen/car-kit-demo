package ru.autokit.android.screens.spares;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ru.autokit.android.R;
import ru.autokit.android.model.CarSparesItem;
import ru.autokit.android.model.ViewCarSparesItem;

import java.util.ArrayList;
import java.util.List;

public class SparesAdapter extends RecyclerView.Adapter<SparesAdapter.ViewHolder> {

    public interface Listener {
        void onClick(List<ViewCarSparesItem> model);
    }

    private List<ViewCarSparesItem> model = new ArrayList();
    private Listener listener;

    public SparesAdapter(Listener listener) {
        this.listener = listener;
    }

    public void updateModel(List<ViewCarSparesItem> list) {
        model.clear();
        model.addAll(list);
        notifyDataSetChanged();
    }

    public List<ViewCarSparesItem> getModel() {
        return model;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spares, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        ViewCarSparesItem item = model.get(position);
        holder.name.setText(item.component1().getName());

        Resources res = holder.itemView.getResources();
        CarSparesItem data = item.component1();

        holder.costOrigin.setText(context.getString(R.string.origin_text_fmt,
                res.getQuantityString(R.plurals.summary_cost_fmt, Integer.valueOf(data.getCostOrigin()), Integer.valueOf(data.getCostOrigin())),
                res.getQuantityString(R.plurals.summary_time_fmt, Integer.valueOf(data.getOriginDuration()), Integer.valueOf(data.getOriginDuration()))
                )
        );
        holder.costReplacement.setText(context.getString(R.string.replacement_text_fmt,
                data.getReplacementProduction(),
                res.getQuantityString(R.plurals.summary_cost_fmt, Integer.valueOf(data.getCostReplacement()), Integer.valueOf(data.getCostReplacement())),
                res.getQuantityString(R.plurals.summary_time_fmt, Integer.valueOf(data.getReplacementDuration()), Integer.valueOf(data.getReplacementDuration()))
                )
        );

        holder.originCheckbox.setSelected(item.isOriginSelected());
        holder.replacementCheckbox.setSelected(item.isReplacementSelected());
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        private final TextView costOrigin;
        private final TextView costReplacement;
        private final View originContainer;
        private final View replacementContainer;

        private final CheckBox originCheckbox;
        private final CheckBox replacementCheckbox;

        // color state for selected and unselected modes

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            costOrigin = itemView.findViewById(R.id.costOrigin);
            costReplacement = itemView.findViewById(R.id.costReplacement);
            originContainer = itemView.findViewById(R.id.originContainer);
            replacementContainer = itemView.findViewById(R.id.replacementContainer);
            originCheckbox = itemView.findViewById(R.id.originCheckbox);
            replacementCheckbox = itemView.findViewById(R.id.replacementCheckbox);

            originCheckbox.setClickable(false);
            originCheckbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ViewCarSparesItem item = model.get(getAdapterPosition());
                    item.setOriginSelected(!item.isOriginSelected()); // opposite to current state
                    item.setReplacementSelected(item.isReplacementSelected() ? false : false); // only one is possible

                    originCheckbox.setChecked(item.isOriginSelected());
                    replacementCheckbox.setChecked(item.isReplacementSelected());

                    if (listener != null) listener.onClick(model);
                }
            });

            replacementCheckbox.setClickable(false);
            replacementCheckbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ViewCarSparesItem item = model.get(getAdapterPosition());
                    item.setReplacementSelected(!item.isReplacementSelected()); // opposite to current state
                    item.setOriginSelected(item.isOriginSelected() ? false : false); // only one is possible

                    originCheckbox.setChecked(item.isOriginSelected());
                    replacementCheckbox.setChecked(item.isReplacementSelected());

                    if (listener != null) listener.onClick(model);
                }
            });

        }
    }
}
