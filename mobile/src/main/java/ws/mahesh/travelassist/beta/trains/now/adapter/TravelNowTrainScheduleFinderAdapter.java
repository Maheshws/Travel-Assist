package ws.mahesh.travelassist.beta.trains.now.adapter;

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
import ws.mahesh.travelassist.beta.trains.TrainStaticHolder;
import ws.mahesh.travelassist.beta.trains.now.model.TravelNowTrainScheduleObject;
import ws.mahesh.travelassist.beta.trains.trainview.TrainViewActivity;

/**
 * Created by mahesh on 28/03/15.
 */
public class TravelNowTrainScheduleFinderAdapter extends RecyclerView.Adapter<TravelNowTrainScheduleFinderAdapter.TrainViewHolder> {
    List<TravelNowTrainScheduleObject> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;


    public TravelNowTrainScheduleFinderAdapter(Context context, List<TravelNowTrainScheduleObject> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public TrainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_travel_now_schedule, parent, false);
        TrainViewHolder holder = new TrainViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(TrainViewHolder holder, int position) {
        TravelNowTrainScheduleObject current = data.get(position);
        holder.cars.setVisibility(View.VISIBLE);
        holder.src_time.setText(TrainStaticHolder.getTimeFromMins(current.getSrc_time()) + " " + TrainStaticHolder.getStationName(current.getEnd_id()));
        holder.dest_time.setText("Reach " + TrainStaticHolder.getStationName(current.getDest_station_id()) + " by " + TrainStaticHolder.getTimeFromMins(current.getDest_time()));
        holder.eta.setText("Time " + (current.getDest_time() - current.getSrc_time()) + "m");
        holder.from_stop.setText("From " + TrainStaticHolder.getStationName(current.getSrc_station_id()));
        if (("" + current.getSpeed()).equals("S")) {
            holder.speed.setText("SLOW");
        } else if (("" + current.getSpeed()).equals("F")) {
            holder.speed.setText("FAST");
        }
        if ((current.getCars() > 0)) {
            holder.cars.setText("CARS: " + current.getCars());
        } else
            holder.cars.setVisibility(View.GONE);
        if (current.getSpl_info().equals("") || current.getSpl_info() == null) {
            holder.spl_info.setVisibility(View.GONE);
        } else
            holder.spl_info.setText("Platform no.: " + current.spl_info);
        holder.start_end.setText(TrainStaticHolder.getStationName(current.getStart_id()) + " - " + TrainStaticHolder.getStationName(current.getEnd_id()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class TrainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView src_time, speed, cars, dest_time, eta, start_end, spl_info, from_stop;

        public TrainViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            src_time = (TextView) itemView.findViewById(R.id.textViewSrcTime1);
            dest_time = (TextView) itemView.findViewById(R.id.textViewDestTime);
            speed = (TextView) itemView.findViewById(R.id.textViewSpeed);
            cars = (TextView) itemView.findViewById(R.id.textViewCars);
            eta = (TextView) itemView.findViewById(R.id.textViewETA);
            start_end = (TextView) itemView.findViewById(R.id.textViewFromTo);
            spl_info = (TextView) itemView.findViewById(R.id.textViewInfo);
            from_stop = (TextView) itemView.findViewById(R.id.textViewFrom);
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(context, TrainViewActivity.class);
            i.putExtra("id", data.get(getAdapterPosition()).getId());
            i.putExtra("route", TrainStaticHolder.getTimeFromMins(data.get(getAdapterPosition()).getSrc_time()) + " " + TrainStaticHolder.getStationName(data.get(getAdapterPosition()).getEnd_id()));
            i.putExtra("line", data.get(getAdapterPosition()).getLine());
            i.putExtra("src", data.get(getAdapterPosition()).getSrc_station_id());
            i.putExtra("dest", data.get(getAdapterPosition()).getDest_station_id());
            context.startActivity(i);
        }
    }
}
