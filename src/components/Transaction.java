package components;

import com.alibaba.fastjson.*;

/**
 * The Transaction class provides a template for a transaction.
 * Title : Transaction.java
 * Description:
 * The class provides a template for a transaction.
 * It contains various attributes of a transaction, such as transaction ID, transaction time, transaction amount, transaction description, and transaction member name.
 * It also provides a method to convert transaction data to JSON format.
 */
public class Transaction {
    private String transactionId;
    private String transactionTime;
    private int transactionAmount;
    private String transactionDescription;
    private String transactionMemberName;

    /**
     * Convert transaction data to JSON format.
     * @param transaction Transaction data
     * @return JSON object
     */
    public static JSONObject TransactionData2Json(Transaction transaction) {
        JSONObject transactionJson = new JSONObject();
        transactionJson.put("transactionId", transaction.getTransactionId());
        transactionJson.put("transactionTime", transaction.getTransactionTime());
        transactionJson.put("transactionAmount", transaction.getTransactionAmount());
        transactionJson.put("transactionDescription", transaction.getTransactionDescription());
        transactionJson.put("transactionMemberName", transaction.getTransactionMemberName());
        return transactionJson;
    }

    /**
     * Default constructor
     */
    public Transaction() {}

    /**
     * Constructor with parameters
     * @param transactionId Transaction ID
     * @param transactionTime Transaction time
     * @param transactionAmount Transaction amount
     * @param transactionDescription Transaction description
     * @param transactionMemberName Transaction member name
     */
    public Transaction(String transactionId, String transactionTime, int transactionAmount, String transactionDescription, String transactionMemberName) {
        this.transactionId = transactionId;
        this.transactionTime = transactionTime;
        this.transactionAmount = transactionAmount;
        this.transactionDescription = transactionDescription;
        this.transactionMemberName = transactionMemberName;
    }

    // getters and setters

    /**
     * Get transaction ID
     * @return Transaction ID
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * Set transaction ID
     * @param transactionId Transaction ID
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * Get transaction time
     * @return Transaction time
     */
    public String getTransactionTime() {
        return transactionTime;
    }

    /**
     * Set transaction time
     * @param transactionTime Transaction time
     */
    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    /**
     * Get transaction amount
     * @return Transaction amount
     */
    public int getTransactionAmount() {
        return transactionAmount;
    }

    /**
     * Set transaction amount
     * @param transactionAmount Transaction amount
     */
    public void setTransactionAmount(int transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    /**
     * Get transaction description
     * @return Transaction description
     */
    public String getTransactionDescription() {
        return transactionDescription;
    }

    /**
     * Set transaction description
     * @param transactionDescription Transaction description
     */
    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    /**
     * Get transaction member name
     * @return Transaction member name
     */
    public String getTransactionMemberName() {
        return transactionMemberName;
    }

    /**
     * Set transaction member name
     * @param transactionMemberName Transaction member name
     */
    public void setTransactionMemberName(String transactionMemberName) {
        this.transactionMemberName = transactionMemberName;
    }
}
