package xyz.alto.debtfriend.registration.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;



import butterknife.Bind;
import butterknife.BindString;
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
import xyz.alto.debtfriend.api.model.RegistrationResult;
import xyz.alto.debtfriend.api.model.User;
import xyz.alto.debtfriend.home.view.HomeView;
import xyz.alto.debtfriend.login.view.LoginView;
import xyz.alto.debtfriend.start.view.StartView;
import xyz.alto.debtfriend.utils.Helper;

/**
 * Created by isak on 2015-10-10.
 */
public class RegistrationView extends LinearLayout {


    @Bind(R.id.view_registration_input_username) EditText mUsername;
    @Bind(R.id.view_registration_input_email) EditText mEmail;
    @Bind(R.id.view_registration_input_password) EditText mPassword;
    @Bind(R.id.view_registration_input_button) Button mCreateAccount;
    @Bind(R.id.view_registration_progressbar) ProgressBar mProgressBar;
    @Bind(R.id.view_registration_loading_text) TextView mLoadingText;

    @BindString(R.string.ok) String ok;
    @BindString(R.string.got_it) String gotIt;
    @BindString(R.string.view_registration_no_internet) String mNoInternet;
    @BindString(R.string.view_registration_fill_all_fields) String mAllFields;
    @BindString(R.string.view_registration_must_be_six_or_longer) String mMustBeSixOrLonger;

    public static class Builder extends ViewBuilder {
        @Override
        protected View build(Context context, ViewGroup container) {
            return new RegistrationView(context);
        }
    }

    public RegistrationView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_registration, this, true);
        ButterKnife.bind(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // Initera saker här
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        /* Om PapperView.Builder har skickats med argument (bundle) eller presenter (object),
        kan de hämtas här med getArguments() och getPresenter() */

        Helper.hideViews(mProgressBar, mLoadingText);

        if (!Helper.canConnect(getContext())) {
            Helper.snackbar(this, mNoInternet, ok, 0);
        }
    }

    public ViewManager getViewManager(Context context) {
        return ((PapperActivity) context).getViewManager();
    }

    @OnClick(R.id.view_registration_input_button) void register() {

        Helper.hideKeyboard(this, getContext());
        Helper.hideViews(mUsername, mEmail, mPassword, mCreateAccount);
        Helper.showViews(mProgressBar, mLoadingText);

        mLoadingText.setText(R.string.view_registration_loading);


        final String email = mEmail.getText().toString().trim();
        final String username = mUsername.getText().toString().trim();
        final String password = mPassword.getText().toString();


        if (Helper.isEmptyString(email) || Helper.isEmptyString(username) || Helper.isEmptyString(password)) {

            Helper.showViews(mUsername, mEmail, mPassword, mCreateAccount);
            Helper.hideViews(mProgressBar, mLoadingText);
            Helper.snackbar(this, mAllFields, ok, 1);

        } else if(password.length() < 6) {

            Helper.showViews(mUsername, mEmail, mPassword, mCreateAccount);
            Helper.hideViews(mProgressBar, mLoadingText);
            Helper.snackbar(this, mMustBeSixOrLonger, gotIt, 1);

        } else if (!Helper.canConnect(getContext())) {

            Helper.showViews(mUsername, mEmail, mPassword, mCreateAccount);
            Helper.hideViews(mProgressBar, mLoadingText);
            Helper.snackbar(this, mNoInternet, ok, 0);

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
                        getViewManager(getContext()).addView(new LoginView.Builder());
                        Helper.snackbar(RegistrationView.this, "Confirm your account at " + email +" before logging in.", ok, 0);
                    } else {
                        Helper.showViews(mUsername, mEmail, mPassword, mCreateAccount);
                        Helper.hideViews(mProgressBar, mLoadingText);
                        Helper.snackbar(RegistrationView.this, result + ".", ok, 0);
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
