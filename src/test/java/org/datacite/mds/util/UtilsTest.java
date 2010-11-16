package org.datacite.mds.util;

import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

public class UtilsTest {
    @Test
    public void testGetDoiPrefix() {
        assertTrue(Utils.getDoiPrefix("10.5072/suffix").equals("10.5072"));
        assertTrue(Utils.getDoiPrefix("/emptyprefix").equals(""));
        assertNull(Utils.getDoiPrefix("noprefix"));
        assertNull(Utils.getDoiPrefix(""));
        assertNull(Utils.getDoiPrefix(null));
    }

    @Test
    public void testGetDoiSuffix() {
        assertTrue(Utils.getDoiSuffix("10.5072/suffix").equals("suffix"));
        assertTrue(Utils.getDoiSuffix("emptysuffix/").equals(""));
        assertNull(Utils.getDoiSuffix("nosuffix"));
        assertNull(Utils.getDoiSuffix(""));
        assertNull(Utils.getDoiSuffix(null));
    }
    
    @Test
    public void testCsvToList() {
        String csv="ab,,cde,f,";
        List<String> list = Utils.csvToList(csv);
        assertEquals(5,list.size());
        assertTrue(list.get(0).equals("ab"));
        assertTrue(list.get(1).equals(""));
        assertTrue(list.get(2).equals("cde"));
        assertTrue(list.get(3).equals("f"));
        assertTrue(list.get(4).equals(""));
        
        assertEquals(1,Utils.csvToList("").size());
        assertEquals(0,Utils.csvToList("").get(0).length());
        assertEquals(0,Utils.csvToList(null).size());
    }
    
    @Test
    public void getHostname() {
        assertTrue(Utils.getHostname("ftp://user@sub.domain.tld:8080/path").equals("sub.domain.tld"));
    }
}
