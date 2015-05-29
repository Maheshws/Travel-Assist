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
import ws.mahesh.travelassist.beta.bus.finder.models.Bus1Object;

/**
 * Created by mahesh on 13/03/15.
 */
public class Bus1ListAdapter extends RecyclerView.Adapter<Bus1ListAdapter.Bus1ViewHolder> {
    List<Bus1Object> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;

    public Bus1ListAdapter(Context context, List<Bus1Object> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public Bus1ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_list_bus, parent, false);
        Bus1ViewHolder holder = new Bus1ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Bus1ViewHolder holder, int position) {
        Bus1Object current = data.get(position);
        holder.busNo.setText(current.getBusName());
        holder.stop1.setText("Direct Bus");
        holder.ETA.setText(current.getEta());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Bus1ViewHolder extends RecyclerView.ViewHolder {
        TextView busNo, ETA, stop1;

        public Bus1ViewHolder(View itemView) {
            super(itemView);
            busNo = (TextView) itemView.findViewById(R.id.textViewBus1);
            ETA = (TextView) itemView.findViewById(R.id.textViewETA);
            stop1 = (TextView) itemView.findViewById(R.id.textViewStop1);
        }
    }
}

