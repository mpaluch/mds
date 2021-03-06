package org.datacite.mds.validation.constraints.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;
import org.datacite.mds.util.Utils;
import org.datacite.mds.validation.constraints.Doi;

public class DoiValidator implements ConstraintValidator<Doi, String> {

    public void initialize(Doi constraintAnnotation) {
        // nothing to initialize
    }

    public boolean isValid(String doi, ConstraintValidatorContext context) {
        if (doi == null) {
            return true;
        }
        String prefix = Utils.getDoiPrefix(doi);
        String suffix = Utils.getDoiSuffix(doi);
        return !StringUtils.isEmpty(prefix) && !StringUtils.isEmpty(suffix) && prefix.matches("10\\.(\\d)+")
                && suffix.matches("[^\u0000-\u001F\u0080-\u009F]+") && !suffix.matches("./.*");
    }
}
