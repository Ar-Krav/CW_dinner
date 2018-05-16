package ark.cw_dinner.mainpart.orderingfood;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import ark.cw_dinner.R;
import ark.cw_dinner.database.tables.meals.MealObject;
import ark.cw_dinner.database.tables.ordering.OrderingObject;
import ark.cw_dinner.utils.UtilService;

/**
 * Created by Ar-Krav on 15.05.2018.
 */

public class OrderingListAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    List<MealObject> mealObjectList;

    OrderingListAdapter(Context context, List<MealObject> products) {
        ctx = context;
        mealObjectList = products;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mealObjectList.size();
    }

    @Override
    public Object getItem(int position) {
        return mealObjectList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = lInflater.inflate(R.layout.ordering_list_item, parent, false);
        }

        final MealObject MEAL_OBJ = mealObjectList.get(position);
        final EditText MEAL_NUM_LABEL = (EditText) listItemView.findViewById(R.id.portionNumberLabel);
        MEAL_NUM_LABEL.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String labelText = s.toString();

                if (labelText.isEmpty()){
                    s.append("0");
                }
            }
        });

        TextView nameLabel = (TextView) listItemView.findViewById(R.id.nameLabel);
        nameLabel.setText(MEAL_OBJ.getName());

        TextView priceLabel = (TextView) listItemView.findViewById(R.id.priceLabel);
        priceLabel.setText("" + MEAL_OBJ.getCost());

        TextView aditionalInfoLabel = (TextView) listItemView.findViewById(R.id.aditionalInfoLabel);
        aditionalInfoLabel.setText("Meal type: " + MEAL_OBJ.getType());

        TextView descriptionLable = (TextView) listItemView.findViewById(R.id.descriptionLabel);
        descriptionLable.setText(MEAL_OBJ.getDescription());

        Button addNewPortionButton = (Button) listItemView.findViewById(R.id.addNewPortion);
        addNewPortionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MEAL_NUM_LABEL.getText().toString().isEmpty()){
                    return;
                }

                if (OrderingFragment.selectedMeals.containsKey(MEAL_OBJ.getMealId())){
                    OrderingObject orderingObject = OrderingFragment.selectedMeals.get(MEAL_OBJ.getMealId());

                    int newMealsNum = getIntValueFromPortionNumLabel(MEAL_NUM_LABEL) + 1;
                    orderingObject.setValue(newMealsNum);
                    orderingObject.setCost(getTotalCost(newMealsNum, MEAL_OBJ.getCost()));

                    MEAL_NUM_LABEL.setText("" + newMealsNum);
                }
                else {
                    OrderingObject orderingObject = new OrderingObject();
                    orderingObject.setMeal(MEAL_OBJ);
                    orderingObject.setDate(UtilService.getCurrentDate());

                    int newMealsNum = getIntValueFromPortionNumLabel(MEAL_NUM_LABEL) + 1;
                    orderingObject.setValue(newMealsNum);
                    orderingObject.setCost(getTotalCost(newMealsNum, MEAL_OBJ.getCost()));

                    MEAL_NUM_LABEL.setText("" + newMealsNum);

                    OrderingFragment.selectedMeals.put(MEAL_OBJ.getMealId(), orderingObject);
                }
            }
        });

        Button delPortionButton = (Button) listItemView.findViewById(R.id.delOnePortion);
        delPortionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MEAL_NUM_LABEL.getText().toString().isEmpty() || getIntValueFromPortionNumLabel(MEAL_NUM_LABEL) == 0){
                    return;
                }

                OrderingObject orderingObject = OrderingFragment.selectedMeals.get(MEAL_OBJ.getMealId());

                int newMealsNum = getIntValueFromPortionNumLabel(MEAL_NUM_LABEL) - 1;
                orderingObject.setValue(newMealsNum);
                orderingObject.setCost(getTotalCost(newMealsNum, MEAL_OBJ.getCost()));

                MEAL_NUM_LABEL.setText("" + newMealsNum);

                if (newMealsNum == 0){
                    OrderingFragment.selectedMeals.remove(MEAL_OBJ.getMealId());
                }
            }
        });

        return listItemView;
    }

    private int getTotalCost(int portionNum, int portionCost){
        return portionCost * portionNum;
    }

    private int getIntValueFromPortionNumLabel(EditText editText){
        return Integer.valueOf(editText.getText().toString());
    }
}
