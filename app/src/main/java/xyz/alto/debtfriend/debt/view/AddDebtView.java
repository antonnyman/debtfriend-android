package xyz.alto.debtfriend.debt.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import se.dromt.papper.ViewBuilder;
import xyz.alto.debtfriend.R;
import xyz.alto.debtfriend.utils.Helper;

/**
 * Created by antonnyman on 14/11/15.
 */
public class AddDebtView extends LinearLayout {

    @Bind(R.id.view_add_debt_currency) Spinner mCurrencies;
    List<String> mCurrencyList = new ArrayList<>();


    public AddDebtView(Context context) {
        super(context);

        LayoutInflater.from(getContext()).inflate(R.layout.view_add_debt, this, true);
        ButterKnife.bind(this);

        mCurrencies = new Spinner(getContext());
        for(Currency c : Helper.getAllCurrencies()) {
            mCurrencyList.add(c.getDisplayName() + " (" + c.getSymbol(Locale.getDefault()) + ")");
        }

        for(String s : mCurrencyList) {
            Log.d("Currency", s);
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, mCurrencyList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCurrencies.setAdapter(spinnerAdapter);


    }


    public static class Builder extends ViewBuilder {
        @Override
        protected View build(Context context, ViewGroup container) {
            return new AddDebtView(context);
        }
    }
}
