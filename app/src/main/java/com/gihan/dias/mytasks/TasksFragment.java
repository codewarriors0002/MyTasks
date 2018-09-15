package com.gihan.dias.mytasks;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.gihan.dias.mytasks.models.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TasksFragment extends Fragment {


    public TasksFragment() {

    }

    public static TasksFragment newInstance() {
        TasksFragment fragment = new TasksFragment();
        return fragment;
    }

    private ListView mlvTasks;
    private ArrayList<Object> taskList = new ArrayList<>();
    private List<Task> overdueTasks;
    private List<Task> laterTasks;
    private Calendar calendar;
    private SimpleDateFormat mdformat;
    private String curentDate;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tasks, container, false);
        mlvTasks = rootView.findViewById(R.id.listTask);
        calendar = Calendar.getInstance();
        mdformat = new SimpleDateFormat("yyyy-MM-dd");
        curentDate = mdformat.format(calendar.getTime()).trim();

        try {
            overdueTasks =  Task.find(Task.class, "date(TRIM(due_date))<date(?)", curentDate);
            laterTasks =  Task.find(Task.class, "date(TRIM(due_date))>date(?)", curentDate);

        }catch (Exception e){
            Toast toast = Toast.makeText(getContext(), R.string.something_wrong, Toast.LENGTH_SHORT);
            toast.show();
        }


        dividerTasks(overdueTasks, laterTasks);



        mlvTasks.setAdapter(new CustomAdapter(getContext(),taskList));
        return rootView;
    }

    private void dividerTasks(List<Task> overdueTasks, List<Task> laterTasks) {
        taskList.clear();
        addToTasksList(overdueTasks, "Overdue");
        addToTasksList(laterTasks, "Later");

    }

    private void addToTasksList(List<Task> tasks, String type) {
        if(tasks.size() > 0 && tasks != null  ){
            taskList.add(new String(type));
            for (Task task : tasks) {
                taskList.add(task);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
