package com.example.android.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameEditText = findViewById(R.id.name_edittext);
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);

        String name = nameEditText.getText().toString();
        boolean whippedCreamState = whippedCreamCheckBox.isChecked();
        boolean chocolateState = chocolateCheckBox.isChecked();
        int price = calculatePrice(quantity, whippedCreamState, chocolateState);

        String priceMessage = createOrderSummary(price, whippedCreamState, chocolateState, name);
        //String priceMessage = "Total: $" + price + "\nThank you!";
        //displayMessage(priceMessage);
        //displayPrice(quantity * 5);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     */
    private int calculatePrice(int quantity, boolean whippedCreamState, boolean chocolateState) {
        int price;
        if (whippedCreamState && chocolateState) {
            price = quantity * 5 + quantity * 1 + quantity * 2;
        } else if (whippedCreamState) {
            price = quantity * 5 + quantity * 1;
        } else if (chocolateState) {
            price = quantity * 5 + quantity * 2;
        } else {
            price = quantity * 5;
        }

        return price;
    }

    public void increment(View view) {
        if (quantity < 100) {
            quantity = quantity + 1;
            displayQuantity(quantity);
        } else {
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
        }
    }

    public void decrement(View view) {
        if (quantity > 1) {
            quantity = quantity - 1;
            displayQuantity(quantity);
        } else {
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    private String createOrderSummary(int price, boolean whippedCreamState, boolean chocolateState, String name) {
        return getString(R.string.name) + ": " + name + "\n + " + getString(R.string.add_whipped_cream_question) + " " + whippedCreamState + "\n " + getString(R.string.add_chocolate_question) + " " + chocolateState + "\n" + getString(R.string.quantity_order) + " " + quantity + "\n" + getString(R.string.total) + " $" + price + "\n" + getString(R.string.thank_you) + "!";
    }
}