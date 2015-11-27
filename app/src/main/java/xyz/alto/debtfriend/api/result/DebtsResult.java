package xyz.alto.debtfriend.api.result;

import java.util.List;

import xyz.alto.debtfriend.debt.model.Debt;


/**
 * Created by antonnyman on 23/10/15.
 */
public class DebtsResult {

    List<Debt> debts;

    public DebtsResult(List<Debt> debts) {
        this.debts = debts;
    }

    public List<Debt> getDebts() {
        return debts;
    }

    public void setDebts(List<Debt> debts) {
        this.debts = debts;
    }
}
