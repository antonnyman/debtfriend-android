package xyz.alto.debtfriend.registration.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;


import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import se.dromt.papper.PapperView;
import xyz.alto.debtfriend.R;
import xyz.alto.debtfriend.api.RestClient;
import xyz.alto.debtfriend.api.model.RegistrationResult;
import xyz.alto.debtfriend.api.model.User;
import xyz.alto.debtfriend.utils.Helper;

/**
 * Created by isak on 2015-10-10.
 */
public class RegistrationView extends PapperView {


    @Bind(R.id.view_registration_input_username) EditText mUsername;
    @Bind(R.id.view_registration_input_email) EditText mEmail;
    @Bind(R.id.view_registration_input_password) EditText mPassword;
    @Bind(R.id.view_registration_input_button) Button mCreateAccount;
    @Bind(R.id.view_registration_progressbar) ProgressBar mProgressBar;
    @Bind(R.id.view_registration_loading_text) TextView mLoadingText;


    View parent = findViewById(R.id.view_registration);



    public RegistrationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // Initera saker här
        ButterKnife.bind(this);
        Helper.hideViews(mProgressBar, mLoadingText);

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        /* Om PapperView.Builder har skickats med argument (bundle) eller presenter (object),
        kan de hämtas här med getArguments() och getPresenter() */
        if (!Helper.canConnect(getContext())) {
            Helper.snackbar(parent, "Internet connection is required to create an account", "OK", 0);
        }
    }

    @OnClick(R.id.view_registration_input_button) void register() {

        Helper.hideKeyboard(this, getContext());
        Helper.hideViews(mUsername, mEmail, mPassword, mCreateAccount);
        Helper.showViews(mProgressBar, mLoadingText);

        mLoadingText.setText(R.string.view_registration_loading);


        String email = mEmail.getText().toString().trim();
        String username = mUsername.getText().toString().trim();
        String password = mPassword.getText().toString();

        


        if (Helper.isEmptyString(email) || Helper.isEmptyString(username) || Helper.isEmptyString(password)) {

            Helper.showViews(mUsername, mEmail, mPassword, mCreateAccount);
            Helper.hideViews(mProgressBar, mLoadingText);
            Helper.snackbar(parent, "Fill in all fields.", "OK", 1);

        } else if(password.length() < 6) {

            Helper.showViews(mUsername, mEmail, mPassword, mCreateAccount);
            Helper.hideViews(mProgressBar, mLoadingText);
            Helper.snackbar(parent, "Password must be 6 characters or longer.", "Got it", 1);

        } else if (!Helper.canConnect(getContext())) {

            Helper.showViews(mUsername, mEmail, mPassword, mCreateAccount);
            Helper.hideViews(mProgressBar, mLoadingText);
            Helper.snackbar(parent, "Internet connection is required to create an account", "OK", 0);

        } else {

            User user = new User(email, username, password);

            RestClient restClient = new RestClient();

            Call<RegistrationResult> call = restClient.getAltoService().register(user);
            call.enqueue(new Callback<RegistrationResult>() {
                @Override
                public void onResponse(Response<RegistrationResult> response, Retrofit retrofit) {
                    Log.d("Response", response.body().getResult() + " " + response.message() + " " + response.code());

                    String result = response.body().getResult();

                    if (result.equals("success")) {
                        getViewManager(getContext()).addView(new PapperView.Builder(R.layout.view_start));
                    } else {
                        Helper.showViews(mUsername, mEmail, mPassword, mCreateAccount);
                        Helper.hideViews(mProgressBar, mLoadingText);
                        Helper.snackbar(findViewById(R.id.view_registration), result + ".", "OK", 0);
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
