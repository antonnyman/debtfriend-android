package xyz.alto.debtfriend.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.File;

import xyz.alto.debtfriend.R;
import xyz.alto.debtfriend.main.MainActivity;

/**
 * Created by antonnyman on 2015-10-12.
 */
public class Helper {

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

    public static void hideToolbar(Activity a) {
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
}
