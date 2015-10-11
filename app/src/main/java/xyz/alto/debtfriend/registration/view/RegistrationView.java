package xyz.alto.debtfriend.registration.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.gson.Gson;

import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import se.dromt.papper.PapperView;
import se.dromt.papper.activity.OnOptionsMenuListener;
import xyz.alto.debtfriend.R;
import xyz.alto.debtfriend.api.RestClient;
import xyz.alto.debtfriend.api.model.User;

/**
 * Created by isak on 2015-10-10.
 */
public class RegistrationView extends PapperView {

    public RegistrationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // Initera saker här
        ButterKnife.inject(this);

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        /* Om PapperView.Builder har skickats med argument (bundle) eller presenter (object),
        kan de hämtas här med getArguments() och getPresenter() */

        RestClient restClient = new RestClient();

        User user = new User("pungmannen@pung.com", "pungster", "pung123123");

        final Call<User> call = restClient.getAltoService().register(user);
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
