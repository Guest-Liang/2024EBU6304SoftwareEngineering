package components;

import com.alibaba.fastjson.*;

/**
 * The UserSession class provides a user session object.
 * Title : UserSession.java
 * Description:
 * The class provides a user session object, which contains the current user, current information, current task, and current transaction.
 * This class uses the singleton pattern to ensure that there is only one UserSession instance in the entire application.
 */
public class UserSession {
    private static UserSession instance;
    private JSONObject currentUser;
    private JSONArray currentInfo;
    private JSONArray currentTask;
    private JSONArray currentTransaction;

    /**
     * Private constructor to prevent instantiation.
     */
    private UserSession() {}

    /**
     * Get the instance of the UserSession class.
     * If the instance is null, create a new instance.
     * @return UserSession instance
     */
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    /**
     * Set the current user.
     * @param currentUser JSON object representing the current user
     */
    public void setCurrentUser(JSONObject currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Get the current user.
     * @return JSON object representing the current user
     */
    public JSONObject getCurrentUser() {
        return currentUser;
    }

    /**
     * Set the current information.
     * @param currentInfo JSON array representing the current information
     */
    public void setCurrentInfo(JSONArray currentInfo) {
        this.currentInfo = currentInfo;
    }

    /**
     * Get the current information.
     * @return JSON array representing the current information
     */
    public JSONArray getCurrentInfo() {
        return currentInfo;
    }

    /**
     * Set the current task.
     * @param currentTask JSON array representing the current task
     */
    public void setCurrentTask(JSONArray currentTask) {
        this.currentTask = currentTask;
    }

    /**
     * Get the current task.
     * @return JSON array representing the current task
     */
    public JSONArray getCurrentTask() {
        return currentTask;
    }

    /**
     * Set the current transaction.
     * @param currentTransaction JSON array representing the current transaction
     */
    public void setCurrentTransaction(JSONArray currentTransaction) {
        this.currentTransaction = currentTransaction;
    }

    /**
     * Get the current transaction.
     * @return JSON array representing the current transaction
     */
    public JSONArray getCurrentTransaction() {
        return currentTransaction;
    }
}