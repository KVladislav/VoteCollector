package org.hillel.it.votecollector.repository;

import org.hillel.it.votecollector.model.entity.Subject;
import org.hillel.it.votecollector.model.entity.Vote;
import org.hillel.it.votecollector.model.search.VoteSearchCriteria;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 13.11.13
 * Time: 23:15
 */
public interface VoteRepository {
    List<Vote> searchVotes(VoteSearchCriteria searchCriteria);

    Vote getVoteById(int voteId);

    List<Vote> getActiveVotes();

    void saveVote(Vote vote);

    void deleteVote(int voteId);

    List<Subject> getSubjectsByVote(int voteId);

    void addSubjectToVote(int voteId, List<Subject> subjects);

    List<Vote> getVotesBySubject(int subjectId);

    void shutDown();

    int countVotes();
}
