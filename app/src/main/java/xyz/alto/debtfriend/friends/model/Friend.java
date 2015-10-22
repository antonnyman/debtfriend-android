package xyz.alto.debtfriend.friends.model;

import java.util.Date;

/**
 * Created by antonnyman on 21/10/15.
 */
public class Friend {

    String username, email;
    Date lastLoggedIn, registredOn;
    boolean admin, confirmed;

    public Friend(String username, String email, Date lastLoggedIn, Date registredOn, boolean admin, boolean confirmed) {
        this.username = username;
        this.email = email;
        this.lastLoggedIn = lastLoggedIn;
        this.registredOn = registredOn;
        this.admin = admin;
        this.confirmed = confirmed;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getLastLoggedIn() {
        return lastLoggedIn;
    }

    public void setLastLoggedIn(Date lastLoggedIn) {
        this.lastLoggedIn = lastLoggedIn;
    }

    public Date getRegistredOn() {
        return registredOn;
    }

    public void setRegistredOn(Date registredOn) {
        this.registredOn = registredOn;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }
}
