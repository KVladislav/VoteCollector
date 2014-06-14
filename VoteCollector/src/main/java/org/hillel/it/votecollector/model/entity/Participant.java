package org.hillel.it.votecollector.model.entity;


import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 26.10.13
 * Time: 23:18
 */
public class Participant extends BaseEntity implements Serializable {
    private String uuid;
    private String ipAdress;

    public Participant() {
        super();
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


    public String getIpAdress() {
        return ipAdress;
    }

    public void setIpAdress(String ipAdress) {
        this.ipAdress = ipAdress;
    }
}
