package components;

import com.alibaba.fastjson.*;

/**
 * The Transaction class provides a template for a transaction.
 * Title : Transaction.java
 * Description:
 * The class provides a template for a transaction.
 */
public class Transaction {
    private String transactionId;
    private String transactionTime;
    private int transactionAmount;
    private String transactionDescription;
    private String transactionMemberName;

    public static JSONObject TransactionData2Json(Transaction transaction) {
        JSONObject transactionJson = new JSONObject();
        transactionJson.put("transactionId", transaction.getTransactionId());
        transactionJson.put("transactionTime", transaction.getTransactionTime());
        transactionJson.put("transactionAmount", transaction.getTransactionAmount());
        transactionJson.put("transactionDescription", transaction.getTransactionDescription());
        transactionJson.put("transactionMemberName", transaction.getTransactionMemberName());
        return transactionJson;
    }

    public Transaction() {}

    public Transaction(String transactionId, String transactionTime, int transactionAmount, String transactionDescription, String transactionMemberName) {
        this.transactionId = transactionId;
        this.transactionTime = transactionTime;
        this.transactionAmount = transactionAmount;
        this.transactionDescription = transactionDescription;
        this.transactionMemberName = transactionMemberName;
    }

    // getters and setters

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    public int getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(int transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    public String getTransactionMemberName() {
        return transactionMemberName;
    }

    public void setTransactionMemberName(String transactionMemberName) {
        this.transactionMemberName = transactionMemberName;
    }
}
