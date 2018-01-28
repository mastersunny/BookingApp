package mastersunny.unitedclub.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ASUS on 1/25/2018.
 */

public class TransactionDTO {
    private long transactionId;
    private StoreOfferDTO storeOfferDTO = new StoreOfferDTO();
    private UserDTO userDTO = new UserDTO();
    private String date;
    private double paidAmount;
    private double dueAmount;
    private int status;

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public double getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(double dueAmount) {
        this.dueAmount = dueAmount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TransactionDTO{" +
                "transactionId=" + transactionId +
                ", storeOfferDTO=" + storeOfferDTO +
                ", userDTO=" + userDTO +
                ", date='" + date + '\'' +
                ", paidAmount=" + paidAmount +
                ", dueAmount=" + dueAmount +
                ", status=" + status +
                '}';
    }
}
