package com.gihan.dias.mytasks.models;

import com.orm.SugarRecord;

public class Task extends SugarRecord<Task>{

    String taskName;
     String dueDate;
     String taskType;

    public Task() {
    }

    public Task(String taskName, String dueDate, String taskType) {
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.taskType = taskType;
    }



    public String getTaskName() {
        return taskName;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getTaskType() {
        return taskType;
    }
}
