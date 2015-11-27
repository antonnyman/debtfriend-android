package xyz.alto.debtfriend.debt.model;

import java.util.Date;

/**
 * Created by antonnyman on 23/10/15.
 */
public class Debt {

    int id, owner, receiver;
    float amount;
    Date date;
    String ownerUsername, receiverUsername, currency, description;

    public Debt(int id,
                int owner,
                int receiver,
                float amount,
                String ownerUsername,
                String receiverUsername,
                String currency,
                Date date,
                String description) {
        this.id = id;
        this.owner = owner;
        this.receiver = receiver;
        this.amount = amount;
        this.date = date;
        this.ownerUsername = ownerUsername;
        this.receiverUsername = receiverUsername;
        this.currency = currency;
        this.description = description;
    }

    public Debt(int id, int owner, int receiver, float amount, String currency, Date date, String description) {
        this.id = id;
        this.owner = owner;
        this.receiver = receiver;
        this.amount = amount;
        this.currency = currency;
        this.date = date;
        this.description = description;
    }

    public Debt(int owner, int receiver, float amount, String currency, String description) {
        this.owner = owner;
        this.receiver = receiver;
        this.amount = amount;
        this.currency = currency;
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

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public void setReceiverUsername(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrency() { return currency; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
