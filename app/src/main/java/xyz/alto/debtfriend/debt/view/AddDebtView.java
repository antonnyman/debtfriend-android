package xyz.alto.debtfriend.debt.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import se.dromt.papper.PapperActivity;
import se.dromt.papper.ViewBuilder;
import se.dromt.papper.ViewManager;
import xyz.alto.debtfriend.R;
import xyz.alto.debtfriend.api.RestClient;
import xyz.alto.debtfriend.api.result.FriendsResult;
import xyz.alto.debtfriend.friends.model.Friend;
import xyz.alto.debtfriend.utils.Helper;

/**
 * Created by antonnyman on 14/11/15.
 */
public class AddDebtView extends LinearLayout {

    @Bind(R.id.view_add_debt_input_amount) EditText mAmount;
    @Bind(R.id.view_add_debt_input_comment) EditText mComment;
    @Bind(R.id.view_add_debt_currency) Spinner mSpinner;
    @Bind(R.id.view_add_debt_friends_list) ListView mFriends;
    ArrayAdapter<String> spinnerAdapter;
    List<String> mCurrencyList = new ArrayList<>();
    RestClient mRestClient = new RestClient();
    List<Friend> allFriends;
    List<String> mUsernames;
    List<Friend> friendsSharingThisDebt;
    ArrayAdapter<Friend> friendAdapter;
    Bundle bundle;
    int currencyPos = 0;


    public AddDebtView(Context context, ViewBuilder builder) {
        super(context);

        LayoutInflater.from(getContext()).inflate(R.layout.view_add_debt, this, true);
        ButterKnife.bind(this);

    }

    public ViewManager getViewManager(Context context) {
        return ((PapperActivity) context).getViewManager();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();


    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();


        friendsSharingThisDebt = new ArrayList<>();
        mUsernames = new ArrayList<>();

        spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, mCurrencyList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        friendAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, friendsSharingThisDebt);
        mFriends.setAdapter(friendAdapter);

        allFriends = getMyFriends();
        new CorrectCurrency().execute();
    }

    public static class Builder extends ViewBuilder {
        @Override
        protected View build(Context context, ViewGroup container) {
            return new AddDebtView(context, this);
        }
    }


    @OnClick(R.id.view_add_debt_button_next) void next() {
        Bundle bundle = new Bundle();

        bundle.putParcelable("friends", Parcels.wrap(friendsSharingThisDebt));
        bundle.putParcelable("currency", Parcels.wrap(mSpinner.getSelectedItemPosition()));
        bundle.putParcelable("amount", Parcels.wrap(mAmount.getText().toString()));
        bundle.putParcelable("comment", Parcels.wrap(mComment.getText().toString()));

        getViewManager(getContext()).addView(new CalculateAndSaveDebtView.Builder().arguments(bundle));
    }



    @OnClick(R.id.view_add_debt_button_add_friend) void addFriend() {

        mUsernames.clear();

        for(Friend f : allFriends) {
            mUsernames.add(f.getUsername());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setItems(mUsernames.toArray(new String[mUsernames.size()]), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(!friendsSharingThisDebt.contains(allFriends.get(i))) {
                    friendsSharingThisDebt.add(allFriends.get(i));
                    friendAdapter.notifyDataSetChanged();
                }
            }
        });
        builder.show();
    }




    List<Friend> getMyFriends() {
        final List<Friend> friendList = new ArrayList<>();


        Call<FriendsResult> call = mRestClient.getAltoService().getFriends(Helper.getKey(getContext()));
        call.enqueue(new Callback<FriendsResult>() {
            @Override
            public void onResponse(Response<FriendsResult> response, Retrofit retrofit) {

                if (response.body() == null) {
                    Helper.toast(getContext(), "API not running.");
                } else {
                    for (Friend f : response.body().getResult()) {
                        friendList.add(new Friend(f.getId(), f.getUsername(), f.getEmail(), f.getLastLoggedIn(), f.getRegisteredOn(), f.getConfirmedOn(), f.isAdmin(), f.isConfirmed()));
                    }
                }

                friendAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
        return friendList;
    }


    class CorrectCurrency extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            for(int i = 0; i < Helper.getAllCurrencies().size(); i++) {
                mCurrencyList.add(Helper.getAllCurrencies().get(i).getDisplayName(Locale.getDefault()) + " (" + Helper.getAllCurrencies().get(i).getSymbol(Locale.getDefault()) + ")");

                if(Helper.getAllCurrencies().get(i).getCurrencyCode().equalsIgnoreCase(Helper.myCurrency())) {
                    currencyPos = i;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mSpinner.setAdapter(spinnerAdapter);
            mSpinner.setSelection(currencyPos);
        }
    }

}
