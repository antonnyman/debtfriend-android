package xyz.alto.debtfriend.registration.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import se.dromt.papper.ViewBuilder;
import xyz.alto.debtfriend.R;
import xyz.alto.debtfriend.api.RestClient;
import xyz.alto.debtfriend.api.model.User;

/**
 * Created by isak on 2015-10-10.
 */
public class RegistrationView extends LinearLayout {

    public static class Builder extends ViewBuilder {
        @Override
        protected View build(Context context, ViewGroup container) {
            return new RegistrationView(context);
        }
    }

    public RegistrationView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_registration, this, true);
        init();
    }

    private void init() {
        RestClient restClient = new RestClient();

        User user = new User("pungmannen@pung.com", "pungster", "pung123123");

        final Call<User> call = restClient
                .getAltoService()
                .register(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Response<User> response, Retrofit retrofit) {
                Log.d("This shit", new Gson().toJson(response.body()) + " " + response.message() + " " + response.code());
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
