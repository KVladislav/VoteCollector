package org.hillel.it.votecollector.model.entity;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 11.11.13
 * Time: 12:40
 */
public class Vote extends BaseEntity implements Serializable {
    private boolean isPublished;
    private boolean isEnded;
    private boolean isRadio;
    private Date endedDate;
    private int showsLimit;
    private String title;
    private List<String> questions;


    public void setEndedDate(Date endedDate) {
        this.endedDate = endedDate;
    }

    public void setShowsLimit(int showsLimit) {
        this.showsLimit = showsLimit;
    }

    public Vote(List<String> questions) {
        super();
        this.questions = questions;
        if (questions == null) questions = new ArrayList<>();
    }

    public Vote() {
        super();
        this.questions = new ArrayList<>();
    }


    public boolean isPublished() {
        if (this.showsLimit <= 0) this.setEnded(true);
        return isPublished;
    }

    public void setPublished(Boolean published) {
        isPublished = published;
    }

    public boolean isEnded() {
        if (this.showsLimit <= 0) this.setEnded(true);
        return isEnded;
    }

    public void setEnded(boolean ended) {
        isEnded = ended;
        if (this.showsLimit <= 0) isEnded = true;
        if (ended) this.endedDate = new Date();
        if (!ended) this.endedDate = null;
    }

    public boolean isRadio() {
        return isRadio;
    }

    public void setRadio(boolean radio) {
        isRadio = radio;
    }


    public Date getEndedDate() {
        return endedDate;
    }

    public int getShowsLimit() {
        if (this.showsLimit <= 0) this.setEnded(true);
        return showsLimit;
    }

    public void decShowsLimit() {
        this.showsLimit--;
    }

    public void addShowsLimit(int showsLimit) {
        this.showsLimit += showsLimit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public List<String> getQuestions() {
        return Collections.unmodifiableList(questions);
    }

    public void addQuestion(String question) {
        questions.add(question);
    }

    public void removeQuestion(String question) {
        questions.remove(question);
    }

    public void save(Vote vote) {
        this.isPublished = vote.isPublished;
        this.setEnded(vote.isEnded);
        this.isRadio = vote.isRadio;
        this.showsLimit = vote.showsLimit;
        this.title = vote.title;
        this.questions = new ArrayList<>(vote.questions);
        super.stateChanged();
    }
}
