package com.gihan.dias.mytasks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gihan.dias.mytasks.models.Task;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    private static final int TASK_ITEM = 0;
    private static final int HEADER = 1;
    private ArrayList<Object> list;
    private LayoutInflater inflater;

    public CustomAdapter(Context context, ArrayList<Object> list){
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemViewType(int position) {

        if(list.get(position) instanceof Task){
            return TASK_ITEM;
        }else{
            return HEADER;
        }
    }

    @Override
    public int getViewTypeCount() {
        // task list have 2 view tipes(item header and item list)
        return 2;
    }

    @Override
    public int getCount() {

        return list.size();
    }

    @Override
    public Object getItem(int position) {

        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            switch (getItemViewType(position)){
                case TASK_ITEM:
                    convertView = inflater.inflate(R.layout.item_task_list_view,null);
                break;

                case HEADER:
                    convertView = inflater.inflate(R.layout.item_task_list_header,null);
                    break;
            }
        }

        //Populate list item
        switch (getItemViewType(position)){
            case TASK_ITEM:
                TextView tvTaskName = convertView.findViewById(R.id.txtTaskName);
                TextView tvTaskType = convertView.findViewById(R.id.txtTaskType);
                TextView tvTaskdate = convertView.findViewById(R.id.txtTaskDate);

                tvTaskName.setText(((Task)list.get(position)).getTaskName());
                tvTaskType.setText(((Task)list.get(position)).getTaskType());
                tvTaskdate.setText(((Task)list.get(position)).getDueDate());
                break;

            case HEADER:
                TextView tvTaskHeader = convertView.findViewById(R.id.txtListHeader);
                tvTaskHeader.setText(((String)list.get(position)));
                break;
        }
        return convertView;
    }
}
