package org.hillel.it.votecollector.model.entity;


import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 27.10.13
 * Time: 20:33
 */
public abstract class BaseEntity implements Serializable {

    private int id;
    private Date creationDate;
    private Date changeDate;

    public BaseEntity() {
        this.creationDate = new Date();
        this.changeDate = new Date();
    }

    public BaseEntity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    protected void stateChanged() {
        this.changeDate = new Date();
    }


    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                ", creationDate=" + creationDate +
                '}';
    }
}
