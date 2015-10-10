package xyz.alto.debtfriend.registration.view;

import android.content.Context;
import android.util.AttributeSet;
<<<<<<< HEAD
import android.util.Log;
import android.widget.Button;
=======
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
>>>>>>> 3a4d4274df823fa70eee71835538dfdddbc86316

import com.google.gson.Gson;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import se.dromt.papper.PapperActivity;
import se.dromt.papper.PapperView;
import se.dromt.papper.ViewManager;
import xyz.alto.debtfriend.R;
import xyz.alto.debtfriend.api.RestClient;
import xyz.alto.debtfriend.api.model.User;
import xyz.alto.debtfriend.api.service.AltoService;
import se.dromt.papper.activity.OnOptionsMenuListener;
import xyz.alto.debtfriend.R;

/**
 * Created by isak on 2015-10-10.
 */
public class RegistrationView extends PapperView implements OnOptionsMenuListener {


    public RegistrationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnCreateOptionsMenuListener(this);
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
                Log.d("This shit", new Gson().toJson(response.body()) + " " +  response.message() + " " + response.code());
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onOptionsMenuCreated(Menu menu) {
        new MenuInflater(getContext()).inflate(R.menu.menu_test, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_about) {
            return true;
        }
        return false;
    }

}
