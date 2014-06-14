package org.hillel.it.votecollector.model.entity;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 26.10.13
 * Time: 22:44
 */
public class User extends Person implements Serializable {
    private boolean isVoteCastomer;
    private boolean isNetworkMember;
    private int freeShows;
    private List<Vote> voteList;
    private List<NetworkMemberSite> networkMemberSites;


    public void setFreeShows(int freeShows) {
        this.freeShows = freeShows;
    }

    public User(List<Vote> voteList, List<NetworkMemberSite> networkMemberSites) {
        super();
        this.voteList = voteList;
        if (voteList == null) this.voteList = new ArrayList<>();
        this.networkMemberSites = networkMemberSites;
        if (networkMemberSites == null) this.networkMemberSites = new ArrayList<>();
    }

    public User() {
        super();
        this.voteList = new ArrayList<>();
        this.networkMemberSites = new ArrayList<>();
    }


    public boolean isVoteCastomer() {
        return isVoteCastomer;
    }

    public void setVoteCastomer(boolean voteCastomer) {
        isVoteCastomer = voteCastomer;
    }

    public boolean isNetworkMember() {
        return isNetworkMember;
    }

    public void setNetworkMember(boolean networkMember) {
        isNetworkMember = networkMember;
    }


    public List<Vote> getVoteList() {
        if (!isVoteCastomer)
            throw new RuntimeException("User is not vote castomer");
        return Collections.unmodifiableList(voteList);
    }

    public void removeVote(int voteId) {
        if (!isVoteCastomer)
            throw new RuntimeException("User is not vote castomer");
        for (Vote vote : voteList)
            if (vote != null && vote.getId() == voteId) {
                voteList.remove(vote);
                return;
            }
        throw new RuntimeException("Vote not found");
    }

    public void removeVotes(List<Vote> votes) {
        if (!isVoteCastomer)
            throw new RuntimeException("User is not vote castomer");
        voteList.removeAll(votes);
    }

    public void addVote(Vote vote) {
        if (!isVoteCastomer)
            throw new RuntimeException("User is not vote castomer");
        if (!voteList.contains(vote))
            voteList.add(vote);
    }

    public void addVotes(List<Vote> votes) {
        if (!isVoteCastomer)
            throw new RuntimeException("User is not vote castomer");
        voteList.addAll(votes);
    }


    public List<NetworkMemberSite> getNetworkMemberSites() {
        if (!isNetworkMember)
            throw new RuntimeException("User is not network member");
        return Collections.unmodifiableList(networkMemberSites);
    }

    public void addSite(NetworkMemberSite site) {
        if (!isNetworkMember)
            throw new RuntimeException("User is not network member");
        if (!networkMemberSites.contains(site))
            networkMemberSites.add(site);
    }

    public void addSites(List<NetworkMemberSite> sites) {
        if (!isNetworkMember)
            throw new RuntimeException("User is not network member");
        networkMemberSites.addAll(sites);
    }

    public void removeSite(int siteId) {
        if (!isNetworkMember)
            throw new RuntimeException("User is not network member");
        for (NetworkMemberSite site : networkMemberSites)
            if (site != null && site.getId() == siteId) {
                networkMemberSites.remove(site);
                return;
            }
        throw new RuntimeException("Site not found");
    }

    public void removeSites(List<NetworkMemberSite> sites) {
        if (!isNetworkMember)
            throw new RuntimeException("User is not network member");
        networkMemberSites.removeAll(sites);
    }

    public void save(User user) {
        super.save(user);
        this.isVoteCastomer = user.isVoteCastomer();
        this.isNetworkMember = user.isNetworkMember();
        this.voteList = user.voteList;
        this.networkMemberSites = user.networkMemberSites;

    }

    @Override
    public String toString() {
        return super.toString() + "User{" +
                "isVoteCastomer=" + isVoteCastomer +
                ", isNetworkMember=" + isNetworkMember +
                ", voteList=" + voteList +
                ", networkMemberSites=" + networkMemberSites +
                '}';
    }

    public int getFreeShows() {
        if (!isVoteCastomer)
            throw new RuntimeException("User is not vote castomer");
        if (freeShows < 0) freeShows = 0;
        return freeShows;
    }

    public void addFreeShows(int freeShows) {
        if (!isVoteCastomer)
            throw new RuntimeException("User is not vote castomer");
        this.freeShows += freeShows;
        if (this.freeShows < 0) this.freeShows = 0;
    }
}
