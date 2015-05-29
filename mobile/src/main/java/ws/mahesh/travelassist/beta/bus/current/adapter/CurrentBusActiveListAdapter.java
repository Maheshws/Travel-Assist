package ws.mahesh.travelassist.beta.bus.current.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ws.mahesh.travelassist.beta.R;
import ws.mahesh.travelassist.beta.bus.StaticBusHolder;
import ws.mahesh.travelassist.beta.bus.buswise.models.BusWiseObject;
import ws.mahesh.travelassist.beta.bus.current.CurrentBusStopsViewActivity;
import ws.mahesh.travelassist.beta.bus.current.StaticBusCurrentHolder;
import ws.mahesh.travelassist.beta.bus.current.models.StopTimeObject;
import ws.mahesh.travelassist.beta.bus.current.models.TripsObject;

/**
 * Created by mahesh on 16/03/15.
 */
public class CurrentBusActiveListAdapter extends RecyclerView.Adapter<CurrentBusActiveListAdapter.Bus1ViewHolder> {
    List<TripsObject> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;
    private List<BusWiseObject> orig;

    public CurrentBusActiveListAdapter(Context context, List<TripsObject> data) {
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
        TripsObject current = data.get(position);
        ArrayList<StopTimeObject> stopTime = current.getStoptime();
        holder.busNo.setText("" + current.getTripID() + " - " + current.getDir());
        holder.stop1.setText("At " + StaticBusHolder.getBusStopName(current.getStoptime().get((stopTime.size() - 1)).getStopId()));
        holder.ETA.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class Bus1ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView busNo, ETA, stop1;

        public Bus1ViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            busNo = (TextView) itemView.findViewById(R.id.textViewBus1);
            ETA = (TextView) itemView.findViewById(R.id.textViewETA);
            stop1 = (TextView) itemView.findViewById(R.id.textViewStop1);
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(context, CurrentBusStopsViewActivity.class);
            i.putExtra("route", data.get(getAdapterPosition()).getBusId());
            i.putExtra("id", data.get(getAdapterPosition()).getTripID());
            i.putExtra("mode", data.get(getAdapterPosition()).getDir());
            StaticBusCurrentHolder.current = data.get(getAdapterPosition());
            context.startActivity(i);
        }
    }
}
