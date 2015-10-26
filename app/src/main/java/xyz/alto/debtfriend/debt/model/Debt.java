package xyz.alto.debtfriend.debt.model;

import java.util.Date;

/**
 * Created by antonnyman on 23/10/15.
 */
public class Debt {

    int id, owner, receiver;
    float amount;
    Date date;
    String description;

    public Debt(int id, int owner, int receiver, float amount, Date date, String description) {
        this.id = id;
        this.owner = owner;
        this.receiver = receiver;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public int getReceiver() {
        return receiver;
    }

    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
