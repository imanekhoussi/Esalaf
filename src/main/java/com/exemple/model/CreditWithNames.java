package com.exemple.model;
import com.exemple.model.Credit;
import java.time.LocalDate;

public class CreditWithNames extends Credit {

    private String productName;
    private String clientName;

    public CreditWithNames() {
        super();
    }

    public CreditWithNames(String productName, String clientName, int qte, LocalDate date) {
        super(qte, date);
        this.productName = productName;
        this.clientName = clientName;
    }

    public CreditWithNames(Long credit_id, String productName, String clientName, int qte, LocalDate date) {
        super(credit_id, qte, date);
        this.productName = productName;
        this.clientName = clientName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public String toString() {
        return "CreditWithNames{" +
                "credit_id=" + this.getCredit_id() +
                ", productName='" + productName + '\'' +
                ", clientName='" + clientName + '\'' +
                ", qte=" + this.getQte() +
                ", date=" + this.getDate() +
                '}';
    }
}
