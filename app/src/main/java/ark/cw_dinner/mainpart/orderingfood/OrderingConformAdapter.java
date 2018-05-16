package ark.cw_dinner.mainpart.orderingfood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ark.cw_dinner.R;
import ark.cw_dinner.database.tables.ordering.OrderingObject;

/**
 * Created by Ar-Krav on 16.05.2018.
 */

public class OrderingConformAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;
    List<OrderingObject> orderingObjList;

    OrderingConformAdapter(Context context, List<OrderingObject> orderingObj) {
        ctx = context;
        orderingObjList = orderingObj;
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
            listItemView = lInflater.inflate(R.layout.confirm_ordering_result_item, parent, false);
        }

        OrderingObject orderingObj = orderingObjList.get(position);

        TextView nameLabel = (TextView) listItemView.findViewById(R.id.nameLabel);
            nameLabel.setText(orderingObj.getMeal().getName());

        TextView valueLabel = (TextView) listItemView.findViewById(R.id.valueLabel);
            valueLabel.setText("" + orderingObj.getValue() + "#");

        TextView totalPriceLabel = (TextView) listItemView.findViewById(R.id.totalCostLabel);
            totalPriceLabel.setText("" + orderingObj.getCost() + " UAH");

        return listItemView;
    }
}
