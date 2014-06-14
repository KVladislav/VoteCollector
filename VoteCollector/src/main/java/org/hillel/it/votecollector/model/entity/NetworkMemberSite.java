package org.hillel.it.votecollector.model.entity;


import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 27.10.13
 * Time: 20:56
 */
public class NetworkMemberSite extends BaseEntity implements Serializable {
    private boolean isPublished;
    private String url;

    public NetworkMemberSite() {
        super();
        url = "";
    }

    public NetworkMemberSite(int id) {
        super(id);
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void save(NetworkMemberSite site) {
        super.stateChanged();
        this.isPublished = site.isPublished;
        this.url = site.url;
    }

    @Override
    public String toString() {
        return super.toString() + "NetworkMemberSite{" +
                "isPublished=" + isPublished +
                ", url='" + url + '\'' +
                '}';
    }
}
