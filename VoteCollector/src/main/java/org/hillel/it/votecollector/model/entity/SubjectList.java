package org.hillel.it.votecollector.model.entity;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 11.11.13
 * Time: 13:14
 */
public class SubjectList implements Serializable {
    private List<Subject> subjectList;


    public SubjectList(List<Subject> subjectList) {
        this.subjectList = subjectList;
        if (subjectList == null) this.subjectList = new ArrayList<>();
    }

    public List<Subject> getSubjectList() {
        return Collections.unmodifiableList(subjectList);
    }

    public void addSubject(Subject subject) {
        subjectList.add(subject);
    }

    public void removeSubject(Subject subject) {
        subjectList.remove(subject);
    }


}
