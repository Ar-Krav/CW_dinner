package ark.cw_dinner.mainpart.orderingfood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ark.cw_dinner.R;
import ark.cw_dinner.database.tables.meals.MealObject;
import ark.cw_dinner.database.tables.ordering.OrderingObject;

/**
 * Created by Ar-Krav on 16.05.2018.
 */

public class TodayOrderingHistoryAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    List<OrderingObject> orderingObjList;

    TodayOrderingHistoryAdapter(Context context, List<OrderingObject> orderingObjList) {
        ctx = context;
        this.orderingObjList = orderingObjList;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return orderingObjList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderingObjList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = lInflater.inflate(R.layout.ordering_history_list_item, parent, false);
        }

        OrderingObject orderingObj = orderingObjList.get(position);

        MealObject mealObj = orderingObj.getMeal();

        TextView nameLabel = (TextView) convertView.findViewById(R.id.nameLabel);
        nameLabel.setText(mealObj.getName());

        TextView priceLabel = (TextView) convertView.findViewById(R.id.priceLabel);
        priceLabel.setText("" + mealObj.getCost());

        TextView aditionalInfoLabel = (TextView) convertView.findViewById(R.id.aditionalInfoLabel);
        aditionalInfoLabel.setText("Meal type: " + mealObj.getType());

        TextView descriptionLable = (TextView) convertView.findViewById(R.id.descriptionLabel);
        descriptionLable.setText(mealObj.getDescription());

        TextView totalNumLabel = (TextView) convertView.findViewById(R.id.totalNumLabel);
        totalNumLabel.setText("Total num: " + orderingObj.getValue());

        TextView totalCostLabel = (TextView) convertView.findViewById(R.id.totalCostLabel);
        totalCostLabel.setText("Total price: " + orderingObj.getCost());

        return listItemView;
    }
}
