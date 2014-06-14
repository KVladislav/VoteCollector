package org.hillel.it.votecollector.model.entity;


import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 16.11.13
 * Time: 22:00
 */
public class Subject extends BaseEntity implements Serializable {
    private String subject;
    private String description;

    public Subject() {
        super();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return super.toString() + "Subject{" +
                "subject='" + subject + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
