package ws.mahesh.travelassist.beta.bus.finder.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import ws.mahesh.travelassist.beta.R;
import ws.mahesh.travelassist.beta.bus.finder.models.Bus2Object;

/**
 * Created by mahesh on 13/03/15.
 */
public class Bus2ListAdapter extends RecyclerView.Adapter<Bus2ListAdapter.Bus2ViewHolder> {
    List<Bus2Object> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;

    public Bus2ListAdapter(Context context, List<Bus2Object> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public Bus2ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_list_bus, parent, false);
        Bus2ViewHolder holder = new Bus2ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Bus2ViewHolder holder, int position) {
        Bus2Object current = data.get(position);
        holder.bus1.setText(current.getBus1() + " - " + current.getBus2());
        holder.stop1.setText("Via - " + current.getStop());
        holder.ETA.setText(current.getEta1() + "-" + current.getEta2());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Bus2ViewHolder extends RecyclerView.ViewHolder {
        TextView bus1, stop1, ETA;

        public Bus2ViewHolder(View itemView) {
            super(itemView);
            bus1 = (TextView) itemView.findViewById(R.id.textViewBus1);
            stop1 = (TextView) itemView.findViewById(R.id.textViewStop1);
            ETA = (TextView) itemView.findViewById(R.id.textViewETA);
        }
    }
}

