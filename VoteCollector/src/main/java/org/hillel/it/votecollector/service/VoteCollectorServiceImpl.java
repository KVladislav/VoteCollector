package org.hillel.it.votecollector.service;


import org.hillel.it.votecollector.model.entity.*;
import org.hillel.it.votecollector.model.search.PersonSearchCriteria;
import org.hillel.it.votecollector.model.search.SiteSearchCriteria;
import org.hillel.it.votecollector.model.search.VoteSearchCriteria;
import org.hillel.it.votecollector.repository.Impl.PersonRepositoryImpl;
import org.hillel.it.votecollector.repository.Impl.SiteRepositoryImpl;
import org.hillel.it.votecollector.repository.Impl.SubjectRepositoryImpl;
import org.hillel.it.votecollector.repository.Impl.VoteRepositoryImpl;
import org.hillel.it.votecollector.repository.PersonRepository;
import org.hillel.it.votecollector.repository.SiteRepository;
import org.hillel.it.votecollector.repository.SubjectRepository;
import org.hillel.it.votecollector.repository.VoteRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 09.11.13
 * Time: 16:15
 */
public class VoteCollectorServiceImpl implements VoteCollectorService {
    PersonRepository personRepository;
    SiteRepository siteRepository;
    VoteRepository voteRepository;
    SubjectRepository subjectRepository;

    public VoteCollectorServiceImpl(PersonRepository personRepository, SiteRepository siteRepository, VoteRepository voteRepository, SubjectRepository subjectRepository) {
        this.personRepository = personRepository;
        this.siteRepository = siteRepository;
        this.voteRepository = voteRepository;
        this.subjectRepository = subjectRepository;
    }

    public VoteCollectorServiceImpl() {
        this.personRepository = new PersonRepositoryImpl();
        this.siteRepository = new SiteRepositoryImpl();
        this.voteRepository = new VoteRepositoryImpl();
        this.subjectRepository = new SubjectRepositoryImpl();
    }

    @Override
    public List<Vote> searchVotes(VoteSearchCriteria searchCriteria) {
        return voteRepository.searchVotes(searchCriteria);
    }

    @Override
    public Vote getVoteById(int voteId) {
        return voteRepository.getVoteById(voteId);
    }

    @Override
    public void saveVote(Vote vote, int userId) {
        User user = personRepository.getUser(userId);
        if (user == null)
            throw new RuntimeException("User not found");
        voteRepository.saveVote(vote);
        user.addVote(vote);
    }

    @Override
    public void deleteVote(int voteId, int userId) {
        User user = personRepository.getUser(userId);
        if (user == null)
            throw new RuntimeException("User not found");
        user.removeVote(voteId);
        voteRepository.deleteVote(voteId);
    }

    @Override
    public List<Vote> getActiveVotes() {
        return voteRepository.getActiveVotes();
    }

    @Override
    public List<Subject> getSubjectsByVote(int voteId) {
        return voteRepository.getSubjectsByVote(voteId);
    }

    @Override
    public void addSubjectToVote(int voteId, List<Subject> subject) {
        voteRepository.addSubjectToVote(voteId, subject);
    }

    @Override
    public List<Vote> getVotesBySubject(int subjectId) {
        return voteRepository.getVotesBySubject(subjectId);
    }

    @Override
    public void addSubjectToSite(int siteId, List<Subject> subject) {
        siteRepository.addSubjectToSite(siteId, subject);

    }

    @Override
    public List<Subject> getSubjectsBySite(int siteId) {
        return siteRepository.getSubjectsBySite(siteId);
    }

    @Override
    public List<NetworkMemberSite> getSitesBySubject(int subjectId) {
        return siteRepository.getSitesBySubject(subjectId);
    }

    @Override
    public Subject getSubjectById(int subjectId) {
        return subjectRepository.getSubjectById(subjectId);
    }

    @Override
    public void saveSubject(Subject subject) {
        subjectRepository.saveSubject(subject);
    }

    @Override
    public void deleteSubject(int subjectId) {
        subjectRepository.deleteSubject(subjectId);
    }

    @Override
    public List<NetworkMemberSite> searchSites(SiteSearchCriteria searchCriteria) {
        return siteRepository.searchSites(searchCriteria);
    }

    @Override
    public NetworkMemberSite getSiteById(int siteId) {
        return siteRepository.getSiteById(siteId);
    }

    @Override
    public NetworkMemberSite getSiteByUrl(String url) {
        return siteRepository.getSiteByUrl(url);
    }

    @Override
    public void saveSite(NetworkMemberSite site, int userId) {
        User user = personRepository.getUser(userId);
        if (user == null)
            throw new RuntimeException("User not found");
        siteRepository.saveSite(site);
        user.addSite(site);
    }

    @Override
    public void deleteSite(int siteId, int userId) {
        User user = personRepository.getUser(userId);
        if (user == null)
            throw new RuntimeException("User not found");
        user.removeSite(siteId);
        siteRepository.deleteSite(siteId);

    }

    @Override
    public List<User> searchUsers(PersonSearchCriteria searchCriteria) {
        return personRepository.searchUsers(searchCriteria);
    }

    @Override
    public List<User> getUsersByManager(int managerId) {
        return personRepository.getUsersByManager(managerId);
    }

    @Override
    public User getUser(int userId) {
        return personRepository.getUser(userId);
    }

    @Override
    public User getUser(String login, String password) {
        return personRepository.getUser(login, password);
    }

    @Override
    public void saveUser(User user, int managerId) {
        Manager manager = personRepository.getManager(managerId);
        if (manager == null)
            throw new RuntimeException("Manager not found");
        personRepository.saveUser(user);
        manager.addUser(user);
        personRepository.saveManager(manager);
    }

    @Override
    public void deleteUser(int userId, int managerId) {
        Manager manager = personRepository.getManager(managerId);
        if (manager == null)
            throw new RuntimeException("Manager not found");
        manager.removeUser(userId);
        personRepository.deleteUser(userId);
    }

    @Override
    public List<Manager> getAllManagers() {
        return personRepository.getAllManagers();
    }

    @Override
    public Manager getManager(int managerId) {
        return personRepository.getManager(managerId);
    }

    @Override
    public Manager getManager(String login, String password) {
        return personRepository.getManager(login, password);
    }

    @Override
    public void saveManager(Manager manager, int rootId) {
        Manager root = personRepository.getManager(rootId);
        if (root == null)
            throw new RuntimeException("RootManager not found");
        personRepository.saveManager(manager);
        root.addManager(manager);

    }

    @Override
    public void deleteManager(int managerId, int rootId) {
        Manager root = personRepository.getManager(rootId);
        if (root == null)
            throw new RuntimeException("RootManager not found");
        root.removeManager(managerId);
        personRepository.deleteManager(managerId);
    }


}
