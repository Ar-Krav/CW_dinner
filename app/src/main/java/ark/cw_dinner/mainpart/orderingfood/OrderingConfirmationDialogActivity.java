package ark.cw_dinner.mainpart.orderingfood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ark.cw_dinner.R;
import ark.cw_dinner.database.DBManager;
import ark.cw_dinner.database.tables.ordering.OrderingObject;
import ark.cw_dinner.mainpart.BasicActivity;

public class OrderingConfirmationDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering_confirmation_dialog);

        setTitle("Confirm Your Order");
        //getActionBar().setIcon(android.R.drawable.ic_dialog_alert);


        List<OrderingObject> orderedObjects = new ArrayList<>(OrderingFragment.selectedMeals.values());
        OrderingConformAdapter orderingConformAdapter = new OrderingConformAdapter(this, orderedObjects);
        ListView orderingResultList = (ListView) findViewById(R.id.orderingResultLis);
        orderingResultList.setAdapter(orderingConformAdapter);


        Button cancelBtn = (Button) findViewById(R.id.cancelAction);
        cancelBtn.setOnClickListener(getCancelClickListener());

        Button confirmBtn = (Button) findViewById(R.id.confirmAction);
        confirmBtn.setOnClickListener(getConfirmClickListener());
        confirmBtn.setText("buy for " + getTotalPrice() + " UAH");
    }

    private View.OnClickListener getCancelClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };
    }

    private View.OnClickListener getConfirmClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBManager dbManager = new DBManager(OrderingConfirmationDialogActivity.this);
                dbManager.makeOrderOfFood(OrderingFragment.selectedMeals);

                Intent intent = new Intent(OrderingConfirmationDialogActivity.this, BasicActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

                Toast.makeText(OrderingConfirmationDialogActivity.this, "Ordering done! Wait for deliverer.", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private int getTotalPrice(){
        int totalPrice = 0;
        for (OrderingObject orderingObject : OrderingFragment.selectedMeals.values()){
            totalPrice += orderingObject.getCost();
        }

        return totalPrice;
    }
}
