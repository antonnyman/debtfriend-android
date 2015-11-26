package xyz.alto.debtfriend.debt.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.Currency;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import se.dromt.papper.ViewBuilder;
import xyz.alto.debtfriend.R;
import xyz.alto.debtfriend.api.RestClient;
import xyz.alto.debtfriend.api.result.AddDebtResult;
import xyz.alto.debtfriend.api.result.DebtsResult;
import xyz.alto.debtfriend.debt.model.Debt;
import xyz.alto.debtfriend.friends.model.Friend;
import xyz.alto.debtfriend.utils.Helper;

/**
 * Created by antonnyman on 25/11/15.
 */
public class CalculateAndSaveDebtView extends LinearLayout {

    @Bind(R.id.view_calculate_and_save_list) ListView mListView;
    @Bind(R.id.view_calculate_and_save_radiogroup) RadioGroup mRadioGroup;
    @Bind(R.id.view_calculate_and_save_I_owe_them) RadioButton mIOweThem;
    @Bind(R.id.view_calculate_and_save_they_owe_me) RadioButton mTheyOweMe;
    @Bind(R.id.view_calculate_and_save_total) TextView mTotal;
    @Bind(R.id.view_calculate_and_save_button) Button mSave;
    Bundle bundle;
    List<Friend> friendsSharingThisDebt;
    int currencyPosition;
    String total;
    String description;
    RestClient restClient = new RestClient();


    public CalculateAndSaveDebtView(Context context, ViewBuilder builder) {
        super(context);
        LayoutInflater.from(getContext()).inflate(R.layout.view_calculate_and_save, this, true);
        ButterKnife.bind(this);
        bundle = builder.getArguments();
    }

    public static class Builder extends ViewBuilder {
        @Override
        protected View build(Context context, ViewGroup container) {
            return new CalculateAndSaveDebtView(context, this);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        friendsSharingThisDebt = Parcels.unwrap(bundle.getParcelable("friends"));
        currencyPosition = Parcels.unwrap(bundle.getParcelable("currency"));
        total = Parcels.unwrap(bundle.getParcelable("amount"));
        description = Parcels.unwrap(bundle.getParcelable("comment"));

        Log.d("Currency", Helper.getAllCurrencies().get(currencyPosition) + "");

        for(Friend f : friendsSharingThisDebt) {
            Log.d("Friend", f.getUsername());
        }

    }


    @OnClick(R.id.view_calculate_and_save_button) void save() {

        Helper.hideViews(mListView, mRadioGroup, mIOweThem, mTheyOweMe, mTotal, mSave);

        float amount = Float.parseFloat(total);
        float part = amount / friendsSharingThisDebt.size();
        Log.d("Amount", amount + " " + part);

        for(int i = 0; i < friendsSharingThisDebt.size(); i++) {
            Debt debt = new Debt(
                    Helper.getMe(getContext()).getId(),
                    friendsSharingThisDebt.get(i).getId(),
                    part,
                    Helper.getAllCurrencies().get(currencyPosition) + "",
                    description
            );

            Call<AddDebtResult> call = restClient.getAltoService().addDebt(Helper.getKey(getContext()), debt);
            call.enqueue(new Callback<AddDebtResult>() {
                @Override
                public void onResponse(Response<AddDebtResult> response, Retrofit retrofit) {
                    if(response.body() != null && response.body().getResult().contains("success")) {
                        Helper.snackbar(CalculateAndSaveDebtView.this, "Debt saved", "ok", 1);
                        Helper.showViews(mListView, mRadioGroup, mIOweThem, mTheyOweMe, mTotal, mSave);
                    } else if(response.body() != null && response.body().getResult().contains("error")) {
                        Helper.snackbar(CalculateAndSaveDebtView.this, "Server returned error for some reason", "That's wierd", 1);
                        Helper.showViews(mListView, mRadioGroup, mIOweThem, mTheyOweMe, mTotal, mSave);
                    } else {
                        Helper.snackbar(CalculateAndSaveDebtView.this, "Something happened, couldn't save :C", "ok", 1);
                        Helper.showViews(mListView, mRadioGroup, mIOweThem, mTheyOweMe, mTotal, mSave);
                    }

                }

                @Override
                public void onFailure(Throwable t) {
                    t.printStackTrace();
                }
            });

        }

    }


}
