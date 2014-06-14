package org.hillel.it.votecollector.model.entity;

import java.io.Serializable;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 26.10.13
 * Time: 23:00
 */
public class Manager extends Person implements Serializable {
    private boolean isRoot;
    private List<Manager> managers;
    private List<User> users;


    public Manager(List<User> users, List<Manager> managers, boolean isRoot) {
        super();
        this.users = users;
        if (users == null) this.users = new ArrayList<>();
        this.isRoot = isRoot;
        if (managers == null && isRoot) this.managers = new ArrayList<>();
        if (!isRoot) this.managers = Collections.EMPTY_LIST;

    }

    public Manager() {
        super();
        this.users = new ArrayList<>();
        isRoot = false;
        managers = new ArrayList<>();

    }

    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean root) {
        isRoot = root;
    }

    public List<Manager> getManagers() {
        if (this.isRoot) return Collections.unmodifiableList(managers);
        else return Collections.EMPTY_LIST;
    }

    public void addManager(Manager manager) {
        if (!this.isRoot)
            throw new RuntimeException("No rights to create manager");
        if (!managers.contains(manager))
            managers.add(manager);
    }

    public void removeManager(int managerId) {
        if (!this.isRoot)
            throw new RuntimeException("No rights to delete manager");
        for (Manager manager : managers)
            if (manager != null && manager.getId() == managerId) {
                managers.remove(manager);
                return;
            }
        throw new RuntimeException("Manager not found");
    }

    public List<User> getUsers() {
        return Collections.unmodifiableList(users);
    }

    public void addUser(User user) {
        if (!users.contains(user))
            users.add(user);
    }

    public void removeUser(int userId) {
        for (User user : users)
            if (user != null && user.getId() == userId) {
                users.remove(user);
                return;
            }
        throw new RuntimeException("User not found");
    }

    public void save(Manager manager) {
        super.save(manager);
        this.isRoot = manager.isRoot;
        this.managers = manager.managers;
        this.users = manager.users;
    }

    @Override
    public String toString() {
        return super.toString() + "Manager{" +
                "isRoot=" + isRoot +
                ", managers=" + managers +
                ", users=" + users +
                '}';
    }
}
