package xyz.alto.debtfriend.debt.view;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import se.dromt.papper.ViewBuilder;
import xyz.alto.debtfriend.R;
import xyz.alto.debtfriend.api.RestClient;
import xyz.alto.debtfriend.api.result.DebtsResult;
import xyz.alto.debtfriend.api.result.UserResult;
import xyz.alto.debtfriend.debt.adapter.DebtListAdapter;
import xyz.alto.debtfriend.debt.model.Debt;
import xyz.alto.debtfriend.friends.model.Friend;
import xyz.alto.debtfriend.utils.Helper;

/**
 * Created by antonnyman on 23/10/15.
 */
public class DebtListView extends LinearLayout {

    @Bind(R.id.view_debts_list) RecyclerView mDebtsList;
    DebtListAdapter mDebtListAdapter;
    List<Debt> mDebts;
    HashSet<Integer> userids = new HashSet<>();
    RestClient restClient = new RestClient();



    public DebtListView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_debts_list, this, true);
        ButterKnife.bind(this);
    }

    public static class Builder extends ViewBuilder {
        @Override
        protected View build(Context context, ViewGroup container) {
            return new DebtListView(context);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        getDebts();

        mDebtListAdapter = new DebtListAdapter(mDebts);
        mDebtsList.setAdapter(mDebtListAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDebtsList.setLayoutManager(linearLayoutManager);

        Log.d("Debts", mDebts.size() + "");

    }


    public void getDebts() {
        Call<DebtsResult> call = restClient.getAltoService().getDebts(Helper.getKey(getContext()));
        call.enqueue(new Callback<DebtsResult>() {
            @Override
            public void onResponse(Response<DebtsResult> response, Retrofit retrofit) {
                if (response.body() == null) {
                    Helper.toast(getContext(), "API not running.");
                } else {

                    for (Debt d : response.body().getDebts()) {
                        mDebts.add(new Debt(
                                d.getId(),
                                d.getOwner(),
                                d.getReceiver(),
                                d.getAmount(),
                                getUsername(d.getOwner()),
                                getUsername(d.getReceiver()),
                                d.getCurrency(),
                                d.getDate(),
                                d.getDescription()));

                        userids.add(d.getOwner());
                        userids.add(d.getReceiver());
                    }
                }
                mDebtListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });

    }

    String getUsername(int id) {

        final String[] username = {""};

        Call<UserResult> call = restClient.getAltoService().getUserById(id, Helper.getKey(getContext()));
        call.enqueue(new Callback<UserResult>() {
            @Override
            public void onResponse(Response<UserResult> response, Retrofit retrofit) {
                username[0] = response.body().getResult().getUsername();
                Log.d("username", username[0]);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });

        mDebts.notify();

        return username[0];
    }


}
