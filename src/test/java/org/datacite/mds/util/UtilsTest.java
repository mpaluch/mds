package org.datacite.mds.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.springframework.core.convert.converter.Converter;

public class UtilsTest {
    @Test
    public void testNormalizeDoi() {
        assertTrue(Utils.normalizeDoi("10.5072/aBcÄöß_12zZ").equals("10.5072/ABCÄöß_12ZZ"));
        assertTrue(Utils.normalizeDoi("").equals(""));
        assertNull(Utils.normalizeDoi(null));
    }

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
        assertTrue(Utils.getDoiSuffix("10.5072/suffix/foo/bar").equals("suffix/foo/bar"));
        assertTrue(Utils.getDoiSuffix("emptysuffix/").equals(""));
        assertNull(Utils.getDoiSuffix("nosuffix"));
        assertNull(Utils.getDoiSuffix(""));
        assertNull(Utils.getDoiSuffix(null));
    }

    @Test
    public void testGetAllocatorFromSymbol() {
        assertTrue(Utils.getAllocatorFromDatacentreSymbol("BL.DC").equals("BL"));
        assertNull(Utils.getAllocatorFromDatacentreSymbol("BL"));
        assertNull(Utils.getAllocatorFromDatacentreSymbol(""));
        assertNull(Utils.getAllocatorFromDatacentreSymbol(null));
    }

    @Test
    public void testCsvToList() {
        String csv = "ab,,cde,f,";
        List<String> list = Utils.csvToList(csv);
        assertEquals(5, list.size());
        assertTrue(list.get(0).equals("ab"));
        assertTrue(list.get(1).equals(""));
        assertTrue(list.get(2).equals("cde"));
        assertTrue(list.get(3).equals("f"));
        assertTrue(list.get(4).equals(""));

        assertEquals(1, Utils.csvToList("").size());
        assertEquals(0, Utils.csvToList("").get(0).length());
        assertEquals(0, Utils.csvToList(null).size());
    }

    @Test
    public void testNormalizeCsv() {
        String csv = " ,  aa,,b c,dd \n ee,ff \ngg,, ,\n";
        Collection<String> newline = Arrays.asList("\n");
        Collection<String> space = Arrays.asList(" ");
        Collection<String> newlineAndSpace = Arrays.asList(" ", "\n");

        assertEquals(",aa,,b c,dd \n ee,ff \ngg,,,", Utils.normalizeCsv(csv, null, false));
        assertEquals(",,,,aa,,b,c,dd,\n,ee,ff,\ngg,,,,", Utils.normalizeCsv(csv, space, false));
        assertEquals(",aa,,b c,dd,ee,ff,gg,,,", Utils.normalizeCsv(csv, newline, false));
        assertEquals(",,,,aa,,b,c,dd,,,ee,ff,,gg,,,,", Utils.normalizeCsv(csv, newlineAndSpace, false));

        assertEquals("aa,b c,dd \n ee,ff \ngg", Utils.normalizeCsv(csv, null, true));
        assertEquals("aa,b,c,dd,\n,ee,ff,\ngg", Utils.normalizeCsv(csv, space, true));
        assertEquals("aa,b c,dd,ee,ff,gg", Utils.normalizeCsv(csv, newline, true));
        assertEquals("aa,b,c,dd,ee,ff,gg", Utils.normalizeCsv(csv, newlineAndSpace, true));

    }

    @Test
    public void getHostname() {
        assertNull(Utils.getHostname(null));
        assertNull(Utils.getHostname(""));
        assertNull(Utils.getHostname("malformedURL"));
        assertEquals("sub.domain.tld", Utils.getHostname("ftp://user@sub.domain.tld:8080/path"));
    }

    @Test
    public void formatXml() throws Exception {
        assertNull(Utils.formatXML(null));
        assertEquals("", Utils.formatXML(""));
        assertNotNull(Utils.formatXML("<root/>"));
    }

    @Test(expected = Exception.class)
    public void formatXml_invalid() throws Exception {
        Utils.formatXML("<foo></bar>");
    }
    
    @Test
    public void testConvertCollectionToCsv() {
        String str = Utils.convertCollectionToCsv(testCollection, new SimpleConverter());
        String expect = "#foo#" + Utils.CSV_SEPARATOR + "#42#" + Utils.CSV_SEPARATOR + "#bar#"; 
        assertEquals(expect, str);
    }

    @Test
    public void testConvertCollectionToCsvNullCollection() {
        String str = Utils.convertCollectionToCsv(null, new SimpleConverter());
        assertNull("", str);
    }
    
    private Collection<Object> testCollection = new ArrayList<Object>() {
        {
            add("foo");
            add(42);
            add("bar");
        }
    };

    private class SimpleConverter implements Converter<Object, String> {
        @Override
        public String convert(Object source) {
            return "#" + source.toString() + "#";
        }
    }
    
    @Test
    public void testWildCardMatch() {
        assertFalse(Utils.wildCardMatch("abcdefg", "", "*"));
        
        assertTrue(Utils.wildCardMatch("abcdefg", "abcdefg", "*"));
        assertTrue(Utils.wildCardMatch("abcde*fg", "*", "*"));
        
        assertTrue(Utils.wildCardMatch("abcde*fg", "ab*", "*"));
        assertTrue(Utils.wildCardMatch("abcde*fg", "*cd*", "*"));
        assertTrue(Utils.wildCardMatch("abcde*fg", "*fg", "*"));
        
        assertTrue(Utils.wildCardMatch("abcde*fg", "*b*d*", "*"));
        assertTrue(Utils.wildCardMatch("abcde*fg", "a*b*c*d*e*f*g", "*"));
        assertTrue(Utils.wildCardMatch("abcde*fg", "a***g", "*"));
        
        assertFalse(Utils.wildCardMatch("abc*defg", "b*", "*"));
        assertFalse(Utils.wildCardMatch("abc*defg", "*z*", "*"));

        assertFalse(Utils.wildCardMatch("abc*defg", "abc", "*"));
        assertFalse(Utils.wildCardMatch("abc*defg", "de", "*"));
        assertFalse(Utils.wildCardMatch("abc*defg", "fg", "*"));
    }

}
