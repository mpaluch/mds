// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.datacite.mds.domain;

import java.lang.String;

privileged aspect Datacentre_Roo_ToString {
    
    public String Datacentre.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("Password: ").append(getPassword()).append(", ");
        sb.append("RoleName: ").append(getRoleName()).append(", ");
        sb.append("Allocator: ").append(getAllocator()).append(", ");
        sb.append("BaseAuthString: ").append(getBaseAuthString()).append(", ");
        sb.append("Symbol: ").append(getSymbol()).append(", ");
        sb.append("Name: ").append(getName()).append(", ");
        sb.append("ContactName: ").append(getContactName()).append(", ");
        sb.append("ContactEmail: ").append(getContactEmail()).append(", ");
        sb.append("DoiQuotaAllowed: ").append(getDoiQuotaAllowed()).append(", ");
        sb.append("DoiQuotaUsed: ").append(getDoiQuotaUsed()).append(", ");
        sb.append("IsActive: ").append(getIsActive()).append(", ");
        sb.append("Domains: ").append(getDomains()).append(", ");
        sb.append("Comments: ").append(getComments()).append(", ");
        sb.append("Prefixes: ").append(getPrefixes() == null ? "null" : getPrefixes().size()).append(", ");
        sb.append("Updated: ").append(getUpdated()).append(", ");
        sb.append("Created: ").append(getCreated());
        return sb.toString();
    }
    
}
