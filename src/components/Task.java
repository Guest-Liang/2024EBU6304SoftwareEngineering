package components;

import com.alibaba.fastjson.*;

/**
 * The Task class represents a task in a to-do list.
 * Title : Task.java
 * Description:
 * This class provides a task object, which includes properties such as title, description, due date, and status.
 * Main methods:
 * - setTaskName(String taskName): Sets the name of the task.
 * - setTaskPay(int taskPay): Sets the pay of the task.
 * 
 * Example usage:
 * Task myTask = new Task();
 * myTask.setTaskName("Buy groceries");
 * myTask.setTaskPay(20);
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

    /**
     * Converts a JSON object to a Task object.
     * @param task The JSON object representing a task
     * @return The Task object
     */
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

    /**
     * The constructor of the Task class.
     */
    public Task() {}
    
    /**
     * The constructor of the Task class.
     * It creates a task with the specified properties.
     * @param taskFinished The status of the task
     * @param taskHandleTime The time the task was handled
     * @param taskSetTime The time the task was set
     * @param taskName The name of the task
     * @param taskPay The pay of the task
     * @param taskId The id of the task
     * @param taskSetter The setter of the task
     * @param taskHandler The handler of the task
     */
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

    /**
     * Gets the status of the task.
     * @return The status of the task
     */
    public boolean getTaskFinished() {
        return taskFinished;
    }

    /**
     * Sets the status of the task.
     * @param taskFinished The status of the task
     */
    public void setTaskFinished(boolean taskFinished) {
        this.taskFinished = taskFinished;
    }

    /**
     * Gets the time the task was handled.
     * @return The time the task was handled
     */
    public String getTaskHandleTime() {
        return taskHandleTime;
    }

    /**
     * Sets the time the task was handled.
     * @param taskHandleTime The time the task was handled
     */
    public void setTaskHandleTime(String taskHandleTime) {
        this.taskHandleTime = taskHandleTime;
    }

    /**
     * Gets the time the task was set.
     * @return The time the task was set
     */
    public String getTaskSetTime() {
        return taskSetTime;
    }

    /**
     * Sets the time the task was set.
     * @param taskSetTime The time the task was set
     */
    public void setTaskSetTime(String taskSetTime) {
        this.taskSetTime = taskSetTime;
    }

    /**
     * Gets the name of the task.
     * @return The name of the task
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * Sets the name of the task.
     * @param taskName The name of the task
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * Gets the pay of the task.
     * @return The pay of the task
     */
    public int getTaskPay() {
        return taskPay;
    }

    /**
     * Sets the pay of the task.
     * @param taskPay The pay of the task
     */
    public void setTaskPay(int taskPay) {
        this.taskPay = taskPay;
    }

    /**
     * Gets the id of the task.
     * @return The id of the task
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * Sets the id of the task.
     * @param taskId The id of the task
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * Gets the setter of the task.
     * @return The setter of the task
     */
    public String getTaskSetter() {
        return taskSetter;
    }

    /**
     * Sets the setter of the task.
     * @param taskSetter The setter of the task
     */
    public void setTaskSetter(String taskSetter) {
        this.taskSetter = taskSetter;
    }

    /**
     * Gets the handler of the task.
     * @return The handler of the task
     */
    public String getTaskHandler() {
        return taskHandler;
    }

    /**
     * Sets the handler of the task.
     * @param taskHandler The handler of the task
     */
    public void setTaskHandler(String taskHandler) {
        this.taskHandler = taskHandler;
    }
}