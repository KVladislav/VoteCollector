package org.hillel.it.votecollector.repository;

import org.hillel.it.votecollector.model.entity.NetworkMemberSite;
import org.hillel.it.votecollector.model.entity.Subject;
import org.hillel.it.votecollector.model.search.SiteSearchCriteria;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 13.11.13
 * Time: 23:15
 */
public interface SiteRepository {
    List<NetworkMemberSite> searchSites(SiteSearchCriteria searchCriteria);

    NetworkMemberSite getSiteById(int siteId);

    NetworkMemberSite getSiteByUrl(String url);

    void saveSite(NetworkMemberSite site);

    void deleteSite(int siteId);

    void addSubjectToSite(int siteId, List<Subject> subject);

    List<Subject> getSubjectsBySite(int siteId);

    List<NetworkMemberSite> getSitesBySubject(int subjectId);

    void shutDown();

    int countSites();
}
