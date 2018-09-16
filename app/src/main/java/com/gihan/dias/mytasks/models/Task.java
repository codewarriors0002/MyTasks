package com.gihan.dias.mytasks.models;

import com.orm.SugarRecord;

public class Task extends SugarRecord<Task>{

    String taskName;
    String dueDate;
    String taskType;
    String time;

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Task() {
    }



    public Task(String taskName, String dueDate, String time, String taskType) {
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.time = time;
        this.taskType = taskType;

    }



    public String getTaskName() {
        return taskName;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getTime() { return time; }

    public String getTaskType() {
        return taskType;
    }
}
