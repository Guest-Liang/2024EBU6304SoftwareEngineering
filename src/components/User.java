package components;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.*;

/**
 * The User class provides a user object.
 * Title : User.java
 * Description:
 * The class provides a user object.
 * It contains various attributes of a user, such as password, whether the user is a parent, relatives, account type, username, and saving goals.
 * It also provides a method to convert user data to JSON format.
 */
public class User {
    private String password;
    private boolean isParent;
    private String relatives;
    private Map<String, Integer> accountType;
    private String username;
    private int SavingGoals;

    /**
     * Convert user data to JSON format.
     * @param user User object
     * @return JSON object
     */
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

    /**
     * Default constructor and initialize accountType
     */
    public User() {
        accountType = new HashMap<>();
    }

    /**
     * Constructor with parameters
     * @param password Password
     * @param isParent Whether the user is a parent
     * @param relatives Relatives
     * @param accountType Account type
     * @param username Username
     * @param SavingGoals Saving goals
     */
    public User(String password, boolean isParent, String relatives, Map<String, Integer> accountType, String username, int SavingGoals) {
        this.password = password;
        this.isParent = isParent;
        this.relatives = relatives;
        this.accountType = accountType;
        this.username = username;
        this.SavingGoals = SavingGoals;
    }

    // getters and setters

    /**
     * Get password
     * @return Password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set password
     * @param password Password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Check whether the user is a parent
     * @return Whether the user is a parent
     */
    public boolean isParent() {
        return isParent;
    }

    /**
     * Set whether the user is a parent
     * @param isParent Whether the user is a parent
     */
    public void setParent(boolean isParent) {
        this.isParent = isParent;
    }

    /**
     * Get relatives
     * @return Relatives
     */
    public String getRelatives() {
        return relatives;
    }

    /**
     * Set relatives
     * @param relatives Relatives
     */
    public void setRelatives(String relatives) {
        this.relatives = relatives;
    }

    /**
     * Get saving balance
     * @return Saving balance
     */
    public int getSavingBalance() {
        return accountType.get("saving");
    }

    /**
     * Set saving balance
     * @param savingBalance Saving balance
     */
    public void setSavingBalance(int savingBalance) {
        this.accountType.put("saving", savingBalance);
    }

    /**
     * Get current balance
     * @return Current balance
     */
    public int getCurrentBalance() {
        return accountType.get("current");
    }

    /**
     * Set current balance
     * @param currentBalance Current balance
     */
    public void setCurrentBalance(int currentBalance) {
        this.accountType.put("current", currentBalance);
    }

    /**
     * Get account type
     * @return Account type
     */
    public Map<String, Integer> getAccountType() {
        return accountType;
    }

    /**
     * Set account type
     * @param accountType Account type
     */
    public void setAccountType(Map<String, Integer> accountType) {
        this.accountType = accountType;
    }

    /**
     * Get username
     * @return Username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set username
     * @param username Username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get saving goals
     * @return Saving goals
     */
    public int getSavingGoals() {
        return SavingGoals;
    }

    /**
     * Set saving goals
     * @param SavingGoals Saving goals
     */
    public void setSavingGoals(int SavingGoals) {
        this.SavingGoals = SavingGoals;
    }
}
