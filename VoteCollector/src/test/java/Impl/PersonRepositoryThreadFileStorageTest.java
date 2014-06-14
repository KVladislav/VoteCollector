package Impl;

import org.hillel.it.votecollector.model.entity.User;
import org.hillel.it.votecollector.repository.Impl.PersonRepositoryImpl;
import org.hillel.it.votecollector.repository.PersonRepository;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static junit.framework.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 16.12.13
 * Time: 21:06
 */
public class PersonRepositoryThreadFileStorageTest {

    @Test
    public void testSaveUser() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        PersonRepository repository = new PersonRepositoryImpl();
        int userCounter = repository.countUsers();
        ExecutorService executorService = Executors.newFixedThreadPool(101);
        for (int i = 0; i < 100; i++) {
            executorService.execute(new ThreadPut(repository, latch));
        }

        latch.countDown();
        Thread.sleep(1000);
        int currientCounter = repository.countUsers();
        repository.shutDown();
        assertEquals(100, currientCounter - userCounter);


    }

    class ThreadPut implements Runnable {
        private PersonRepository repository;
        CountDownLatch latch;

        public ThreadPut(PersonRepository repository, CountDownLatch latch) {
            this.latch = latch;

            if (repository == null) {
                throw new RuntimeException("empty rep");
            }
            this.repository = repository;
        }

        public void run() {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            User user = new User();
            repository.saveUser(user);
        }

    }
}
