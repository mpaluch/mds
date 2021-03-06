package org.datacite.mds.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import javax.validation.GroupSequence;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.log4j.Logger;
import org.datacite.mds.util.Utils;
import org.datacite.mds.validation.constraints.Doi;
import org.datacite.mds.validation.constraints.MatchDoiPrefix;
import org.datacite.mds.validation.constraints.MatchDomain;
import org.datacite.mds.validation.constraints.Unique;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findDatasetsByDoiEquals" })
@MatchDoiPrefix
@MatchDomain(groups = Dataset.SecondLevelConstraint.class)
@Unique(field = "doi")
@GroupSequence({ Dataset.class, Dataset.SecondLevelConstraint.class })
public class Dataset {
    
    private static Logger log4j = Logger.getLogger(Dataset.class);

    @NotNull
    @Doi
    @Column(unique = true)
    private String doi;

    @NotNull
    private Boolean isActive = true;

    private Boolean isRefQuality;

    @Min(100L)
    @Max(510L)
    private Integer lastLandingPageStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private Date lastLandingPageStatusCheck;

    private String lastMetadataStatus;

    @NotNull
    @ManyToOne(targetEntity = Datacentre.class)
    @JoinColumn
    private Datacentre datacentre;

    @Transient
    @URL
    private String url;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private Date updated;

    private static TypedQuery<Dataset> queryDatasetsByDatacentre(Datacentre datacentre) {
        EntityManager em = entityManager();
        TypedQuery<Dataset> q = em.createQuery("SELECT Dataset FROM Dataset AS dataset WHERE dataset.datacentre = :datacentre", Dataset.class);
        q.setParameter("datacentre", datacentre);
        return q;
    }

    public static List<Dataset> findDatasetEntriesByDatacentres(Datacentre datacentre, int firstResult, int maxResults) {
        TypedQuery<Dataset> q = queryDatasetsByDatacentre(datacentre);
        return q.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    public static List<Dataset> findDatasetsByDatacentres(Datacentre datacentre) {
        TypedQuery<Dataset> q = queryDatasetsByDatacentre(datacentre);
        return q.getResultList();
    }

    public static long countDatasetsByDatacentre(Datacentre datacentre) {
        EntityManager em = entityManager();
        TypedQuery<Long> q = em.createQuery("SELECT COUNT(*) FROM Dataset AS dataset WHERE dataset.datacentre = :datacentre", Long.class);
        q.setParameter("datacentre", datacentre);
        return q.getSingleResult();
    }

    @Transactional
    public void persist() {
        Date date = new Date();
        setCreated(date);
        setUpdated(date);
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }

    @Transactional
    public Dataset merge() {
        setUpdated(new Date());
        if (this.entityManager == null) this.entityManager = entityManager();
        Dataset merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    /**
     * retrieve a dataset by 
     * 
     * @param doi
     *            of an dataset
     * @return dataset with the given doi or null if no such dataset
     *         exists
     */
    public static Dataset findDatasetByDoi(String doi) {
        doi = Utils.normalizeDoi(doi);
        if (doi == null) {
            return null;
        }
        try {
            log4j.debug("search for '" + doi + "'");
            Dataset dataset = findDatasetsByDoiEquals(doi).getSingleResult();
            log4j.debug("found '" + doi + "'");
            return dataset;
        } catch (Exception e) {
            log4j.debug("no dataset found");
            return null;
        }
    }

    public void setDoi(String doi) {
        this.doi = Utils.normalizeDoi(doi);
    }
    
    public interface SecondLevelConstraint {};
}
