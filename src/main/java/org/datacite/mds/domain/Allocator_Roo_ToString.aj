// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.datacite.mds.domain;

import java.lang.String;

privileged aspect Allocator_Roo_ToString {
    
    public String Allocator.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("Symbol: ").append(getSymbol()).append(", ");
        sb.append("Password: ").append(getPassword()).append(", ");
        sb.append("Name: ").append(getName()).append(", ");
        sb.append("ContactName: ").append(getContactName()).append(", ");
        sb.append("ContactEmail: ").append(getContactEmail()).append(", ");
        sb.append("DoiQuotaAllowed: ").append(getDoiQuotaAllowed()).append(", ");
        sb.append("DoiQuotaUsed: ").append(getDoiQuotaUsed()).append(", ");
        sb.append("Prefixes: ").append(getPrefixes() == null ? "null" : getPrefixes().size()).append(", ");
        sb.append("IsActive: ").append(getIsActive()).append(", ");
        sb.append("RoleName: ").append(getRoleName());
        return sb.toString();
    }
    
}
