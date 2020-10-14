package model;

import org.junit.Test;

import java.sql.Timestamp;

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
    public static Person_Stocks createPerson_Stocks() {
        Person person = PersonTest.createPerson();
        Quotes quotes = QuotesTest.createQuotes();
        return new Person_Stocks(person, quotes);
    }

    @Test
    public void testPerson_StocksGetterAndSetters() {
        Quotes quotes = QuotesTest.createQuotes();
        Person person = PersonTest.createPerson();
        Person_Stocks personStocks = new Person_Stocks();
        int id = 10;
        personStocks.setId(id);
        personStocks.setPerson(person);
        personStocks.setQuotes(quotes);
        assertEquals("person matches", person, personStocks.getPerson());
        assertEquals("quote matches", quotes, personStocks.getQuotes());
        assertEquals("id matches", id, personStocks.getId());
    }

    @Test
    public void testEqualsNegativeDifferentPerson() {
        Person_Stocks personStocks = createPerson_Stocks();
        personStocks.setId(10);
        Quotes quotes = QuotesTest.createQuotes();
        Person person = new Person();
        Timestamp birthDate = new Timestamp(PersonTest.birthDayCalendar.getTimeInMillis() + 10000);
        person.setBirthDate(birthDate);
        person.setFirstName(PersonTest.firstName);
        person.setLastName(PersonTest.lastName);
        Person_Stocks personStocks1 = new Person_Stocks(person, quotes);
        assertFalse("Different person", personStocks.equals(personStocks1));
    }

    @Test
    public void testEquals() {
        Person_Stocks personStocks = createPerson_Stocks();
        assertTrue("Same objects are equal", personStocks.equals(createPerson_Stocks()));
    }

    @Test
    public void testToString() {
        Person_Stocks personStocks = createPerson_Stocks();
        assertTrue("toString has lastName", personStocks.toString().contains(PersonTest.lastName));
        assertTrue("toString has firstName", personStocks.toString().contains(PersonTest.firstName));
        assertTrue("toString has stockSymbol", personStocks.toString().contains(QuotesTest.stockSymbol));
        assertTrue("toString has time", personStocks.toString().contains((String.valueOf(QuotesTest.time))));
        assertTrue("toString has price", personStocks.toString().contains(Integer.toString(QuotesTest.price)));
    }

}


