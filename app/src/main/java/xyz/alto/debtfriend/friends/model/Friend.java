package xyz.alto.debtfriend.friends.model;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import java.util.Date;

/**
 * Created by antonnyman on 21/10/15.
 */
@Parcel
public class Friend {

    int id;
    String username, email;
    Date lastLoggedIn, registeredOn, confirmedOn;
    boolean admin, confirmed;

    @ParcelConstructor
    public Friend(int id, String username, String email, Date lastLoggedIn, Date registeredOn, Date confirmedOn, boolean admin, boolean confirmed) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.lastLoggedIn = lastLoggedIn;
        this.registeredOn = registeredOn;
        this.confirmedOn = confirmedOn;
        this.admin = admin;
        this.confirmed = confirmed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(Date registeredOn) {
        this.registeredOn = registeredOn;
    }

    public Date getConfirmedOn() {
        return confirmedOn;
    }

    public void setConfirmedOn(Date confirmedOn) {
        this.confirmedOn = confirmedOn;
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

    @Override
    public String toString() {
        return username;
    }
}
