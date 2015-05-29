package ws.mahesh.travelassist.beta.cabs.customcabs.adapter;

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
import ws.mahesh.travelassist.beta.cabs.StaticCabsHolder;
import ws.mahesh.travelassist.beta.cabs.database.CabsDatabaseHelper;
import ws.mahesh.travelassist.beta.cabs.models.CabsObject;
import ws.mahesh.travelassist.beta.cabs.now.CabsTravelNowActivity;

/**
 * Created by mahesh on 16/03/15.
 */
public class CustomCabsListAdapter extends RecyclerView.Adapter<CustomCabsListAdapter.CabsViewHolder> {
    List<CabsObject> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;
    private CabsDatabaseHelper dbHelper;

    public CustomCabsListAdapter(Context context, List<CabsObject> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        dbHelper = new CabsDatabaseHelper(context);
    }

    @Override
    public CabsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_list_bus, parent, false);
        CabsViewHolder holder = new CabsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CabsViewHolder holder, int position) {
        CabsObject current = data.get(position);
        holder.busNo.setText(current.getName());
        holder.stop1.setText(current.getCity());
        holder.ETA.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class CabsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView busNo, ETA, stop1;

        public CabsViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            busNo = (TextView) itemView.findViewById(R.id.textViewBus1);
            ETA = (TextView) itemView.findViewById(R.id.textViewETA);
            stop1 = (TextView) itemView.findViewById(R.id.textViewStop1);
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(context, CabsTravelNowActivity.class);
            i.putExtra("id", data.get(getAdapterPosition()).getId());
            context.startActivity(i);
        }

        @Override
        public boolean onLongClick(View view) {
            dbHelper.deleteCustomCab(data.get(getAdapterPosition()).getId());
            StaticCabsHolder.removeCustomCab(data.get(getAdapterPosition()).getId());
            data.remove(getAdapterPosition());
            notifyDataSetChanged();
            return false;
        }
    }
}
