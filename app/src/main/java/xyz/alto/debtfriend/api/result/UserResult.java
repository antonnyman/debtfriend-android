package xyz.alto.debtfriend.api.result;

import java.util.List;

import xyz.alto.debtfriend.api.model.User;
import xyz.alto.debtfriend.friends.model.Friend;

/**
 * Created by antonnyman on 30/10/15.
 */
public class UserResult {

    Friend result;

    public UserResult(Friend result) {
        this.result = result;
    }

    public Friend getResult() {
        return result;
    }

    public void setResult(Friend result) {
        this.result = result;
    }
}
