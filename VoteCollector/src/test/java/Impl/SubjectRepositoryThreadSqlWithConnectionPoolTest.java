package Impl;

import org.hillel.it.votecollector.model.entity.Subject;
import org.hillel.it.votecollector.repository.Impl.SubjectRepositoryImpl;
import org.hillel.it.votecollector.repository.SubjectRepository;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static junit.framework.Assert.assertEquals;


/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 18.12.13
 * Time: 14:02
 */
public class SubjectRepositoryThreadSqlWithConnectionPoolTest {
    @Test
    public void testSaveSubject() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        SubjectRepository repository = new SubjectRepositoryImpl();
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        int subjectCounter = repository.countSubjects();
        for (int i = 0; i < 100; i++) {
            executorService.execute(new ThreadPut(repository, latch));
        }

        latch.countDown();
        Thread.sleep(1000);
        int currientCounter = repository.countSubjects();
        repository.shutDown();
        assertEquals(100, currientCounter - subjectCounter);


    }

    class ThreadPut implements Runnable {
        private SubjectRepository repository;
        CountDownLatch latch;

        public ThreadPut(SubjectRepository repository, CountDownLatch latch) {
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
            Subject subject = new Subject();
            repository.saveSubject(subject);
        }

    }

}