package org.hillel.it.votecollector.repository.Impl;

import org.hillel.it.votecollector.IOopperations.DatabaseIO;
import org.hillel.it.votecollector.model.entity.NetworkMemberSite;
import org.hillel.it.votecollector.model.entity.SiteSubject;
import org.hillel.it.votecollector.model.entity.Subject;
import org.hillel.it.votecollector.model.search.SiteSearchCriteria;
import org.hillel.it.votecollector.repository.SiteRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 28.11.13
 * Time: 19:32
 */
@Service("SiteRepositoryImpl")
public class SiteRepositoryImpl implements SiteRepository {
    private List<NetworkMemberSite> sites;
    private String sitesFile = "sites.xml";
    private String siteSubjectsFile = "sitesSubject.dat";
    private AtomicBoolean isSitesChanged = new AtomicBoolean(false);
    private List<SiteSubject> siteSubjects;
    private ExecutorService executorService;


    public SiteRepositoryImpl() {
        this.sites = new CopyOnWriteArrayList<>();
        this.siteSubjects = new CopyOnWriteArrayList<>();

        sites.addAll(DatabaseIO.loadFromXML(sitesFile));

        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new SaveThread());
    }

    @Override
    public List<NetworkMemberSite> searchSites(SiteSearchCriteria searchCriteria) {
        return new ArrayList<>(sites);
    }

    @Override
    public NetworkMemberSite getSiteById(int siteId) {
        for (NetworkMemberSite site : sites)
            if (site != null && site.getId() == siteId) return site;
        return null;
    }

    @Override
    public NetworkMemberSite getSiteByUrl(String url) {
        if (url != null)
            for (NetworkMemberSite site : sites)
                if (site != null && url.equals(site.getUrl())) return site;
        return null;
    }

    @Override
    public void saveSite(NetworkMemberSite site) {
        if (site == null) return;
        NetworkMemberSite st = getSiteById(site.getId());
        if (st == null) {
            int lastId = findLastId();
            site.setId(++lastId);
            sites.add(site);
        } else st.save(site);
        isSitesChanged.set(true);
    }

    private int findLastId() {
        int lastId = 0;

        for (NetworkMemberSite site : sites) {
            if (site.getId() > lastId) {
                lastId = site.getId();
            }
        }
        return lastId;
    }

    @Override
    public void deleteSite(int siteId) {
        NetworkMemberSite site = getSiteById(siteId);
        if (site == null)
            throw new RuntimeException("Site not found");
        sites.remove(site);
        isSitesChanged.set(true);
    }

    @Override
    public List<Subject> getSubjectsBySite(int siteId) {
        List<Subject> result = new ArrayList<>();
        for (SiteSubject siteSubject : siteSubjects) {
            if (siteSubject != null) {
                if (siteSubject.getNetworkMemberSite() != null) {
                    if (siteSubject.getNetworkMemberSite().getId() == siteId) {
                        result.add(siteSubject.getSubject());
                    }
                }
            }
        }
        return result;
    }

    @Override
    public void addSubjectToSite(int siteId, List<Subject> subjects) {
        if (subjects == null) {
            throw new RuntimeException("Subject list should not be null");
        }
        NetworkMemberSite site = getSiteById(siteId);
        if (site == null) {
            throw new RuntimeException("No site with such ID");
        }
        for (Subject subject : subjects) {
            if (subject != null) {
                SiteSubject siteSubject = new SiteSubject();
                siteSubject.setNetworkMemberSite(site);
                siteSubject.setSubject(subject);
                siteSubjects.add(siteSubject);
            }
        }
    }


    @Override
    public List<NetworkMemberSite> getSitesBySubject(int subjectId) {
        List<NetworkMemberSite> result = new ArrayList<>();
        for (SiteSubject siteSubject : siteSubjects) {
            if (siteSubject != null &&
                    siteSubject.getSubject() != null &&
                    siteSubject.getSubject().getId() == subjectId &&
                    siteSubject.getNetworkMemberSite() != null) {
                result.add(siteSubject.getNetworkMemberSite());

            }
        }
        return result;
    }

    @PreDestroy
    public void shutDown() {
        executorService.shutdownNow();
        while (!executorService.isTerminated()) {
        }
    }

    @Override
    public int countSites() {
        return sites.size();
    }

    class SaveThread implements Runnable {
        public void run() {
            try {
                while (true) {
                    if (isSitesChanged.get()) {
                        isSitesChanged.set(false);
                        DatabaseIO.saveToXML(sites, sitesFile);
                        DatabaseIO.save(siteSubjects, siteSubjectsFile);
                    }
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                System.out.println("Perform shutdown");
                DatabaseIO.saveToXML(sites, sitesFile);
                DatabaseIO.save(siteSubjects, siteSubjectsFile);
            }
        }
    }
}
