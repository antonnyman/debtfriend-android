package xyz.alto.debtfriend.api.result;

import java.util.List;

import xyz.alto.debtfriend.api.model.User;

/**
 * Created by antonnyman on 30/10/15.
 */
public class UserResult {

    List<User> result;

    public UserResult(List<User> result) {
        this.result = result;
    }

    public List<User> getResult() {
        return result;
    }

    public void setResult(List<User> result) {
        this.result = result;
    }
}
