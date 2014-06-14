package org.hillel.it.votecollector.model.entity;


import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 05.11.13
 * Time: 18:58
 */
public class AccountDetails extends BaseEntity implements Serializable {

    private String visaNumber;
    private int expMonth;
    private int expYear;

    public AccountDetails() {
        super();
    }


    public String getVisaNumber() {
        return visaNumber;
    }

    public void setVisaNumber(String visaNumber) {
        this.visaNumber = visaNumber;
    }

    public int getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(int expMonth) {
        this.expMonth = expMonth;
    }

    public int getExpYear() {
        return expYear;
    }

    public void setExpYear(int expYear) {
        this.expYear = expYear;
    }
}
