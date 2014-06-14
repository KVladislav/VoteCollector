package org.hillel.it.votecollector.repository;

import org.hillel.it.votecollector.model.entity.Subject;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 16.11.13
 * Time: 22:22
 */
public interface SubjectRepository {

    Subject getSubjectById(int id);

    void saveSubject(Subject subject);

    void deleteSubject(int id);

    void shutDown();

    int countSubjects();
}
