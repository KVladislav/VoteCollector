package org.hillel.it.votecollector.model.entity;


import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 05.11.13
 * Time: 22:37
 */
public abstract class Person extends BaseEntity implements Serializable {

    private String name;
    private String sureName;
    private String login;
    private String password;
    private String mail;
    private String telephone;
    private String address;
    private AccountDetails accountDetails;

    @Override
    public String toString() {
        return super.toString() + "Person{" +
                "address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", sureName='" + sureName + '\'' +
                ", login='" + login + '\'' +
                ", mail='" + mail + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }


    public Person() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSureName() {
        return sureName;
    }

    public void setSureName(String sureName) {
        this.sureName = sureName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String Mail) {
        this.mail = Mail;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public AccountDetails getAccountDetails() {
        return accountDetails;
    }

    public void setAccountDetails(AccountDetails accountDetails) {
        this.accountDetails = accountDetails;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void save(Person person) {
        super.stateChanged();
        this.name = person.name;
        this.sureName = person.sureName;
        this.login = person.login;
        this.password = person.password;
        this.mail = person.mail;
        this.telephone = person.telephone;
        this.address = person.address;
        this.accountDetails = person.accountDetails;
    }

}
