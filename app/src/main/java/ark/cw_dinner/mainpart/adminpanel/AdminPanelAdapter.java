package ark.cw_dinner.mainpart.adminpanel;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import ark.cw_dinner.R;
import ark.cw_dinner.database.DBManager;
import ark.cw_dinner.database.tables.meals.MealObject;
import ark.cw_dinner.mainpart.orderinghistory.HistoryFragment;
import ark.cw_dinner.utils.UtilService;

/**
 * Created by Ar-Krav on 19.05.2018.
 */

public class AdminPanelAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> listDataHeader;
    private HashMap<String, List<MealObject>> childData;

    public AdminPanelAdapter(Context context, List<String> listDataHeader, HashMap<String, List<MealObject>> childData) {
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
        return  childData.get(listDataHeader.get(groupPosition)) == null ? 0 : childData.get(listDataHeader.get(groupPosition)).size();
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
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final int groupPositionForDel = groupPosition;
        final int childPositionForDel = childPosition;

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.admin_panel_list_item, null);
        }

        final MealObject mealObj = childData.get(listDataHeader.get(groupPosition)).get(childPosition);

        TextView nameLabel = (TextView) convertView.findViewById(R.id.nameLabel);
        nameLabel.setText(mealObj.getName());

        TextView priceLabel = (TextView) convertView.findViewById(R.id.priceLabel);
        priceLabel.setText("" + mealObj.getCost());

        TextView aditionalInfoLabel = (TextView) convertView.findViewById(R.id.aditionalInfoLabel);
        aditionalInfoLabel.setText("Available days: " + TextUtils.join(", ", mealObj.getAvailableInDays()));

        TextView descriptionLable = (TextView) convertView.findViewById(R.id.descriptionLabel);
        descriptionLable.setText(mealObj.getDescription());

        Button delBtn = (Button) convertView.findViewById(R.id.delMealBtn);
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Delete meal item")
                        .setMessage("Do you want delete meal item?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Yes",  new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                DBManager dbManager = new DBManager(context);
                                dbManager.deleteMeal(mealObj.getMealId());

                                childData.get(listDataHeader.get(groupPositionForDel)).remove(childPositionForDel);
                                notifyDataSetChanged();


                                Toast.makeText(context, mealObj.getName() + " was deleted", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .show();
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
