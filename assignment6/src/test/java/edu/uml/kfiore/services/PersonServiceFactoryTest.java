package edu.uml.kfiore.services;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * JUnit test for <CODE>activitiesService</CODE>
 */
public class PersonServiceFactoryTest {

    @Test
    public void testFactory() {
        PersonService instance = ServiceFactory.getPersonServiceInstance();
        assertNotNull("Make sure PersonServiceFactory works", instance);
    }
}
