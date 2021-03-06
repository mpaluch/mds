package org.datacite.mds.util;

import org.apache.log4j.Logger;
import org.datacite.mds.domain.Allocator;
import org.datacite.mds.domain.Datacentre;
import org.datacite.mds.domain.Dataset;
import org.datacite.mds.service.SecurityException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * security related utils
 */
public class SecurityUtils {

    private static Logger log4j = Logger.getLogger(SecurityUtils.class);
    
    public static boolean isLoggedIn() {
        return getCurrentSymbolOrNull() != null;
    }
    
    public static boolean isLoggedInAsAllocator() {
        return getCurrentAllocatorOrNull() != null;
    }

    public static boolean isLoggedInAsDatacentre() {
        return getCurrentDatacentreOrNull() != null;
    }
  
    /**
     * retrieves Allocator object matching symbol used for logging into
     * application
     * 
     * @return Allocator object
     * @throws SecurityException 
     *             when not logged in as Allocator
     */
    public static Allocator getCurrentAllocator() throws SecurityException {
        Allocator allocator = getCurrentAllocatorOrNull();
        if (allocator == null) {
            throw new SecurityException("allocator not registered");
        }
        return allocator;
    }

    /**
     * retrieves Datacentre object matching symbol used for logging into
     * application with Datacentre's symbol
     * 
     * @return Datacentre object
     * @throws SecurityException
     *             when not logged in as Datacentre
     */
    public static Datacentre getCurrentDatacentre() throws SecurityException {
        Datacentre datacentre = getCurrentDatacentreOrNull();
        if (datacentre == null) {
            throw new SecurityException("datacentre not registered");
        }
        return datacentre;
    }
    
    private static Allocator getCurrentAllocatorOrNull() {
        String symbol = getCurrentSymbolOrNull();
        return Allocator.findAllocatorBySymbol(symbol);
    }

    private static Datacentre getCurrentDatacentreOrNull() {
        String symbol = getCurrentSymbolOrNull();
        return Datacentre.findDatacentreBySymbol(symbol);
    }
    
    private static String getCurrentSymbolOrNull() {
        log4j.debug("get current auth");
        Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
        if (currentAuth == null) {
            log4j.debug("not logged in");
            return null;
        } else {
            return currentAuth.getName();
        }
    }

    /**
     * Checks if a Datacentre still has available DOIs
     * 
     * @param datacentre
     * @throws SecurityException
     *             Datacentre run out of quota
     */
    public static void checkQuota(Datacentre datacentre) throws SecurityException {
        if (datacentre.isQuotaExceeded()) {
            String message = "datacentre quota exceeded: " + datacentre.getSymbol();
            log4j.info(message);
            throw new SecurityException(message);
        }
    }
    
    public static void checkDatasetOwnership(Dataset dataset, Datacentre datacentre) throws SecurityException {
        if (!datacentre.getSymbol().equals(dataset.getDatacentre().getSymbol()))
            throw new SecurityException("cannot access dataset which belongs to another party");
    }

}
