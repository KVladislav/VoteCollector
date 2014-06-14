package org.hillel.it.votecollector.model.entity;


import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 30.10.13
 * Time: 23:03
 */
public class ParticipantVote implements Serializable {
    private Participant participant;
    private Vote vote;


    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public Vote getVoteList() {
        return vote;
    }

    public void setVoteList(Vote voteList) {
        this.vote = voteList;
    }
}
