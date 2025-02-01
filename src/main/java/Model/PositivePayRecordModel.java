package Model;

import java.time.LocalDate;
import java.util.Objects;

public class PositivePayRecordModel implements Comparable<PositivePayRecordModel> {

    private String bankDescription;
    private String accountNumber;
    private double amount;
    private String paymentNumber;
    private LocalDate postDate;
    private String payeeName;

    public PositivePayRecordModel() {
    }

    public PositivePayRecordModel(String bankDescription, String accountNumber, double amount, String paymentNumber, LocalDate postDate, String payeeName) {
        this.bankDescription = bankDescription;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.paymentNumber = paymentNumber;
        this.postDate = postDate;
        this.payeeName = payeeName;
    }

    public String getBankDescription() {
        return bankDescription;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public String getPaymentNumber() {
        return paymentNumber;
    }

    public LocalDate getPostDate() {
        return postDate;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setBankDescription(String bankDescription) {
        this.bankDescription = bankDescription;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setPaymentNumber(String paymentNumber) {
        this.paymentNumber = paymentNumber;
    }

    public void setPostDate(LocalDate postDate) {
        this.postDate = postDate;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    @Override
    public String toString() {
        return "CheckModel{" +
                "bankDescription='" + bankDescription + '\'' +
                ", \n accountNumber='" + accountNumber + '\'' +
                ", \n amount='" + amount + '\'' +
                ", \n paymentNumber='" + paymentNumber + '\'' +
                ", \n postDate='" + postDate + '\'' +
                ", \n payeeName='" + payeeName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;
        PositivePayRecordModel otherPositivePayRecordModel = (PositivePayRecordModel) obj;
        return Objects.equals(bankDescription, otherPositivePayRecordModel.bankDescription) &&
                Objects.equals(accountNumber, otherPositivePayRecordModel.accountNumber) &&
                Objects.equals(amount, otherPositivePayRecordModel.amount) &&
                Objects.equals(paymentNumber, otherPositivePayRecordModel.paymentNumber) &&
                Objects.equals(postDate, otherPositivePayRecordModel.postDate) &&
                Objects.equals(payeeName, otherPositivePayRecordModel.payeeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bankDescription, accountNumber, amount, paymentNumber, postDate, payeeName);
    }

    @Override
    public int compareTo(PositivePayRecordModel otherCheck){
        return this.postDate.compareTo(otherCheck.postDate);
    }
}
