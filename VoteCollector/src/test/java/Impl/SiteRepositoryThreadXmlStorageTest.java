package Impl;

import org.hillel.it.votecollector.model.entity.NetworkMemberSite;
import org.hillel.it.votecollector.repository.Impl.SiteRepositoryImpl;
import org.hillel.it.votecollector.repository.SiteRepository;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static junit.framework.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 20.01.14
 * Time: 21:21
 */
public class SiteRepositoryThreadXmlStorageTest {
    @Test
    public void testSaveSite() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        SiteRepository repository = new SiteRepositoryImpl();
        int siteCounter = repository.countSites();
        ExecutorService executorService = Executors.newFixedThreadPool(101);
        for (int i = 0; i < 100; i++) {
            executorService.execute(new ThreadPut(repository, latch));
        }

        latch.countDown();
        Thread.sleep(100);
        repository.shutDown();
        repository = new SiteRepositoryImpl();
        int currientCounter = repository.countSites();
        repository.shutDown();

        assertEquals(100, currientCounter - siteCounter);


    }

    class ThreadPut implements Runnable {
        private SiteRepository repository;
        CountDownLatch latch;

        public ThreadPut(SiteRepository repository, CountDownLatch latch) {
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
            NetworkMemberSite site = new NetworkMemberSite();
            repository.saveSite(site);
        }

    }
}
