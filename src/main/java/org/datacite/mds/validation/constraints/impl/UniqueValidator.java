package org.datacite.mds.validation.constraints.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.datacite.mds.validation.constraints.Unique;

public class UniqueValidator implements ConstraintValidator<Unique, Object> {

    Logger log = Logger.getLogger(UniqueValidator.class);

    @PersistenceContext
    transient EntityManager entityManager;

    Class<?> entity;
    String field;
    String idField;

    public void initialize(Unique constraintAnnotation) {
        this.entity = constraintAnnotation.entity();
        this.field = constraintAnnotation.field();
        this.idField = constraintAnnotation.idField();
    }

    public boolean isValid(Object object, ConstraintValidatorContext context) {
        // if hibernate persists the object the entityManager is not injected,
        // so we always return true.
        if (entityManager == null)
            return true;

        Serializable id, value;
        try {
            id = (Serializable) PropertyUtils.getProperty(object, idField);
            value = (Serializable) PropertyUtils.getProperty(object, field);
        } catch (Exception e) {
            log.debug("error getting property:" + e.getMessage());
            return false;
        }
        log.debug("entity=" + entity.getName() + ", id: " + idField + "=" + id + ", field: " + field + "=" + value);

        String qstr = "SELECT " + idField + " FROM " + entity.getName() + " WHERE " + field + " = :value";
        TypedQuery<Serializable> q = entityManager.createQuery(qstr, Serializable.class);
        q.setParameter("value", value);
        List<Serializable> results = q.getResultList();

        Boolean isUnique = true;
        if (results.size() == 1) {
            Serializable foundId = results.get(0);
            log.debug("#results=1 (" + idField + "=" + foundId + ")");
            isUnique = foundId.equals(id);
        } else {
            log.debug("#results=" + results.size());
            isUnique = results.size() == 0;
        }
        log.debug("isUnique=" + isUnique);
        return isUnique;
    }

}
