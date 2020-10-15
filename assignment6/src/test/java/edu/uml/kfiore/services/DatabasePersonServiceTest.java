package edu.uml.kfiore.services;

import edu.uml.kfiore.model.Person;
import edu.uml.kfiore.model.PersonTest;
import edu.uml.kfiore.model.Quotes;
import edu.uml.kfiore.util.DatabaseUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the DatabasePersonService
 */
public class DatabasePersonServiceTest {

    private PersonService personService;

    private void initDb() throws Exception {
        DatabaseUtils.initializeDatabase(DatabaseUtils.initializationFile);
    }

    /**
     * Set up database and make sure it is correct before testing
     * @throws Exception
     */

    @Before
    public void setUp() throws Exception {
        initDb();
        personService = ServiceFactory.getPersonServiceInstance();
    }

    /**
     * Database tear down/clean up
     * @throws Exception
     */

    @After
    public void tearDown() throws Exception {
        initDb();
    }

    /**
     * Test to make sure PersonService is online and functioning
     */

    @Test
    public void testGetInstance() {
        assertNotNull("Make sure PersonService is available", personService);
    }

    /**
     * Test to make sure we get a Person object from the PersonService
     * @throws PersonServiceException
     */

    @Test
    public void testGetPerson() throws PersonServiceException {
        List<Person> personList = personService.getPerson();
        assertFalse("Need Person objects returned", personList.isEmpty());
    }

    /**
     * Test to make sure a Person object is tagged to the Quote object
     * @throws PersonServiceException
     */

    @Test
    public void testGetQuotes() throws PersonServiceException {
        Person person = PersonTest.createPerson();
        List<Quotes> quotesList = personService.getQuotes(person);
        assertFalse("Need Person objects returned", quotesList.isEmpty());
    }

    /**
     * Test to find a new Person added into the database and making sure it matches
     * @throws PersonServiceException
     */

    @Test
    public void testAddPerson()throws PersonServiceException {
        Person newPerson = PersonTest.createPerson();
        personService.addOrUpdatePerson(newPerson);
        List<Person> personList = personService.getPerson();
        boolean found = false;
        for (Person person : personList) {
            if (person.getUserName().equals(PersonTest.userName)) {
                found = true;
                break;
            }
        }
        assertTrue("Found the person we added", found);
    }

    /**
     * Test which creates a Person and matches them to Quotes in the database
     * @throws PersonServiceException
     */

    @Test
    public void testAddQuotesToPerson() throws PersonServiceException {
        Person person = PersonTest.createPerson();
        List<Quotes> quotes = personService.getQuotes(person);
        for (Quotes quote : quotes) {
            personService.addQuotesToPerson(quote, person);
        }
        List<Quotes> quoteList = personService.getQuotes(person);
        for (Quotes quote : quotes) {
            boolean removed = quoteList.remove(quote);
            assertTrue("The quote was found in the list", removed);
        }
        assertTrue("Returned quotes match were expected ", quoteList.isEmpty());

    }


}
