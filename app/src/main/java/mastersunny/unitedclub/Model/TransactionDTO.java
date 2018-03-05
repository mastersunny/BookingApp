package mastersunny.unitedclub.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ASUS on 1/25/2018.
 */

public class TransactionDTO implements Serializable{
    @SerializedName("transaction_id")
    private int transactionId;
    @SerializedName("offer")
    private StoreOfferDTO storeOfferDTO;
    @SerializedName("users")
    private UserDTO userDTO;
    @SerializedName("status")
    private int paidStatus;
    @SerializedName("created_at")
    private String transactionDate;
    @SerializedName("amount")
    private double amount;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public StoreOfferDTO getStoreOfferDTO() {
        return storeOfferDTO;
    }

    public void setStoreOfferDTO(StoreOfferDTO storeOfferDTO) {
        this.storeOfferDTO = storeOfferDTO;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public int getPaidStatus() {
        return paidStatus;
    }

    public void setPaidStatus(int paidStatus) {
        this.paidStatus = paidStatus;
    }

    @Override
    public String toString() {
        return "TransactionDTO{" +
                "transactionId=" + transactionId +
                ", storeOfferDTO=" + storeOfferDTO +
                ", userDTO=" + userDTO +
                ", paidStatus=" + paidStatus +
                ", transactionDate='" + transactionDate + '\'' +
                ", amount=" + amount +
                '}';
    }
}
