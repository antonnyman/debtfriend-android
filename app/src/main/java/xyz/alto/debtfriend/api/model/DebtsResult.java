package xyz.alto.debtfriend.api.model;

import java.util.List;

import xyz.alto.debtfriend.debt.model.Debt;


/**
 * Created by antonnyman on 23/10/15.
 */
public class DebtsResult {

    List<Debt> result;

    public DebtsResult(List<Debt> result) {
        this.result = result;
    }

    public List<Debt> getResult() {
        return result;
    }

    public void setResult(List<Debt> result) {
        this.result = result;
    }
}
