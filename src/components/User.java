package components;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.*;

/**
 * The User class provides a user object.
 * Title : User.java
 * Description:
 * The class provides a user object.
 */
public class User {
    private String password;
    private boolean isParent;
    private String relatives;
    private Map<String, Integer> accountType;
    private String username;
    private int SavingGoals;

    // Convert user data to a jsonobject format
    public static JSONObject UserData2Json(User user) {
        JSONObject userJson = new JSONObject();
        userJson.put("password", user.getPassword());
        userJson.put("isParent", user.isParent());
        userJson.put("relatives", user.getRelatives());
        JSONObject accoutTypeJson = new JSONObject();
        accoutTypeJson.put("saving", user.getSavingBalance());
        accoutTypeJson.put("current", user.getCurrentBalance());
        userJson.put("accountType", accoutTypeJson);
        userJson.put("username", user.getUsername());
        userJson.put("SavingGoals", user.getSavingGoals());
        return userJson;
    }

    public User() {
        accountType = new HashMap<>();
    }

    public User(String password, boolean isParent, String relatives, Map<String, Integer> accountType, String username, int SavingGoals) {
        this.password = password;
        this.isParent = isParent;
        this.relatives = relatives;
        this.accountType = accountType;
        this.username = username;
        this.SavingGoals = SavingGoals;
    }

    // getters and setters

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean isParent) {
        this.isParent = isParent;
    }

    public String getRelatives() {
        return relatives;
    }

    public void setRelatives(String relatives) {
        this.relatives = relatives;
    }

    public int getSavingBalance() {
        return accountType.get("saving");
    }

    public void setSavingBalance(int savingBalance) {
        this.accountType.put("saving", savingBalance);
    }

    public int getCurrentBalance() {
        return accountType.get("current");
    }

    public void setCurrentBalance(int currentBalance) {
        this.accountType.put("current", currentBalance);
    }

    public Map<String, Integer> getAccountType() {
        return accountType;
    }

    public void setAccountType(Map<String, Integer> accountType) {
        this.accountType = accountType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getSavingGoals() {
        return SavingGoals;
    }

    public void setSavingGoals(int SavingGoals) {
        this.SavingGoals = SavingGoals;
    }
}
