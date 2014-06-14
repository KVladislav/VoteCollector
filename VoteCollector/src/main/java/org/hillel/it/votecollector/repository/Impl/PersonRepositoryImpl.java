package org.hillel.it.votecollector.repository.Impl;

import org.hillel.it.votecollector.IOopperations.DatabaseIO;
import org.hillel.it.votecollector.model.entity.AccountDetails;
import org.hillel.it.votecollector.model.entity.Manager;
import org.hillel.it.votecollector.model.entity.User;
import org.hillel.it.votecollector.model.search.PersonSearchCriteria;
import org.hillel.it.votecollector.repository.PersonRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 16.11.13
 * Time: 22:52
 */

@Service("PersonRepositoryImpl")
public class PersonRepositoryImpl implements PersonRepository {
    private List<Manager> managers;
    private List<User> users;
    private String usersFile = "users.dat";
    private String managersFile = "managers.dat";
    private ExecutorService executorService;

    private AtomicBoolean isUsersChanged = new AtomicBoolean(false);
    private AtomicBoolean isManagersChanged = new AtomicBoolean(false);

    public PersonRepositoryImpl() {
        managers = new CopyOnWriteArrayList<>();
        users = new CopyOnWriteArrayList<>();
        this.users.addAll((List<User>) DatabaseIO.load(usersFile));
        this.managers.addAll((List<Manager>) DatabaseIO.load(managersFile));

        if (this.managers.size() == 0) {
            Manager root = new Manager(new ArrayList<User>(), new ArrayList<Manager>(), true);
            root.setLogin("root");
            root.setPassword("root");
            root.setName("Alex");
            root.setSureName("Belov");
            root.setAddress("Odessa, Utesova str.");
            root.setMail("alex@odessa.ua");
            root.setTelephone("322322");
            root.setRoot(true);
            root.setAccountDetails(new AccountDetails());
            root.getAccountDetails().setVisaNumber("1234567812345678");
            root.getAccountDetails().setExpMonth(12);
            root.getAccountDetails().setExpYear(15);
            this.managers.add(root);
        }
        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new SaveThread());
    }


    public List<User> searchUsers(PersonSearchCriteria searchCriteria) {
        return new ArrayList<>(users);
    }

    @Override
    public User getUser(int userId) {
        for (User user : users)
            if (user != null && user.getId() == userId)
                return user;
        return null;
    }

    public List<User> getUsersByManager(int managerId) {
        return new ArrayList<>(getManager(managerId).getUsers());
    }

    @Override
    public User getUser(String login, String password) {
        for (User user : users) {
            if (user != null && login != null && password != null
                    && login.equals(user.getLogin()) && password.equals(user.getPassword()))
                return user;
        }
        return null;
    }

    @Override
    public void saveUser(User user) {
        if (user == null) return;
        User usr = getUser(user.getId());
        if (usr == null) {
            int lastId = findLastId(0);
            user.setId(++lastId);
            users.add(user);
        } else usr.save(user);
        isUsersChanged.set(true);
    }

    private int findLastId(int type) {
        int lastId = 0;
        if (type == 0) {
            for (User user : users) {
                if (user.getId() > lastId) {
                    lastId = user.getId();
                }
            }
        }
        if (type == 1) for (Manager manager : managers) {
            if (manager.getId() > lastId) {
                lastId = manager.getId();
            }
        }
        return lastId;
    }


    @Override
    public void deleteUser(int userId) {
        User user = getUser(userId);
        if (user == null)
            throw new RuntimeException("User not found");
        users.remove(user);
        isUsersChanged.set(true);

    }

    @Override
    public List<Manager> getAllManagers() {
        return new ArrayList<>(managers);
    }

    @Override
    public Manager getManager(int managerId) {
        for (Manager manager : managers)
            if (manager != null && manager.getId() == managerId) return manager;
        return null;
    }

    @Override
    public Manager getManager(String login, String password) {
        for (Manager manager : managers)
            if (manager != null && login != null && password != null
                    && login.equals(manager.getLogin()) && password.equals(manager.getPassword())) return manager;
        return null;
    }

    @Override
    public void saveManager(Manager manager) {
        if (manager == null) return;
        Manager mngr = getManager(manager.getId());
        if (mngr == null) {
            int lastId = findLastId(1);
            manager.setId(++lastId);
            managers.add(manager);
        } else mngr.save(manager);
        isManagersChanged.set(true);

    }

    @Override
    public void deleteManager(int managerId) {
        Manager manager = getManager(managerId);
        if (manager == null)
            throw new RuntimeException("Manager not found");
        managers.remove(manager);
        isManagersChanged.set(true);

    }

    @PreDestroy
    public void shutDown() {
        executorService.shutdownNow();
        while (!executorService.isTerminated()) {
        }
    }

    @Override
    public int countUsers() {
        return users.size();
    }

    @Override
    public int countManagers() {
        return managers.size();
    }

    class SaveThread implements Runnable {

        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    if (isManagersChanged.get()) {
                        isManagersChanged.set(false);
                        DatabaseIO.save(managers, managersFile);
                    }
                    if (isUsersChanged.get()) {
                        isUsersChanged.set(false);
                        DatabaseIO.save(users, usersFile);
                    }
                    TimeUnit.MILLISECONDS.sleep(1000);
                }
            } catch (InterruptedException e) {
                System.out.println("Perform shutdown");
                if (isManagersChanged.get()) {
                    DatabaseIO.save(managers, managersFile);
                }
                if (isUsersChanged.get()) {
                    DatabaseIO.save(users, usersFile);
                }
            }
        }
    }
}
