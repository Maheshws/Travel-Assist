package ws.mahesh.travelassist.beta.bus.buswise.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ws.mahesh.travelassist.beta.R;
import ws.mahesh.travelassist.beta.bus.buswise.BusWiseStopsActivity;
import ws.mahesh.travelassist.beta.bus.buswise.models.BusWiseObject;

/**
 * Created by mahesh on 16/03/15.
 */
public class BusWiseListAdapter extends RecyclerView.Adapter<BusWiseListAdapter.Bus1ViewHolder> implements Filterable {
    List<BusWiseObject> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;
    private List<BusWiseObject> orig;

    public BusWiseListAdapter(Context context, List<BusWiseObject> data) {
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
        BusWiseObject current = data.get(position);
        holder.busNo.setText(current.getShortName());
        holder.stop1.setText(current.getLongName());
        holder.ETA.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                final FilterResults oReturn = new FilterResults();
                final List<BusWiseObject> results = new ArrayList<>();
                if (orig == null)
                    orig = data;
                if (charSequence != null) {
                    if (orig != null & orig.size() > 0) {
                        for (final BusWiseObject g : orig) {
                            if (g.getShortName().toLowerCase().contains(charSequence.toString()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                data = (ArrayList<BusWiseObject>) filterResults.values;
                notifyDataSetChanged();
            }
        };

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
            Intent i = new Intent(context, BusWiseStopsActivity.class);
            i.putExtra("busId", data.get(getAdapterPosition()).getId());
            i.putExtra("busName", data.get(getAdapterPosition()).getShortName());
            context.startActivity(i);
        }
    }
}
