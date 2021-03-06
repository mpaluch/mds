// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.datacite.mds.domain;

import java.lang.String;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.datacite.mds.domain.Dataset;

privileged aspect Dataset_Roo_Finder {
    
    public static TypedQuery<Dataset> Dataset.findDatasetsByDoiEquals(String doi) {
        if (doi == null || doi.length() == 0) throw new IllegalArgumentException("The doi argument is required");
        EntityManager em = Dataset.entityManager();
        TypedQuery<Dataset> q = em.createQuery("SELECT Dataset FROM Dataset AS dataset WHERE dataset.doi = :doi", Dataset.class);
        q.setParameter("doi", doi);
        return q;
    }
    
}
