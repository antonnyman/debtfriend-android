package xyz.alto.debtfriend.friends.model;

import java.util.Date;

/**
 * Created by antonnyman on 21/10/15.
 */
public class Friend {

    String email, username;
    Date lastLoggedIn;
    boolean admin;

    public Friend(String email, String username, Date lastLoggedIn, boolean admin) {
        this.email = email;
        this.username = username;
        this.lastLoggedIn = lastLoggedIn;
        this.admin = admin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getLastLoggedIn() {
        return lastLoggedIn;
    }

    public void setLastLoggedIn(Date lastLoggedIn) {
        this.lastLoggedIn = lastLoggedIn;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
