// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.datacite.mds.domain;

import java.lang.Integer;
import java.lang.Long;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import org.datacite.mds.domain.Dataset;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Dataset_Roo_Entity {
    
    declare @type: Dataset: @Entity;
    
    @PersistenceContext
    transient EntityManager Dataset.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Dataset.id;
    
    @Version
    @Column(name = "version")
    private Integer Dataset.version;
    
    public Long Dataset.getId() {
        return this.id;
    }
    
    public void Dataset.setId(Long id) {
        this.id = id;
    }
    
    public Integer Dataset.getVersion() {
        return this.version;
    }
    
    public void Dataset.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void Dataset.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Dataset attached = this.entityManager.find(this.getClass(), this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Dataset.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    public static final EntityManager Dataset.entityManager() {
        EntityManager em = new Dataset().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Dataset.countDatasets() {
        return entityManager().createQuery("select count(o) from Dataset o", Long.class).getSingleResult();
    }
    
    public static List<Dataset> Dataset.findAllDatasets() {
        return entityManager().createQuery("select o from Dataset o", Dataset.class).getResultList();
    }
    
    public static Dataset Dataset.findDataset(Long id) {
        if (id == null) return null;
        return entityManager().find(Dataset.class, id);
    }
    
    public static List<Dataset> Dataset.findDatasetEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from Dataset o", Dataset.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
