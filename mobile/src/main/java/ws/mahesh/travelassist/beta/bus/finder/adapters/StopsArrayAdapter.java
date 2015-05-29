package ws.mahesh.travelassist.beta.bus.finder.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

import ws.mahesh.travelassist.beta.R;
import ws.mahesh.travelassist.beta.bus.finder.models.StopObject;

/**
 * Created by mahesh on 02/04/15.
 */
public class StopsArrayAdapter extends ArrayAdapter<StopObject> implements Filterable {
    private Context context;
    private ArrayList<StopObject> objects;
    private ArrayList<StopObject> mOriginalValues;
    private ArrayList<StopObject> suggestions = new ArrayList<>();
    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((StopObject) (resultValue)).getStopname();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (StopObject product : mOriginalValues) {
                    if (product.getStopname().toLowerCase()
                            .contains(constraint.toString().toLowerCase())) {
                        suggestions.add(product);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            ArrayList<StopObject> filteredList = (ArrayList<StopObject>) results.values;
            if (results != null && results.count > 0) {
                objects.clear();
                for (StopObject c : filteredList) {
                    objects.add(c);
                }
                notifyDataSetChanged();
            }
        }
    };

    public StopsArrayAdapter(Context context, int res, ArrayList<StopObject> objects) {
        super(context, res, objects);
        this.context = context;
        this.objects = objects;
        mOriginalValues = objects;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public StopObject getItem(int position) {
        return objects.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_list_bus, parent,
                false);
        TextView busNo = (TextView) rowView.findViewById(R.id.textViewBus1);
        TextView ETA = (TextView) rowView.findViewById(R.id.textViewETA);
        TextView stop1 = (TextView) rowView.findViewById(R.id.textViewStop1);

        busNo.setText(objects.get(position).getStopname());
        ETA.setVisibility(View.GONE);
        stop1.setVisibility(View.GONE);

        return rowView;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }
}
