package com.gihan.dias.mytasks.UI.fragment;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.gihan.dias.mytasks.R;
import com.gihan.dias.mytasks.UI.activity.MainActivity;
import com.gihan.dias.mytasks.models.Task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddEditTaskFragment extends Fragment implements
        AdapterView.OnItemSelectedListener {


    public AddEditTaskFragment() {
        // Required empty public constructor
    }

    private Spinner spnrType;
    private String[] items;
    private Calendar myCalendar;
    private EditText edtTxtTask;
    private EditText edtTxtDueDate;
    private EditText edtTxtTime;
    private Button btnSave;
    private String listType;
    private String notEdtTaskName = null;
    private FloatingActionButton fab;
    private static Task task;

    public static AddEditTaskFragment newInstance() {
        AddEditTaskFragment fragment = new AddEditTaskFragment();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_edit_task, container, false);
        myCalendar = Calendar.getInstance();
        edtTxtTask = rootView.findViewById(R.id.edttxtTask);
        edtTxtDueDate = rootView.findViewById(R.id.edttxtDueDate);
        edtTxtTime = rootView.findViewById(R.id.edttxtTime);
        spnrType = rootView.findViewById(R.id.spnrType);
        btnSave = rootView.findViewById(R.id.btnSave);
        fab = getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        fab.setClickable(false);
        Bundle bundle=getArguments();
        spnrType.setOnItemSelectedListener(this);
        items = new String[] { "Default", "Personal", "Work" };

        //pass actionbar title to MainActivity method for set title
        ((MainActivity) getActivity()).setActionBarTitle(R.string.create_new_task);
        //Creating the ArrayAdapter instance having the  list
        ArrayAdapter aa = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,items);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spnrType.setAdapter(aa);

        if(bundle != null) {
            ((MainActivity) getActivity()).setActionBarTitle(R.string.update_task);
            notEdtTaskName = String.valueOf(bundle.getString("taskName"));
            edtTxtTask.setText(String.valueOf(bundle.getString("taskName")));
            edtTxtDueDate.setText(String.valueOf(bundle.getString("dueDate")));
            spnrType.setSelection(aa.getPosition(String.valueOf(bundle.getString("taskType"))));
            edtTxtTime.setText(String.valueOf(bundle.getString("time")));
        }


       final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateEdtTxtDueDate();
            }
        };

        edtTxtDueDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        final TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);
                updateEdtTxtTime();
            }
        };
        edtTxtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(getContext(),timeSetListener,
                        myCalendar.get(Calendar.HOUR_OF_DAY),
                        myCalendar.get(Calendar.MINUTE),false).show();
            }
        });




        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName = edtTxtTask.getText().toString().trim();
                String dueDate = edtTxtDueDate.getText().toString().trim();
                String time = edtTxtTime.getText().toString().trim();
                if(!validateRegisterUserInputs(taskName,dueDate)){
                if(notEdtTaskName != null){
                    List<Task> tasks =  Task.find(Task.class, "task_name = ? ", notEdtTaskName);
                   // Toast.makeText(getContext(),  task.get(0).getTaskName() , Toast.LENGTH_LONG).show();
                    if(tasks.size()>0 && tasks != null){

                        if(!validateRegisterUserInputs(taskName,dueDate)){
                            Task task1 = tasks.get(0);
                            task1.setTaskName(taskName);
                            task1.setTaskType(listType);
                            task1.setDueDate(dueDate);
                            task1.setTime(time);
                            task1.save();
                            Toast.makeText(getContext(),  R.string.update_task , Toast.LENGTH_LONG).show();
                            getActivity().onBackPressed();
                        }

                    }
                }else{

                    List<Task> tasks =  Task.find(Task.class, "task_name = ? ", taskName);
                    if(tasks.isEmpty() ){

                            Task task = new Task(taskName, dueDate, time, listType);
                            task.save();
                            Toast.makeText(getContext(),  R.string.save , Toast.LENGTH_LONG).show();
                            getActivity().onBackPressed();

                    }else{
                        Toast.makeText(getContext(),  R.string.task_name_exist , Toast.LENGTH_LONG).show();
                    }

                }
                }

            }
        });

        return rootView;
    }

    private boolean validateRegisterUserInputs(String taskName, String duedate){
        boolean error = false;

        if(taskName.equals("")){
            error = true;
            edtTxtTask.setError("Empty firstname");
        }

        if(duedate.equals("")){
            error = true;
            edtTxtDueDate.setError("Empty firstname");
        }

        return error;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       // Toast.makeText(getContext(),items[position] , Toast.LENGTH_LONG).show();
         listType = items[position].toString().trim();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void updateEdtTxtDueDate() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edtTxtDueDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateEdtTxtTime() {
        String myFormat = "hh:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edtTxtTime.setText(sdf.format(myCalendar.getTime()));
    }
}
