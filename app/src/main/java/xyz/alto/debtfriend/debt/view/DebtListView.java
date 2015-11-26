package xyz.alto.debtfriend.debt.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import xyz.alto.debtfriend.R;
import xyz.alto.debtfriend.api.RestClient;
import xyz.alto.debtfriend.api.result.DebtsResult;
import xyz.alto.debtfriend.debt.adapter.DebtListAdapter;
import xyz.alto.debtfriend.debt.model.Debt;
import xyz.alto.debtfriend.utils.Helper;

/**
 * Created by antonnyman on 23/10/15.
 */
public class DebtListView extends LinearLayout {

    @Bind(R.id.view_debts_list) RecyclerView mDebtsList;
    DebtListAdapter mDebtListAdapter;


    public DebtListView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_debts_list, this, true);
        ButterKnife.bind(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        List<Debt> debts = getDebts();

        mDebtListAdapter = new DebtListAdapter(debts);
        mDebtsList.setAdapter(mDebtListAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDebtsList.setLayoutManager(linearLayoutManager);

    }

    public List<Debt> getDebts() {
        final List<Debt> debts = new ArrayList<>();
        RestClient restClient = new RestClient();

        Call<DebtsResult> call = restClient.getAltoService().getDebts(Helper.getKey(getContext()));
        call.enqueue(new Callback<DebtsResult>() {
            @Override
            public void onResponse(Response<DebtsResult> response, Retrofit retrofit) {
                for(Debt d : response.body().getResult()) {
                    debts.add(new Debt(d.getId(), d.getOwner(), d.getReceiver(), d.getAmount(), d.getCurrency(), d.getDate(), d.getDescription()));
                }

                mDebtListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

        return debts;
    }
}
