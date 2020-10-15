package edu.uml.kfiore.model;

import edu.uml.kfiore.model.database.Person;
import edu.uml.kfiore.model.database.PersonStocks;
import edu.uml.kfiore.model.database.Quote;
import org.junit.Test;

import java.math.BigDecimal;

import static edu.uml.kfiore.model.QuotesTest.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for Person_Stocks class
 */
public class Person_StocksTest {

    /**
     * Testing helper method for generating Person_Stocks test data
     *
     * @return a Person_Stocks object that uses Person and Quotes
     * return from their respective create method.
     */
    public static PersonStocks createPerson_Stocks() {
        Person person = PersonTest.createPerson();
        Quote quotes = createQuotes();
        return new PersonStocks(person, quotes);
    }

    @Test
    public void testPerson_StocksGetterAndSetters() {
        Person person = PersonTest.createPerson();
        PersonStocks personStocks = new PersonStocks();
        int id = 10;
        personStocks.setId(id);
        personStocks.setPerson(person);
        assertEquals("person matches", person, personStocks.getPerson());
        assertEquals("id matches", id, personStocks.getId());
    }

    @Test
    public void testEqualsNegativeDifferentPerson() {
        PersonStocks personStocks = createPerson_Stocks();
        personStocks.setId(10);
        Quote quotes = createQuotes();
        Person person = new Person();
        person.setUserName(PersonTest.userName);
        PersonStocks personStocks1 = new PersonStocks(person, quotes);
        assertFalse("Different person", personStocks.equals(personStocks1));
    }

    @Test
    public void testEquals() {
        PersonStocks personStocks = createPerson_Stocks();
        assertTrue("Same objects are equal", personStocks.equals(createPerson_Stocks()));
    }

    @Test
    public void testToString() {
        PersonStocks personStocks = createPerson_Stocks();
        assertTrue("toString has firstName", personStocks.toString().contains(PersonTest.userName));
        assertTrue("toString has stockSymbol", personStocks.toString().contains(stockSymbol));
        assertTrue("toString has time", personStocks.toString().contains((String.valueOf(time))));
        assertEquals("toString has price", true, personStocks.toString().contains(BigDecimal.valueOf(QuotesTest.price)));
    }

}


