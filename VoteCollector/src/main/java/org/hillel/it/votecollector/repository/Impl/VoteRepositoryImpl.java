package org.hillel.it.votecollector.repository.Impl;

import org.hillel.it.votecollector.IOopperations.DatabaseIO;
import org.hillel.it.votecollector.model.entity.Subject;
import org.hillel.it.votecollector.model.entity.Vote;
import org.hillel.it.votecollector.model.entity.VoteSubject;
import org.hillel.it.votecollector.model.search.VoteSearchCriteria;
import org.hillel.it.votecollector.repository.VoteRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 29.11.13
 * Time: 17:21
 */
@Service("VoteRepositoryImpl")
public class VoteRepositoryImpl implements VoteRepository {
    private List<Vote> votes;
    private List<VoteSubject> voteSubjects;
    String votesFile = "votes.dat";
    String voteSubjectFile = "voteSubject.dat";
    private AtomicBoolean isVotesChanged = new AtomicBoolean(false);
    private ExecutorService executorService;

    public VoteRepositoryImpl() {
        this.votes = new CopyOnWriteArrayList<>();
        this.voteSubjects = new CopyOnWriteArrayList<>();

        votes.addAll((List<Vote>) DatabaseIO.load(votesFile));
        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new SaveThread());
    }

    @Override
    public List<Vote> searchVotes(VoteSearchCriteria searchCriteria) {
        return new ArrayList<>(votes);
    }

    @Override
    public int countVotes() {
        return votes.size();
    }

    @Override
    public Vote getVoteById(int voteId) {
        for (Vote vote : votes)
            if (vote != null && vote.getId() == voteId) return vote;
        return null;
    }

    @Override
    public void saveVote(Vote vote) {
        if (vote == null) return;
        Vote vt = getVoteById(vote.getId());
        if (vt == null) {
            int lastId = findLastId();
            vote.setId(++lastId);
            votes.add(vote);
        } else vt.save(vote);
        isVotesChanged.set(true);
    }

    private int findLastId() {
        int lastId = 0;

        for (Vote vote : votes) {
            if (vote.getId() > lastId) {
                lastId = vote.getId();
            }
        }
        return lastId;
    }

    @Override
    public void deleteVote(int voteId) {
        Vote vote = getVoteById(voteId);
        if (vote == null)
            throw new RuntimeException("Vote not found");
        votes.remove(vote);
        isVotesChanged.set(true);

    }

    @Override
    public List<Vote> getActiveVotes() {
        List<Vote> result = new ArrayList<>();
        for (Vote vote : votes)
            if (vote.isPublished() && !vote.isEnded()) result.add(vote);
        return result;
    }

    @Override
    public List<Subject> getSubjectsByVote(int voteId) {
        List<Subject> result = new ArrayList<>();
        for (VoteSubject voteSubject : voteSubjects) {
            if (voteSubject != null) {
                if (voteSubject.getVote() != null) {
                    if (voteSubject.getVote().getId() == voteId) {
                        result.add(voteSubject.getSubject());
                    }
                }
            }
        }
        return result;
    }

    @Override
    public void addSubjectToVote(int voteId, List<Subject> subjects) {
        if (subjects == null) {
            throw new RuntimeException("Subject list should not be null");
        }
        Vote vote = getVoteById(voteId);
        if (vote == null) {
            throw new RuntimeException("No vote with such ID");
        }
        for (Subject subject : subjects) {
            if (subject != null) {
                VoteSubject voteSubject = new VoteSubject();
                voteSubject.setVote(vote);
                voteSubject.setSubject(subject);
                voteSubjects.add(voteSubject);
            }
        }
    }


    @Override
    public List<Vote> getVotesBySubject(int subjectId) {
        List<Vote> result = new ArrayList<>();
        for (VoteSubject voteSubject : voteSubjects) {
            if (voteSubject != null &&
                    voteSubject.getSubject() != null &&
                    voteSubject.getSubject().getId() == subjectId &&
                    voteSubject.getVote() != null) {
                result.add(voteSubject.getVote());

            }
        }
        return result;
    }

    @PreDestroy

    public void shutDown() {
        executorService.shutdownNow();
        while (!executorService.isTerminated()) {
        }
    }

    class SaveThread implements Runnable {
        public void run() {
            try {
                while (true) {
                    if (isVotesChanged.get()) {
                        isVotesChanged.set(false);
                        DatabaseIO.save(votes, votesFile);
                        DatabaseIO.save(voteSubjects, voteSubjectFile);
                    }
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                System.out.println("Perform shutdown");
                DatabaseIO.save(votes, votesFile);
                DatabaseIO.save(voteSubjects, voteSubjectFile);

            }
        }

    }
}
