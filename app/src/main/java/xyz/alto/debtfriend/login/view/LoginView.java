package xyz.alto.debtfriend.login.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import se.dromt.papper.ViewBuilder;
import xyz.alto.debtfriend.R;
import xyz.alto.debtfriend.api.RestClient;
import xyz.alto.debtfriend.api.model.LoginResult;
import xyz.alto.debtfriend.api.model.User;
import xyz.alto.debtfriend.utils.Helper;

/**
 * Created by Anton on 2015-10-10.
 */

public class LoginView extends LinearLayout {


    @Bind(R.id.view_login_input_username_or_email) EditText mEmail;
    @Bind(R.id.view_login_input_password) EditText mPassword;
    @Bind(R.id.view_login_button_login) Button mLogin;
    @Bind(R.id.view_login_progressbar) ProgressBar mProgressBar;
    @Bind(R.id.view_login_loading_text) TextView mLoadingText;

    @BindString(R.string.ok) String ok;
    @BindString(R.string.got_it) String gotIt;
    @BindString(R.string.view_login_no_internet) String mNoInternet;
    @BindString(R.string.view_login_fill_all_fields) String mAllFields;

    public static class Builder extends ViewBuilder {
        @Override
        protected View build(Context context, ViewGroup container) {
            return new LoginView(context);
        }
    }

    public LoginView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_login, this, true);
        ButterKnife.bind(this);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        Helper.hideViews(mProgressBar, mLoadingText);
    }

    @OnClick(R.id.view_login_button_login) void login() {

        Helper.hideKeyboard(this, getContext());
        Helper.hideViews(mEmail, mPassword, mLogin);
        Helper.showViews(mProgressBar, mLoadingText);

        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString();

        if (Helper.isEmptyString(email) || Helper.isEmptyString(password)) {
            Helper.showViews(mEmail, mPassword, mLogin);
            Helper.hideViews(mProgressBar, mLoadingText);
            Helper.snackbar(this, mAllFields, gotIt, 1);
        } else if(!Helper.canConnect(getContext())) {
            Helper.showViews(mEmail, mPassword, mLogin);
            Helper.hideViews(mProgressBar, mLoadingText);
            Helper.snackbar(this, mNoInternet, gotIt, 1);
        } else {

            User user = new User(email, password);
            RestClient restClient = new RestClient();

            Call<LoginResult> call = restClient.getAltoService().login(user);
            call.enqueue(new Callback<LoginResult>() {
                @Override
                public void onResponse(Response<LoginResult> response, Retrofit retrofit) {



                    if(response.body() != null && !response.body().getResult() && response.body().getKey().contains("confirm")) {
                        Log.d("Response", response.body().getResult() + " " + response.body().getKey() + " " + response.message() + " " + response.code());

                        Helper.hideViews(mProgressBar, mLoadingText);

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Confirm account")
                                .setMessage(response.body().getKey())
                                .setPositiveButton(ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Helper.showViews(mEmail, mPassword, mLogin);
                                        Helper.hideViews(mProgressBar, mLoadingText);
                                    }
                                })
                                .setNeutralButton("Resend email", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Helper.showViews(mEmail, mPassword, mLogin);
                                        Helper.hideViews(mProgressBar, mLoadingText);
                                        Helper.snackbar(LoginView.this, "Confirm email resent.", ok, 1);
                                    }
                                })
                                .create();
                        builder.show();
                    } else if(response.body() != null && !response.body().getResult()) {
                        Helper.showViews(mEmail, mPassword, mLogin);
                        Helper.hideViews(mProgressBar, mLoadingText);
                        Helper.snackbar(LoginView.this, response.body().getKey(), ok, 1);
                    } else {
                        Helper.showViews(mEmail, mPassword, mLogin);
                        Helper.hideViews(mProgressBar, mLoadingText);
                        Helper.snackbar(LoginView.this, "Login successful", ok, 1);
                        Log.d("The KEY", response.body().getKey());
                        Helper.storeString(getContext(), "key", response.body().getKey());
                        Helper.storeBoolean(getContext(), "isLoggedIn", true);
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
