// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.datacite.mds.domain;

import java.lang.String;

privileged aspect Prefix_Roo_ToString {
    
    public String Prefix.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("Created: ").append(getCreated()).append(", ");
        sb.append("Prefix: ").append(getPrefix());
        return sb.toString();
    }
    
}
