package com.gihan.dias.mytasks;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gihan.dias.mytasks.models.Task;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    private static final int TASK_ITEM = 0;
    private static final int HEADER = 1;
    private ArrayList<Object> list;
    private LayoutInflater inflater;
    private Context context;
    private ButtonIntaractionListener buttonIntaractionListener = null;
    private Task deleteListItem;


    public CustomAdapter(Context context, ArrayList<Object> list){
        this.list = list;
        this.context = context;
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
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
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
    public View getView(final int position, View convertView, ViewGroup parent) {

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
                CheckBox cbDelete = convertView.findViewById(R.id.cbDelete);
                LinearLayout contentTask = convertView.findViewById(R.id.cotentTask);

                cbDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {




                        if(buttonIntaractionListener!=null){
                            deleteListItem = (Task) list.get(position);
                            buttonIntaractionListener.deleteItemFromList(deleteListItem);
                        }


                    }
                });

                contentTask.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(buttonIntaractionListener!=null){
                            Task taskDetail = (Task) list.get(position);
                            buttonIntaractionListener.loadAddEditTaskFragment(taskDetail);
                        }
                    }
                });
                tvTaskName.setText(((Task)list.get(position)).getTaskName());
                tvTaskType.setText(((Task)list.get(position)).getTaskType());
                tvTaskdate.setText(((Task)list.get(position)).getDueDate() + " , " + ((Task)list.get(position)).getTime());
                break;

            case HEADER:
                TextView tvTaskHeader = convertView.findViewById(R.id.txtListHeader);
                tvTaskHeader.setText(((String)list.get(position)));
                break;
        }

        return convertView;
    }



    public void setButtonIntaractionListener(ButtonIntaractionListener buttonIntaractionListener) {
        this.buttonIntaractionListener = buttonIntaractionListener;
    }

    public interface ButtonIntaractionListener{
         void loadAddEditTaskFragment(Task deieteTask);
         void deleteItemFromList(Task deleteListItem);

    }
}
