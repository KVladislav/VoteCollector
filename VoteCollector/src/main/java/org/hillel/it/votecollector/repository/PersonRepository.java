package org.hillel.it.votecollector.repository;

import org.hillel.it.votecollector.model.entity.Manager;
import org.hillel.it.votecollector.model.entity.Person;
import org.hillel.it.votecollector.model.entity.User;
import org.hillel.it.votecollector.model.search.PersonSearchCriteria;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 16.11.13
 * Time: 21:33
 */
public interface PersonRepository {
    /**
     * Returns list of users that meet searchCriteria
     *
     * @param searchCriteria
     * @return List<User>
     */
    List<User> searchUsers(PersonSearchCriteria searchCriteria);

    List<User> getUsersByManager(int managerId);

    /**
     * Returns list of users that belongs to manager with a given Id
     *
     * @param managerId managerId
     * @return List<User>
     */

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
    void saveUser(User user);

    /**
     * Adds new user and links it to given manager, if user is already exist throws RuntimeException
     *
     * @param user      new user
     * @param managerId manager's id to link
     */

    void deleteUser(int userId);

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
    void saveManager(Manager manager);

    /**
     * Removes manager from database
     *
     * @param managerId
     */
    void deleteManager(int managerId);

    /**
     * perfom repository shutdown
     */
    void shutDown();

    /**
     * counts users in repository (used for tests)
     *
     * @return
     */
    int countUsers();

    /**
     * counts managers in repository (used for tests)
     *
     * @return
     */
    int countManagers();
}


