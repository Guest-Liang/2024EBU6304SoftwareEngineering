package components;

import com.alibaba.fastjson.*;

/**
 * The UserSession class provides a user session object.
 * Title : UserSession.java
 * Description:
 * The class provides a user session object.
 * @author Liang Zheyu
 * @version 0.1.0
 */
public class UserSession {
    private static UserSession instance;
    private JSONObject currentUser;
    private JSONArray currentInfo;
    private JSONArray currentTask;
    private JSONArray currentTransaction;

    private UserSession() {}

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void setCurrentUser(JSONObject currentUser) {
        this.currentUser = currentUser;
    }

    public JSONObject getCurrentUser() {
        return currentUser;
    }

    public void setCurrentInfo(JSONArray currentInfo) {
        this.currentInfo = currentInfo;
    }

    public JSONArray getCurrentInfo() {
        return currentInfo;
    }

    public void setCurrentTask(JSONArray currentTask) {
        this.currentTask = currentTask;
    }

    public JSONArray getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTransaction(JSONArray currentTransaction) {
        this.currentTransaction = currentTransaction;
    }

    public JSONArray getCurrentTransaction() {
        return currentTransaction;
    }
}