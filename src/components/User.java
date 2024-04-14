package components;

import com.alibaba.fastjson.*;

/**
 * The User class provides a user object.
 * Title : User.java
 * Description:
 * The class provides a user object.
 * @author Liang Zheyu
 * @version 0.2.0
 */
public class User {
    private String password;
    private boolean isParent;
    private int balance;
    private String accountType;
    private String username;
    private int SavingGoals;

    // Convert user data to a jsonobject format
    public static JSONObject UserData2Json(User user) {
        JSONObject userJson = new JSONObject();
        userJson.put("password", user.getPassword());
        userJson.put("isParent", user.isParent());
        userJson.put("balance", user.getBalance());
        userJson.put("accountType", user.getAccountType());
        userJson.put("username", user.getUsername());
        userJson.put("SavingGoals", user.getSavingGoals());
        return userJson;
    }

    public User() {}

    public User(String password, boolean isParent, int balance, String accountType, String username, int SavingGoals) {
        this.password = password;
        this.isParent = isParent;
        this.balance = balance;
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

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
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
