package org.hillel.it.votecollector.service;

import org.hillel.it.votecollector.model.entity.*;
import org.hillel.it.votecollector.model.search.PersonSearchCriteria;
import org.hillel.it.votecollector.model.search.SiteSearchCriteria;
import org.hillel.it.votecollector.model.search.VoteSearchCriteria;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 06.11.13
 * Time: 23:22
 */
public interface VoteCollectorService {

    List<Vote> searchVotes(VoteSearchCriteria searchCriteria);

    Vote getVoteById(int voteId);

    List<Vote> getActiveVotes();

    void saveVote(Vote vote, int userId);

    void deleteVote(int voteId, int userId);

    List<Subject> getSubjectsByVote(int voteId);

    void addSubjectToVote(int voteId, List<Subject> subject);

    List<Vote> getVotesBySubject(int subjectId);

    void addSubjectToSite(int siteId, List<Subject> subject);

    List<Subject> getSubjectsBySite(int siteId);

    List<NetworkMemberSite> getSitesBySubject(int subjectId);

    Subject getSubjectById(int id);

    void saveSubject(Subject subject);

    void deleteSubject(int id);

    List<NetworkMemberSite> searchSites(SiteSearchCriteria searchCriteria);

    NetworkMemberSite getSiteById(int siteId);

    NetworkMemberSite getSiteByUrl(String url);

    void saveSite(NetworkMemberSite site, int userId);

    void deleteSite(int siteId, int userId);

    List<User> getUsersByManager(int managerId);

    /**
     * Returns list of users that meet searchCriteria
     *
     * @param searchCriteria
     * @return List<User>
     */
    List<User> searchUsers(PersonSearchCriteria searchCriteria);

    User getUser(int userId);

    /**
     * Returns user by its login
     *
     * @param login
     * @return User object
     */
    User getUser(String login, String password);

    /**
     * Saves changes for given user, if user not found throws RuntimeException
     *
     * @param user
     */
    void saveUser(User user, int managerId);


//    /**
//     * Transfer unused shows from user's store to vote
//     *
//     * @param userId
//     * @param voteId
//     * @param amount
//     */
//    void chargeVoteByUser(int userId, int voteId, int amount);
//
//    /**
//     * Transfer not used shows from vote to user's store
//     *
//     * @param userId
//     * @param voteId
//     * @param amount
//     */
//    void chargeUserByVote(int userId, int voteId, int amount);
//
//    /**
//     * Unlinks list of users from a given manager, if users does not belongs to manager throws RuntimeException
//     *
//     * @param usersForRemove
//     * @param managerId
//     */
//    void removeUsersFromManager(List<User> usersForRemove, int managerId);
//
//    /**
//     * Link unlinked list of users to a given manager, if users arn't free throws RuntimeException
//     *
//     * @param freeUsers
//     * @param managerId
//     */
//    void addUsersToManager(List<User> freeUsers, int managerId);
//
//    /**
//     * Returns users that not linked to any manager
//     *
//     * @return
//     */
//    List<User> getNotLinkedUsers();

    /**
     * Removes user from database
     *
     * @param userId
     */
    void deleteUser(int userId, int managerId);


    /**
     * Returns a list of managers
     *
     * @return
     */
    List<Manager> getAllManagers();

    /**
     * Returns manager by its id
     *
     * @param managerId
     * @return
     */
    Manager getManager(int managerId);

    /**
     * Return manager by its login
     *
     * @param login
     * @return
     */
    Manager getManager(String login, String password);

    /**
     * Saves existing manager, if no manager exists with given id throws RuntimeException
     *
     * @param manager
     */
    void saveManager(Manager manager, int rootId);


    /**
     * Removes manager from database
     *
     * @param managerId
     */
    void deleteManager(int managerId, int rootId);

// For future release

//    /**
//     * Transfer unused shows from user's store to vote
//     *
//     * @param userId
//     * @param voteId
//     * @param amount
//     */
//    void chargeVoteByUser(int userId, int voteId, int amount);
//
//    /**
//     * Transfer not used shows from vote to user's store
//     *
//     * @param userId
//     * @param voteId
//     * @param amount
//     */
//    void chargeUserByVote(int userId, int voteId, int amount);
//
//    /**
//     * Unlinks list of users from a given manager, if users does not belongs to manager throws RuntimeException
//     *
//     * @param usersForRemove
//     * @param managerId
//     */
//    void removeUsersFromManager(List<User> usersForRemove, int managerId);
//
//    /**
//     * Link unlinked list of users to a given manager, if users arn't free throws RuntimeException
//     *
//     * @param freeUsers
//     * @param managerId
//     */
//    void addUsersToManager(List<User> freeUsers, int managerId);
//
//    /**
//     * Returns users that not linked to any manager
//     *
//     * @return
//     */
//    List<User> getNotLinkedUsers();
}
