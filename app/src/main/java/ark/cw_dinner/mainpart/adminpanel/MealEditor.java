package ark.cw_dinner.mainpart.adminpanel;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ark.cw_dinner.R;
import ark.cw_dinner.database.DBManager;
import ark.cw_dinner.database.tables.meals.MealObject;
import ark.cw_dinner.utils.TagsValues;

public class MealEditor extends AppCompatActivity {

    Boolean isForUpdate;
    MealObject mealObject;
    Map<String, Integer> mealTypeMap;
    Map<String, Integer> daysOfWeek;
    DBManager dbManager;

    EditText nameField;
    EditText costField;
    EditText descriptionField;
    Spinner typeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_editor);

        dbManager = new DBManager(this);

        mealTypeMap = dbManager.getMealTypes();
        daysOfWeek = dbManager.getDaysOfWeek();

        mealObject = (MealObject) getIntent().getSerializableExtra(TagsValues.MEAL_EDITOR_INTENT_EXTRA);
        if (mealObject == null){
            mealObject = new MealObject();
            mealObject.setAvailableInDays(new ArrayList<String>());
            isForUpdate = false;
        }
        else {
            isForUpdate = true;
        }

        changeAvailableDaysNameOnId();

        nameField = (EditText) findViewById(R.id.meal_name_field);
        nameField.setText(mealObject.getName());

        costField = (EditText) findViewById(R.id.meal_cost_field);
        costField.setText("" + mealObject.getCost());

        descriptionField = (EditText) findViewById(R.id.meal_description_field);
        descriptionField.setText(mealObject.getDescription());

        typeSpinner = (Spinner) findViewById(R.id.mealTypeSpinner);
        List<String> spinnerList = new ArrayList<String>(mealTypeMap.keySet());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
        typeSpinner.setPrompt("TitleHead");

        Button monBtn = findViewById(R.id.mondayDay);
        monBtn.setBackgroundColor(checkButtonForSelect("1") ? Color.GREEN : Color.RED);

        Button tueBtn = findViewById(R.id.tuesdayDay);
        tueBtn.setBackgroundColor(checkButtonForSelect("2") ? Color.GREEN : Color.RED);

        Button WedBtn = findViewById(R.id.wednesdayDay);
        WedBtn.setBackgroundColor(checkButtonForSelect("3") ? Color.GREEN : Color.RED);

        Button thurBtn = findViewById(R.id.thursdayDay);
        thurBtn.setBackgroundColor(checkButtonForSelect("4") ? Color.GREEN : Color.RED);

        Button fridayBtn = findViewById(R.id.fridayDay);
        fridayBtn.setBackgroundColor(checkButtonForSelect("5") ? Color.GREEN : Color.RED);

        Button satBtn = findViewById(R.id.saturdayDay);
        satBtn.setBackgroundColor(checkButtonForSelect("6") ? Color.GREEN : Color.RED);

        Button sunBtn = findViewById(R.id.sundayDay);
        sunBtn.setBackgroundColor(checkButtonForSelect("7") ? Color.GREEN : Color.RED);


        monBtn.setOnClickListener(getDaysClickListener());
        tueBtn.setOnClickListener(getDaysClickListener());
        WedBtn.setOnClickListener(getDaysClickListener());
        thurBtn.setOnClickListener(getDaysClickListener());
        fridayBtn.setOnClickListener(getDaysClickListener());
        satBtn.setOnClickListener(getDaysClickListener());
        sunBtn.setOnClickListener(getDaysClickListener());

        Button saveBtn = findViewById(R.id.createBtn);
            saveBtn.setOnClickListener(getSaveListener());

        Button cancelBtn = findViewById(R.id.cancelBtn);
            cancelBtn.setOnClickListener(getCancelListener());
    }

    private View.OnClickListener getSaveListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mealObject.setName(nameField.getText().toString());
                mealObject.setCost(Integer.valueOf(costField.getText().toString()));
                mealObject.setDescription(descriptionField.getText().toString());
                mealObject.setType("" + mealTypeMap.get(typeSpinner.getSelectedItem().toString()));

                Log.d("UPSERT_TEST_TAG", "meal: " + mealObject.getType());
                Log.d("UPSERT_TEST_TAG", "isUpdate: " + isForUpdate);

                dbManager.upsertMealsAndMenu(mealObject, isForUpdate);

                Toast.makeText(MealEditor.this, "Changes done", Toast.LENGTH_SHORT).show();
                finish();
            }
        };
    }

    private View.OnClickListener getCancelListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };
    }

    private View.OnClickListener getDaysClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.mondayDay:{
                        buttonAction("1", v);
                        break;
                    }
                    case R.id.tuesdayDay:{
                        buttonAction("2", v);
                        break;
                    }
                    case R.id.wednesdayDay:{
                        buttonAction("3", v);
                        break;
                    }
                    case R.id.thursdayDay:{
                        buttonAction("4", v);
                        break;
                    }
                    case R.id.fridayDay:{
                        buttonAction("5", v);
                        break;
                    }
                    case R.id.saturdayDay:{
                        buttonAction("6", v);
                        break;
                    }
                    case R.id.sundayDay:{
                        buttonAction("7", v);
                        break;
                    }
                }
            }
        };
    }

    private void buttonAction(String value, View btn){
        if (checkButtonForSelect(value)){
            mealObject.getAvailableInDays().remove(value);
            btn.setBackgroundColor(Color.RED);
        }else {
            mealObject.getAvailableInDays().add(value);
            btn.setBackgroundColor(Color.GREEN);
        }
    }

    private Boolean checkButtonForSelect(String value){
        return mealObject.getAvailableInDays().contains(value);
    }

    private void changeAvailableDaysNameOnId(){
        List<String> daysOfWeekInNumber = new ArrayList<>();

        for (String day : mealObject.getAvailableInDays()){
            if (!daysOfWeek.containsKey(day)){
                continue;
            }
            String dayID = daysOfWeek.get(day).toString();
            daysOfWeekInNumber.add(dayID);
        }

        mealObject.setAvailableInDays(daysOfWeekInNumber);
    }
}
