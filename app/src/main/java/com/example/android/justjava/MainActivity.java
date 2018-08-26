/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int quantity = 1;


    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100){
            // Show an error message as a toast
            Toast.makeText(this, getString(R.string.toast_error_message_100), Toast.LENGTH_SHORT).show();
            // Exit this method early because there is nothing left to do
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }


    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1){
            // Show an error message as a toast
            Toast.makeText(this, getString(R.string.toast_error_message_1), Toast.LENGTH_SHORT).show();
            // Exit this method early because there is nothing left to do
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }


    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        EditText nameField = (EditText) findViewById(R.id.name_field);
        String nameInput = nameField.getText().toString();

        int price = calculatePrice(hasWhippedCream,hasChocolate);

        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, nameInput);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.coffee_order_for
        ) + nameInput);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        displayMessage(priceMessage);
    }


    /**
     * Calculates the price of the order.
     * @param hasWhippedCream is whether or not the user wants whipped cream topping
     * @param hasChocolate is whether or not the user wants chocolate  topping
     *
     * @return total price
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        // Price of 1 cup of coffee
        int basePrice = 5;

        // Add 1$ if the user wants whipped cream
        if (hasWhippedCream) {
            basePrice = basePrice + 1;
        }
        // Add 2$ if the user wants chocolate
        if (hasChocolate) {
            basePrice = basePrice + 2;
        }

        // Calculate the total price by multiplying by quantity
        return basePrice * quantity;
    }


    /**
     * Creates a summary of the order.
     *
     * @param price  of the order
     * @param hasWhippedCream is whether or not the user wants whipped cream topping
     * @param nameInput of the customer
     * @return text summary
     *
     */
    private String createOrderSummary(int price, boolean hasWhippedCream, boolean hasChocolate, String nameInput) {
        String priceMessage = getString(R.string.submit_order_name) + nameInput;
        priceMessage = priceMessage + "\n" + getString(R.string.whipped_cream_yes_or_no) + (hasWhippedCream? getString(R.string.boolean_true) : getString(R.string.boolean_false));
        priceMessage = priceMessage + "\n" + getString(R.string.add_chocolate_yes_or_no) + (hasChocolate? getString(R.string.boolean_true) : getString(R.string.boolean_false));
        priceMessage = priceMessage + "\n" + getString(R.string.quantity) + ":" + quantity;
        priceMessage = priceMessage + "\n" + getString(R.string.total) + ":" + price + "$";
        priceMessage = priceMessage + "\n" + getString(R.string.thank_you);
        return priceMessage;
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

}
