package mastersunny.unitedclub.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ASUS on 1/25/2018.
 */

public class TransactionDetailsDTO {
    @SerializedName("transaction_id")
    private long transactionId;
    @SerializedName("user_id")
    private long userId;
    @SerializedName("store_id")
    private long storeId;
    @SerializedName("date")
    private String date;
    @SerializedName("amount")
    private double amount;
    @SerializedName("status")
    private int status;

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TransactionDetailsDTO{" +
                "transactionId=" + transactionId +
                ", userId=" + userId +
                ", storeId=" + storeId +
                ", date='" + date + '\'' +
                ", amount=" + amount +
                ", status=" + status +
                '}';
    }
}
