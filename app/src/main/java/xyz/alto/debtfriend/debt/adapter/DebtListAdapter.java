package xyz.alto.debtfriend.debt.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import xyz.alto.debtfriend.R;
import xyz.alto.debtfriend.debt.model.Debt;

/**
 * Created by antonnyman on 23/10/15.
 */
public class DebtListAdapter extends RecyclerView.Adapter<DebtListAdapter.ViewHolder> {

    private List<Debt> mDebts;
    private DateFormat mDateFormat =  DateFormat.getDateTimeInstance();

    public DebtListAdapter(List<Debt> mDebts) {
        this.mDebts = mDebts;
    }

    @Override
    public DebtListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_debts_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.owner.setText(mDebts.get(position).getOwner());
        holder.receiver.setText(mDebts.get(position).getReceiver());
        holder.amount.setText(mDebts.get(position).getAmount() + "");
        holder.date.setText(mDateFormat.format(mDebts.get(position).getDate()));
        holder.description.setText(mDebts.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return mDebts != null ? mDebts.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.view_debts_list_item_owner)TextView owner;
        @Bind(R.id.view_debts_list_item_receiver)TextView receiver;
        @Bind(R.id.view_debts_list_item_amount)TextView amount;
        @Bind(R.id.view_debts_list_item_date)TextView date;
        @Bind(R.id.view_debts_list_item_description)TextView description;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this.itemView);
        }
    }
}
