package xyz.alto.debtfriend.debt.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import se.dromt.papper.ViewBuilder;
import xyz.alto.debtfriend.R;

/**
 * Created by antonnyman on 25/11/15.
 */
public class CalculateAndSaveDebtView extends LinearLayout {

    @Bind(R.id.view_calculate_and_save_list) ListView mListView;
    @Bind(R.id.view_calculate_and_save_radiogroup) RadioGroup mRadioGroup;
    @Bind(R.id.view_calculate_and_save_I_owe_them) RadioButton mIOweThem;
    @Bind(R.id.view_calculate_and_save_they_owe_me) RadioButton mTheyOweMe;
    @Bind(R.id.view_calculate_and_save_total) TextView mTotal;
    @Bind(R.id.view_calculate_and_save_button) Button mSave;


    public CalculateAndSaveDebtView(Context context) {
        super(context);
        LayoutInflater.from(getContext()).inflate(R.layout.view_calculate_and_save, this, true);
        ButterKnife.bind(this);
    }

    public static class Builder extends ViewBuilder {
        @Override
        protected View build(Context context, ViewGroup container) {
            return new CalculateAndSaveDebtView(context);
        }
    }

}
