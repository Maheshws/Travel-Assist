package ws.mahesh.travelassist.beta.trains.trainview.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import ws.mahesh.travelassist.beta.R;
import ws.mahesh.travelassist.beta.trains.TrainStaticHolder;
import ws.mahesh.travelassist.beta.trains.trainview.model.TrainViewObject;

/**
 * Created by mahesh on 28/03/15.
 */

public class TrainViewAdapter extends RecyclerView.Adapter<TrainViewAdapter.TrainViewHolder> {
    List<TrainViewObject> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;
    private int ScrollSrc, ScrollDest;

    public TrainViewAdapter(Context context, List<TrainViewObject> data, int ScrollSrc, int ScrollDest) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.ScrollSrc = ScrollSrc;
        this.ScrollDest = ScrollDest;
    }

    @Override
    public TrainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_train_view, parent, false);
        TrainViewHolder holder = new TrainViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(TrainViewHolder holder, int position) {
        TrainViewObject current = data.get(position);
        holder.side.setVisibility(View.VISIBLE);
        holder.pf_no.setVisibility(View.VISIBLE);
        holder.station.setText(TrainStaticHolder.getStationName(current.getStation_id()));
        holder.time.setText(TrainStaticHolder.getTimeFromMins(current.getTime()));

        if (current.getPlatform_no().equals(""))
            holder.pf_no.setVisibility(View.GONE);
        else
            holder.pf_no.setText("Platform No. " + current.getPlatform_no());
        if (current.getPlatform_side().equals("L"))
            holder.side.setText("Platform on LEFT side");
        else if (current.getPlatform_side().equals("B"))
            holder.side.setText("Platform on BOTH side");
        else if (current.getPlatform_side().equals("R"))
            holder.side.setText("Platform on RIGHT side");
        else
            holder.side.setVisibility(View.GONE);
        if (position >= ScrollSrc && position <= ScrollDest) {
            holder.mainLayout.setBackgroundColor(Color.parseColor("#2196F3"));
            holder.pf_no.setTextColor(Color.parseColor("#FFFFFF"));
            holder.side.setTextColor(Color.parseColor("#FFFFFF"));
            holder.time.setTextColor(Color.parseColor("#FFFFFF"));
            holder.station.setTextColor(Color.parseColor("#FFFFFF"));
        } else {
            holder.mainLayout.setBackgroundColor(Color.TRANSPARENT);
            holder.pf_no.setTextColor(Color.parseColor("#000000"));
            holder.side.setTextColor(Color.parseColor("#000000"));
            holder.time.setTextColor(Color.parseColor("#000000"));
            holder.station.setTextColor(Color.parseColor("#000000"));
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class TrainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView time, station, side, pf_no;
        LinearLayout mainLayout;

        public TrainViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            time = (TextView) itemView.findViewById(R.id.textViewTime);
            station = (TextView) itemView.findViewById(R.id.textViewStation);
            side = (TextView) itemView.findViewById(R.id.textViewSide);
            pf_no = (TextView) itemView.findViewById(R.id.textViewPlatform);
            mainLayout = (LinearLayout) itemView.findViewById(R.id.main_layout);
        }

        @Override
        public void onClick(View view) {
            //Intent i = new Intent(context, TrainsRouteViewActivity.class);
            //i.putExtra("id", data.get(getAdapterPosition()).getId());
            //context.startActivity(i);

        }
    }
}
