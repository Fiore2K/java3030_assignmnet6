package edu.uml.kfiore.model;

import edu.uml.kfiore.util.DatabaseUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for the Person class
 */
public class PersonTest {


    public static final String userName = "sam";

    /**
     * Testing helper method for generating Person test data
     *
     * @return a Person object that uses static constants for data.
     */
    public static Person createPerson() {
        Person person = new Person();
        person.setUserName(userName);
        DatabaseUtils.saveOrUpdate(person);
        return person;
    }

    @Test
    public void testPersonGettersAndSetters() {
        Person person = createPerson();
        int id = 3;
        person.setId(id);
        assertEquals("first name matches", userName, person.getUserName());
        assertEquals("id matches", id, person.getId());

    }

}
