package org.hillel.it.votecollector.model.entity;


import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 16.11.13
 * Time: 22:05
 */
public class SiteSubject implements Serializable {

    private NetworkMemberSite networkMemberSite;
    private Subject subject;


    public NetworkMemberSite getNetworkMemberSite() {
        return networkMemberSite;
    }

    public void setNetworkMemberSite(NetworkMemberSite networkMemberSite) {
        this.networkMemberSite = networkMemberSite;
    }


    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
