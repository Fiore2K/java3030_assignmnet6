package edu.uml.kfiore.model;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for StockQuery Class
 */
public class StockQueryTest {

    @Test
    public void testBasicConstruction() throws Exception{
        String symbol = "APPL";
        StockQuery stockQuery = new StockQuery(symbol,"2011/12/12 12:12:01","2011/12/12 12:12:01");
        assertEquals("Verify construction", symbol, stockQuery.getSymbol());
    }

}
