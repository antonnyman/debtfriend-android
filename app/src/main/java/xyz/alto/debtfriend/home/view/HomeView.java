package xyz.alto.debtfriend.home.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Currency;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import se.dromt.papper.OnOptionsMenuListener;
import se.dromt.papper.ViewManager;
import se.dromt.papper.PapperActivity;
import se.dromt.papper.ViewBuilder;
import xyz.alto.debtfriend.R;
import xyz.alto.debtfriend.api.RestClient;
import xyz.alto.debtfriend.api.model.LogoutResult;
import xyz.alto.debtfriend.debt.view.AddDebtView;
import xyz.alto.debtfriend.friends.view.FriendsListView;
import xyz.alto.debtfriend.login.view.LoginView;
import xyz.alto.debtfriend.registration.view.RegistrationView;
import xyz.alto.debtfriend.start.view.StartView;
import xyz.alto.debtfriend.utils.Helper;

/**
 * Created by Anton on 2015-10-10.
 */
public class HomeView extends LinearLayout implements OnOptionsMenuListener {

    public HomeView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_home, this, true);
        ButterKnife.bind(this);


        List<Currency> currencies = Helper.getAllCurrencies();
        for(Currency c : currencies) {
            Log.d("Currency", c.getCurrencyCode() + " " + c.getDisplayName() + " " + c.getSymbol());
        }
    }

    public static class Builder extends ViewBuilder {
        @Override
        protected View build(Context context, ViewGroup container) {
            return new HomeView(context);
        }
    }

    private ViewManager getViewManager(Context context) {
        return ((PapperActivity) context).getViewManager();
    }



    @Nullable
    @OnClick(R.id.view_home_button_friends) void clickFriends() {
        getViewManager(getContext()).addView(new FriendsListView.Builder());
    }

    @Nullable
    @OnClick(R.id.view_home_button_logout) void logout() {
        Helper.clearKey(getContext());
        RestClient restClient = new RestClient();

        Call<LogoutResult> call = restClient.getAltoService().logout(Helper.getKey(getContext()));
        call.enqueue(new Callback<LogoutResult>() {
            @Override
            public void onResponse(Response<LogoutResult> response, Retrofit retrofit) {
                Log.d("Response", response.body().getResult() + " " +response.code() + " "+ response.message() + " " + response.isSuccess());
                if(response.code() == 200)
                Helper.snackbar(HomeView.this, "Logged out", "OK", 2);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });

        getViewManager(getContext()).addView(new StartView.Builder());
    }

    @Nullable
    @OnClick(R.id.view_home_add_debt_fab) void addDebt(){
        getViewManager(getContext()).addView(new AddDebtView.Builder());
    }

    @Override
    public void onOptionsMenuCreated(Menu menu) {
        new MenuInflater(getContext()).inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }
}
