package xyz.alto.debtfriend.api.result;

import java.util.Date;
import java.util.List;

import xyz.alto.debtfriend.friends.model.Friend;

/**
 * Created by antonnyman on 21/10/15.
 */
public class FriendsResult {

    List<Friend> result;

    public FriendsResult(List<Friend> result) {
        this.result = result;
    }

    public List<Friend> getResult() {
        return result;
    }

    public void setResult(List<Friend> result) {
        this.result = result;
    }
}
