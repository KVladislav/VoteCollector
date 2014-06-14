package org.hillel.it.votecollector.model.entity;


import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 16.11.13
 * Time: 22:08
 */
public class VoteSubject implements Serializable {
    private Vote vote;
    private Subject subject;


    public Vote getVote() {
        return vote;
    }

    public void setVote(Vote vote) {
        this.vote = vote;
    }


    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
