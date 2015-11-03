package xyz.alto.debtfriend.friends.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import xyz.alto.debtfriend.R;
import xyz.alto.debtfriend.friends.model.Friend;

/**
 * Created by antonnyman on 02/11/15.
 */
public class AddFriendAdapter extends RecyclerView.Adapter<AddFriendAdapter.ViewHolder> {

    private List<Friend> mFriends;

    public AddFriendAdapter(List<Friend> mFriends) {
        this.mFriends = mFriends;
    }

    @Override
    public AddFriendAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_friends_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.username.setText(mFriends.get(position).getUsername());
    }

    @Override
    public int getItemCount() {
        return mFriends != null ? mFriends.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.view_friends_list_item_username)
        TextView username;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
