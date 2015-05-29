package com.julia.bookshelf.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.julia.bookshelf.R;

public class NavigationDrawerAdapter extends ArrayAdapter<DrawerMenuItem> {
    private final Context context;
    private final DrawerMenuItem[] menuItemsArray;

    public NavigationDrawerAdapter(Context context, DrawerMenuItem[] itemsArray) {
        super(context, R.layout.drawer_item, itemsArray);
        this.context = context;
        this.menuItemsArray = itemsArray;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.drawer_item, parent, false);
        TextView txtAction = (TextView) rowView.findViewById(R.id.txt_action);
        ImageView imgAction = (ImageView) rowView.findViewById(R.id.img_action);

        DrawerMenuItem menuItem = menuItemsArray[position];
        txtAction.setText(menuItem.getAction());
        imgAction.setImageResource(menuItem.getImg());

        return rowView;
    }
}