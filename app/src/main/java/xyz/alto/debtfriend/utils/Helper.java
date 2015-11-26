package xyz.alto.debtfriend.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import xyz.alto.debtfriend.R;
import xyz.alto.debtfriend.api.RestClient;
import xyz.alto.debtfriend.api.model.User;
import xyz.alto.debtfriend.api.result.UserResult;
import xyz.alto.debtfriend.friends.model.Friend;
import xyz.alto.debtfriend.friends.model.Me;
import xyz.alto.debtfriend.main.MainActivity;

/**
 * Created by antonnyman on 2015-10-12.
 */
public class Helper {

    public static DateFormat format = SimpleDateFormat.getDateInstance();

    public static void toast(Context context, String string) {
        Toast.makeText(context, string, Toast.LENGTH_LONG).show();
    }

    public static void snackbar(View view, String text, String actionText, int length) {
        final Snackbar snackbar;

        switch (length) {
            case 0:
                snackbar = Snackbar.make(view, text, Snackbar.LENGTH_INDEFINITE);
                break;
            case 1:
                snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
                break;
            case 2:
                snackbar = Snackbar.make(view, text, Snackbar.LENGTH_SHORT);
                break;
            default:
                snackbar = Snackbar.make(view, text, Snackbar.LENGTH_INDEFINITE);
        }

            snackbar.setAction(actionText, new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    snackbar.dismiss();
                }
            });
            snackbar.show();

    }

    public static void hideKeyboard(View view, Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public static void showKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public static void hideToolbar(Context a) {
        MainActivity m = (MainActivity) a;
        m.getSupportActionBar().hide();
    }

    public static void showToolbar(Activity a) {
        MainActivity m = (MainActivity) a;
        m.getSupportActionBar().show();
    }

    public static void changeToolbarTitle(Activity a, String title) {
        MainActivity m = (MainActivity) a;
        m.getSupportActionBar().setTitle(title);
    }

    public static void hideViews(View... v) {
        for(View view : v) {
            view.setVisibility(View.GONE);
        }
    }

    public static void showViews(View... v){
        for(View view : v) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public static boolean isEmptyString(CharSequence string) {
        return (TextUtils.isEmpty(string) || string.toString().equalsIgnoreCase("null"));
    }

    public static boolean canConnect(@NonNull Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void storeString(Context context, String key, String save) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Static.SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, save);
        editor.apply();
    }

    public static void storeInt(Context context, String key, int save) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Static.SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, save);
        editor.apply();
    }

    public static void storeBoolean(Context context, String key, boolean save) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Static.SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, save);
        editor.apply();
    }

    public static boolean isLoggedIn(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Static.SHARED_PREFS, Context.MODE_PRIVATE);
        boolean isloggedin = sharedPreferences.getBoolean("isLoggedIn", false);
        return isloggedin;
    }

    public static String getKey(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Static.SHARED_PREFS, Context.MODE_PRIVATE);
        String key = sharedPreferences.getString("key", "");
        return key;
    }

    public static void clearKey(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Static.SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
    }


    public static void saveMe(final Context context, String key) {
        RestClient restClient = new RestClient();
        Call<UserResult> call = restClient.getAltoService().getUser(key);
        call.enqueue(new Callback<UserResult>() {
            @Override
            public void onResponse(Response<UserResult> response, Retrofit retrofit) {
                SharedPreferences sharedPreferences = context.getSharedPreferences(Static.SHARED_PREFS, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("id", response.body().getResult().getId());
                editor.putString("username", response.body().getResult().getUsername());
                editor.putString("email", response.body().getResult().getEmail());
                editor.putString("lastLoggedIn", response.body().getResult().getLastLoggedIn().toString());
                editor.putString("registeredOn", response.body().getResult().getRegisteredOn().toString());
                editor.putString("confirmedOn", response.body().getResult().getConfirmedOn().toString());
                editor.putBoolean("admin", response.body().getResult().isAdmin());
                editor.putBoolean("confirmed", response.body().getResult().isConfirmed());
                editor.apply();
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static Me getMe(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Static.SHARED_PREFS, Context.MODE_PRIVATE);

        Date lastLoggedIn = null;
        Date registeredOn = null;
        Date confirmedOn = null;
        try {
            lastLoggedIn = format.parse(sharedPreferences.getString("lastLoggedIn", ""));
            registeredOn = format.parse(sharedPreferences.getString("registeredOn", ""));
            confirmedOn = format.parse(sharedPreferences.getString("confirmedOn", ""));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new Me(
                    sharedPreferences.getInt("id", 0),
                    sharedPreferences.getString("username", ""),
                    sharedPreferences.getString("email", ""),
                    lastLoggedIn,
                    registeredOn,
                    confirmedOn,
                    sharedPreferences.getBoolean("admin", false),
                    sharedPreferences.getBoolean("confirmed", false)
            );

    }

    public static boolean fileExists(Context context, String filename) {
        File file = context.getFileStreamPath(filename);
        if(file == null || !file.exists()) {
            return false;
        }
        return true;
    }

    public static void deleteFile(Context context, String filename) {
        File file = context.getFileStreamPath(filename);
        if(file != null) {
            file.delete();
        }
    }

    public static void fadeIn(Context c, View v) {
        Animation fadeInAnim = AnimationUtils.loadAnimation(c, android.R.anim.fade_in);
        v.setVisibility(View.VISIBLE);
        v.startAnimation(fadeInAnim);
    }

    public static void fadeOut(Context c, View v) {
        Animation fadeOutAnim = AnimationUtils.loadAnimation(c, android.R.anim.fade_out);
        v.startAnimation(fadeOutAnim);
        v.setVisibility(View.INVISIBLE);
    }

    public static List<Currency> getAllCurrencies() {
        return new ArrayList<>(Currency.getAvailableCurrencies());
    }

    public static String myCurrency() {
        return Currency.getInstance(Locale.getDefault()) + "";
    }


}
