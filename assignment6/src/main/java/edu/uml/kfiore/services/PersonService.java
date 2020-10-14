package services;


import model.Person;
import model.Quotes;

import java.util.List;

/**
 * This API describes how to get person and quotes data from an external resource as well as add or update a person or quote
 */
public interface PersonService {

    /**
     * Get a list of all people
     *
     * @return a list of Person instances
     * @throws PersonServiceException
     */
    List<Person> getPerson() throws PersonServiceException;

    /**
     * Add a new person or update an existing Persons data
     *
     * @param person a person object to either update or create
     * @throws PersonServiceException
     */
    void addOrUpdatePerson(Person person) throws PersonServiceException;

    /**
     * Get a list of all a persons quotes.
     *
     * @return a list of quotes instances
     * @throws PersonServiceException
     */
    List<Quotes> getQuotes(Person person) throws PersonServiceException;

    /**
     * Assign a quote to a person.
     *
     * @param quotes  The quotes to assign
     * @param person The person to assign the hobby too.
     * @throws PersonServiceException
     */
    public void addQuotesToPerson(Quotes quotes, Person person) throws PersonServiceException;

}
