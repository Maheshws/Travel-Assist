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
import ws.mahesh.travelassist.beta.bus.finder.models.Bus3Object;

/**
 * Created by mahesh on 13/03/15.
 */
public class Bus3ListAdapter extends RecyclerView.Adapter<Bus3ListAdapter.Bus3ViewHolder> {
    List<Bus3Object> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;

    public Bus3ListAdapter(Context context, List<Bus3Object> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public Bus3ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_list_bus, parent, false);
        Bus3ViewHolder holder = new Bus3ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Bus3ViewHolder holder, int position) {
        Bus3Object current = data.get(position);
        holder.bus1.setText(current.getBus1() + " - " + current.getBus2() + " - " + current.getBus3());
        holder.stop1.setText("Via - " + current.getStop1() + " - " + current.getStop2());
        holder.ETA.setText(current.getEta1() + "-" + current.getEta2() + "-" + current.getEta3());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Bus3ViewHolder extends RecyclerView.ViewHolder {
        TextView bus1, stop1, ETA;

        public Bus3ViewHolder(View itemView) {
            super(itemView);
            bus1 = (TextView) itemView.findViewById(R.id.textViewBus1);
            stop1 = (TextView) itemView.findViewById(R.id.textViewStop1);
            ETA = (TextView) itemView.findViewById(R.id.textViewETA);
        }
    }
}

