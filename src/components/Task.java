package components;

import com.alibaba.fastjson.*;

/**
 * The Task class provides a task object.
 * Title : Task.java
 * Description:
 * The class provides a task object.
 * @author Liang Zheyu
 * @version 0.1.0
 */
public class Task {
    private boolean taskFinished;
    private String taskHandleTime;
    private String taskSetTime;
    private String taskName;
    private int taskPay;
    private String taskId;
    private String taskSetter;
    private String taskHandler;

    String[] columnNames = {"taskName", "taskId", "taskFinished", "taskSetTime", "taskHandleTime" ,"taskPay", "taskSetter", "taskHandler"};

    // Convert task data to a jsonobject format
    public static JSONObject TaskData2Json(Task task) {
        JSONObject taskJson = new JSONObject();
        taskJson.put("taskFinished", task.getTaskFinished());
        taskJson.put("taskHandleTime", task.getTaskHandleTime());
        taskJson.put("taskSetTime", task.getTaskSetTime());
        taskJson.put("taskName", task.getTaskName());
        taskJson.put("taskPay", task.getTaskPay());
        taskJson.put("taskId", task.getTaskId());
        taskJson.put("taskSetter", task.getTaskSetter());
        taskJson.put("taskHandler", task.getTaskHandler());
        return taskJson;
    }

    public Task() {}
    
    public Task(boolean taskFinished, String taskHandleTime, String taskSetTime, String taskName, int taskPay, String taskId, String taskSetter, String taskHandler) {
        this.taskFinished = taskFinished;
        this.taskHandleTime = taskHandleTime;
        this.taskSetTime = taskSetTime;
        this.taskName = taskName;
        this.taskPay = taskPay;
        this.taskId = taskId;
        this.taskSetter = taskSetter;
        this.taskHandler = taskHandler;
    }

    // getters and setters

    public boolean getTaskFinished() {
        return taskFinished;
    }

    public void setTaskFinished(boolean taskFinished) {
        this.taskFinished = taskFinished;
    }

    public String getTaskHandleTime() {
        return taskHandleTime;
    }

    public void setTaskHandleTime(String taskHandleTime) {
        this.taskHandleTime = taskHandleTime;
    }

    public String getTaskSetTime() {
        return taskSetTime;
    }

    public void setTaskSetTime(String taskSetTime) {
        this.taskSetTime = taskSetTime;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getTaskPay() {
        return taskPay;
    }

    public void setTaskPay(int taskPay) {
        this.taskPay = taskPay;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskSetter() {
        return taskSetter;
    }

    public void setTaskSetter(String taskSetter) {
        this.taskSetter = taskSetter;
    }

    public String getTaskHandler() {
        return taskHandler;
    }

    public void setTaskHandler(String taskHandler) {
        this.taskHandler = taskHandler;
    }
}