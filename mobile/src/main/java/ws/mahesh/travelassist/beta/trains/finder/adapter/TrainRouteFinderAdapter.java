package ws.mahesh.travelassist.beta.trains.finder.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import ws.mahesh.travelassist.beta.R;
import ws.mahesh.travelassist.beta.trains.finder.TrainsRouteViewActivity;
import ws.mahesh.travelassist.beta.trains.finder.models.TrainFinderObject;
import ws.mahesh.travelassist.beta.trains.schedule.TrainSchedulesActivity;

/**
 * Created by mahesh on 25/03/15.
 */
public class TrainRouteFinderAdapter extends RecyclerView.Adapter<TrainRouteFinderAdapter.TrainViewHolder> {
    List<TrainFinderObject> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;

    public TrainRouteFinderAdapter(Context context, List<TrainFinderObject> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public TrainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_list_bus, parent, false);
        TrainViewHolder holder = new TrainViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(TrainViewHolder holder, int position) {
        TrainFinderObject current = data.get(position);
        holder.busNo.setText(current.getRoute());
        holder.stop1.setText(current.getLinecode());
        holder.ETA.setText(current.getEta());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class TrainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView busNo, ETA, stop1;

        public TrainViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            busNo = (TextView) itemView.findViewById(R.id.textViewBus1);
            ETA = (TextView) itemView.findViewById(R.id.textViewETA);
            stop1 = (TextView) itemView.findViewById(R.id.textViewStop1);
        }

        @Override
        public void onClick(View view) {
            if (!data.get(getAdapterPosition()).getRoute().equals("Direct Train")) {
                Intent i = new Intent(context, TrainsRouteViewActivity.class);
                i.putExtra("id", data.get(getAdapterPosition()).getId());
                context.startActivity(i);
            } else {
                Intent i = new Intent(context, TrainSchedulesActivity.class);
                i.putExtra("src", data.get(getAdapterPosition()).getSrc());
                i.putExtra("dest", data.get(getAdapterPosition()).getDest());
                i.putExtra("line", data.get(getAdapterPosition()).getLineId());
                i.putExtra("route", data.get(getAdapterPosition()).getRoute());
                context.startActivity(i);
            }
        }
    }
}

