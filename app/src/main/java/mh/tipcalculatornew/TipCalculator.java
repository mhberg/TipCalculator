//---------------------------------------------------------
// Obligatorisk Opgave 4                                   |
// author: Mads Heilberg                                   |
// email: mads.heilberg@gmail.com                          |
//---------------------------------------------------------
package mh.tipcalculatornew;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class TipCalculator extends AppCompatActivity {

    //local formatter objects
    private static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance();
    private static final NumberFormat PERCENT_FORMAT = NumberFormat.getPercentInstance();

    //instance variables for amounts and views
    private double billAmount = 0.0; //bill amount (set by user)
    private double percent = 0.15; //tip percentage
    private TextView amountTextView; //shows bill amount
    private TextView percentTextView; //shows tip percentage
    private TextView tipTextView; //shows tip amount
    private TextView totalTextView; //total bill amount

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //calls superclass onCreate
        setContentView(R.layout.activity_tip_calculator); //GUI inflating (layout resource)

        //set references to layout resources
        amountTextView = (TextView) findViewById(R.id.amountTextView);
        percentTextView = (TextView) findViewById(R.id.percentTextView);
        tipTextView = (TextView) findViewById(R.id.tipTextView);
        totalTextView = (TextView) findViewById(R.id.totalTextView);
        //sets format texts to 0
        tipTextView.setText(CURRENCY_FORMAT.format(0));
        totalTextView.setText(CURRENCY_FORMAT.format(0));

        //setup reference for amountEditText and listener
        EditText amountEditText = (EditText) findViewById(R.id.amountEditText);
        amountEditText.addTextChangedListener(amountEditTextListener);

        //setup reference for SeekBar and listener
        SeekBar percentSeekBar = (SeekBar) findViewById(R.id.percentSeekBar);
        percentSeekBar.setOnSeekBarChangeListener(seekBarListener);
    }

    /**
     * calculates tip and total amounts based on billAmount and formats + displays
     * @void
     */
    private void calculate(){
        //formats percent and displays in percentTextView
        percentTextView.setText(PERCENT_FORMAT.format(percent));

        //calculates tip and total
        double tip = billAmount * percent;
        double total = billAmount + tip;

        //display tip and total amount formatted
        tipTextView.setText(CURRENCY_FORMAT.format(tip));
        totalTextView.setText(CURRENCY_FORMAT.format(total));
    }

    //anon-inner class listener instance for the SeekBar progress changed events
    private final OnSeekBarChangeListener seekBarListener = new OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            percent = progress / 100.0; //set new percent from progress
            calculate();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {}
    };

    //anon-inner class listener instance for the amountEditText change text events
    private final TextWatcher amountEditTextListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try { //gets bill amount and display format value
                billAmount = Double.parseDouble(s.toString()) / 100.0;
                amountTextView.setText(CURRENCY_FORMAT.format(billAmount));
            } catch (NumberFormatException e){
                amountTextView.setText(""); //clears the textview
                billAmount = 0.0; //set bill amount to 0
            }
            calculate();
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };
}