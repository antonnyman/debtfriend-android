package xyz.alto.debtfriend.friends.view;

import android.content.Context;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.parceler.Parcels;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import se.dromt.papper.PapperActivity;
import se.dromt.papper.ViewBuilder;
import se.dromt.papper.ViewManager;
import xyz.alto.debtfriend.R;
import xyz.alto.debtfriend.friends.model.Friend;

/**
 * Created by antonnyman on 03/11/15.
 */
public class FriendDetailView extends LinearLayout {

    @Bind(R.id.view_friends_detail_id) TextView id;
    @Bind(R.id.view_friends_detail_email) TextView email;
    @Bind(R.id.view_friends_detail_username) TextView username;
    @Bind(R.id.view_friends_detail_lastloggedin) TextView lastLoggedIn;
    @Bind(R.id.view_friends_detail_registeredon) TextView registeredOn;
    @Bind(R.id.view_friends_detail_confirmedon) TextView confirmedOn;
    @Bind(R.id.view_friends_detail_admin) TextView admin;
    @Bind(R.id.view_friends_detail_confirmed) TextView confirmed;

    Bundle bundle;

    public FriendDetailView(Context context, ViewBuilder builder) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_friends_detail, this, true);
        ButterKnife.bind(this);

        bundle = builder.getArguments();
    }



    public static class Builder extends ViewBuilder {
        @Override
        protected View build(Context context, ViewGroup container) {
            return new FriendDetailView(context, this);
        }
    }

    public ViewManager getViewManager(Context context) {
        return ((PapperActivity) context).getViewManager();
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        Friend friend = Parcels.unwrap(bundle.getParcelable("friend"));
        Date now = new Date();
        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());

        id.setText(friend.getId() + "");
        email.setText(friend.getEmail());
        username.setText(friend.getUsername());

        if (friend.getLastLoggedIn() == null) {
            lastLoggedIn.setText("Never");
        } else {
            lastLoggedIn.setText(DateUtils.getRelativeDateTimeString(getContext(), friend.getLastLoggedIn().getTime(), DateUtils.DAY_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, 0));
        }

        if(friend.isAdmin()) {
            registeredOn.setText(df.format(friend.getRegisteredOn()));
            admin.setText(friend.isAdmin() + "");
            confirmed.setText(friend.isConfirmed() + "");
            if(friend.getConfirmedOn() == null) {
                confirmedOn.setText("Not confirmed");
            } else {
                confirmedOn.setText(df.format(friend.getConfirmedOn()));
            }
        }

        Log.d("Hey", friend.getUsername());
    }
}
