package ws.mahesh.travelassist.beta.bus.current.adapter;

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
import ws.mahesh.travelassist.beta.bus.current.models.CurrentStopObject;

/**
 * Created by mahesh on 03/03/15.
 */
public class CurrentStopsAdapter extends RecyclerView.Adapter<CurrentStopsAdapter.StopsViewHolder> {
    List<CurrentStopObject> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;

    public CurrentStopsAdapter(Context context, List<CurrentStopObject> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public StopsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_current_stop, parent, false);
        StopsViewHolder holder = new StopsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(StopsViewHolder holder, int position) {
        CurrentStopObject current = data.get(position);
        if (current.isVisited()) {
            holder.layout.setBackgroundColor(Color.parseColor("#4CAF50"));


        } else {
            holder.layout.setBackgroundColor(Color.TRANSPARENT);

        }
        holder.ETA.setText(current.getETA());
        holder.StopName.setText(current.getStopName());
        holder.Visited.setText(current.getReachedAt());
        holder.Distance.setText(current.getDistance());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class StopsViewHolder extends RecyclerView.ViewHolder {
        TextView StopName, ETA, Visited, Distance;
        LinearLayout layout;

        public StopsViewHolder(View itemView) {
            super(itemView);
            StopName = (TextView) itemView.findViewById(R.id.textViewName);
            ETA = (TextView) itemView.findViewById(R.id.textViewETA);
            Distance = (TextView) itemView.findViewById(R.id.textViewDistance);
            Visited = (TextView) itemView.findViewById(R.id.textViewVisited);
            layout = (LinearLayout) itemView.findViewById(R.id.main_layout);
        }
    }
}
