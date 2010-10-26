package org.datacite.mds.validation.constraints;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.datacite.mds.domain.Allocator;
import org.datacite.mds.domain.Datacentre;
import org.datacite.mds.util.Utils;
import org.junit.Before;
import org.junit.Test;

public class MatchSymbolPrefixTest {

    Datacentre datacentre;

    @Before
    public void init() {
        Allocator allocator = new Allocator();
        allocator.setSymbol("AL");

        datacentre = new Datacentre();
        datacentre.setAllocator(allocator);
    }

    @Test
    public void test() {
        assertTrue(isValid("AL.DC"));
        assertFalse(isValid("OTHER.DC"));
    }

    boolean isValid(String symbol) {
        datacentre.setSymbol(symbol);
        System.out.println(datacentre);
        return Utils.isConstraintValid(datacentre, MatchSymbolPrefix.class);
    }
}