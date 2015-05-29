package ws.mahesh.travelassist.beta.cabs.farecalculator.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

import ws.mahesh.travelassist.beta.R;
import ws.mahesh.travelassist.beta.cabs.farecalculator.base.FareCalculator;

/**
 * Created by mahesh on 28/03/15.
 */
public class CabsFareCalculatorAdapter extends RecyclerView.Adapter<CabsFareCalculatorAdapter.CabsViewHolder> {
    final DecimalFormat df = new DecimalFormat("#0.0");
    List<FareCalculator> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;


    public CabsFareCalculatorAdapter(Context context, List<FareCalculator> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public CabsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_cabs_fare, parent, false);
        CabsViewHolder holder = new CabsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CabsViewHolder holder, int position) {
        FareCalculator current = data.get(position);
        holder.ModeType.setText(current.getObject().getName() + " - " + current.getObject().getCity());
        holder.BaseAmount.setText("Rs. " + df.format(current.getBaseAmount()));
        holder.ExtraAmount.setText("Rs. " + df.format(current.getExtraDistanceAmount()));
        holder.ExtraTime.setText("Rs. " + df.format(current.getExtraWaitingAmount()));
        holder.TotalAmount.setText("Rs. " + df.format(current.getTotalAmount()));
        holder.NightAmount.setText("Rs. " + df.format(current.getNightAmount()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class CabsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView BaseAmount, ExtraAmount, ExtraTime, TotalAmount, NightAmount, ModeType;

        public CabsViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            ModeType = (TextView) itemView.findViewById(R.id.textViewMode);

            BaseAmount = (TextView) itemView.findViewById(R.id.textViewBaseAmount);
            ExtraAmount = (TextView) itemView.findViewById(R.id.textViewextraDistanceAmount);
            ExtraTime = (TextView) itemView.findViewById(R.id.textViewWaitTimeAmount);
            TotalAmount = (TextView) itemView.findViewById(R.id.textViewTotalAmount);
            NightAmount = (TextView) itemView.findViewById(R.id.textViewTotalAmountNight);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
