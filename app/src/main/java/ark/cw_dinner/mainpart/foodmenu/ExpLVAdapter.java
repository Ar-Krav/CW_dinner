package ark.cw_dinner.mainpart.foodmenu;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import ark.cw_dinner.R;
import ark.cw_dinner.database.tables.meals.MealObject;

/**
 * Created by Ar-Krav on 25.03.2018.
 */

public class ExpLVAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listDataHeader;
    private HashMap<String, List<MealObject>> childData;

    public ExpLVAdapter(Context context, List<String> listDataHeader, HashMap<String, List<MealObject>> childData) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.childData = childData;
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childData.get(listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childData.get(listDataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerToDisplay = listDataHeader.get(groupPosition);

        Log.d("TEST_LOG", "getGroupView: in group");

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.fmenu_list_group_header, null);

            Log.d("TEST_LOG", "getGroupView: in if");
        }

        TextView messageField = (TextView) convertView.findViewById(R.id.lblListHeader);
        messageField.setText(headerToDisplay);

        Log.d("TEST_LOG", "getGroupView: in group_: " + headerToDisplay);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.fmenu_list_item, null);
        }

        MealObject mealObj = childData.get(listDataHeader.get(groupPosition)).get(childPosition);

        TextView nameLabel = (TextView) convertView.findViewById(R.id.nameLabel);
            nameLabel.setText(mealObj.getName());

        TextView priceLabel = (TextView) convertView.findViewById(R.id.priceLabel);
            priceLabel.setText("" + mealObj.getCost());

        TextView daysLabel = (TextView) convertView.findViewById(R.id.daysAvailableLabel);
            daysLabel.setText(TextUtils.join(", ", mealObj.getAvailableInDays()));

        TextView descriptionLable = (TextView) convertView.findViewById(R.id.descriptionLabel);
            descriptionLable.setText(mealObj.getDescription());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
