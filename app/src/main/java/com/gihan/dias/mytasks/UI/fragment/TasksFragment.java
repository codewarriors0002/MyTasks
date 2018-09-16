package com.gihan.dias.mytasks.UI.fragment;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.gihan.dias.mytasks.CustomAdapter;
import com.gihan.dias.mytasks.R;
import com.gihan.dias.mytasks.UI.activity.MainActivity;
import com.gihan.dias.mytasks.models.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TasksFragment extends Fragment implements CustomAdapter.ButtonIntaractionListener {


    public TasksFragment() {

    }

    public static TasksFragment newInstance() {
        TasksFragment fragment = new TasksFragment();
        return fragment;
    }

    private ListView mlvTasks;
    private ArrayList<Object> taskList = new ArrayList<>();
    private List<Task> overdueTasks;
    private List<Task> todayTasks;
    private List<Task> laterTasks;
    private Calendar calendar;
    private SimpleDateFormat mdformat;
    private String curentDate;
    private CustomAdapter customAdapter;
    private FloatingActionButton fab;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tasks, container, false);
        //pass actionbar title to MainActivity method for set title
        ((MainActivity) getActivity()).setActionBarTitle(R.string.task_list);

        mlvTasks = rootView.findViewById(R.id.listTask);
        calendar = Calendar.getInstance();
        mdformat = new SimpleDateFormat("yyyy-MM-dd");
        curentDate = mdformat.format(calendar.getTime()).trim();
        fab = getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
        fab.setClickable(true);

        try {
            overdueTasks =  Task.find(Task.class, "date(TRIM(due_date))<date(?)", curentDate);
            todayTasks =  Task.find(Task.class, "date(TRIM(due_date)) = date(?)", curentDate);
            laterTasks =  Task.find(Task.class, "date(TRIM(due_date))>date(?)", curentDate);

        }catch (Exception e){
            Toast toast = Toast.makeText(getContext(), R.string.something_wrong, Toast.LENGTH_SHORT);
            toast.show();
        }


        dividerTasks(overdueTasks, todayTasks, laterTasks);

        customAdapter = new CustomAdapter(getContext(),taskList);
        customAdapter.setButtonIntaractionListener(this);
        mlvTasks.setAdapter(customAdapter);
        return rootView;
    }

    private void dividerTasks(List<Task> overdueTasks, List<Task> todayTasks, List<Task> laterTasks) {
        taskList.clear();
        addToTasksList(overdueTasks, "Overdue");
        addToTasksList(todayTasks, "Today");
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

    @Override
    public void loadAddEditTaskFragment( Task  deieteTask) {

        Bundle bundle=new Bundle();
        bundle.putString("taskName",deieteTask.getTaskName());
        bundle.putString("dueDate",deieteTask.getDueDate());
        bundle.putString("taskType",deieteTask.getTaskType());
        bundle.putString("time",deieteTask.getTime());

        AddEditTaskFragment addEditTaskFragment = AddEditTaskFragment.newInstance();
        addEditTaskFragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_frame, addEditTaskFragment, "addEditTaskFragment");
        transaction.commit();
    }

    @Override
    public void deleteItemFromList(Task deleteListItem) {
        Task.executeQuery("DELETE FROM Task WHERE task_name = ?",deleteListItem.getTaskName());
        customAdapter.notifyDataSetChanged();
        getActivity().onBackPressed();
    }


}
