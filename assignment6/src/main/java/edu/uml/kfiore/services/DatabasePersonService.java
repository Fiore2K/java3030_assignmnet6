package edu.uml.kfiore.services;

import edu.uml.kfiore.model.Person;
import edu.uml.kfiore.model.Person_Stocks;
import edu.uml.kfiore.model.Quotes;
import edu.uml.kfiore.util.DatabaseUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

import static edu.uml.kfiore.util.DatabaseUtils.getSessionFactory;

public class DatabasePersonService implements PersonService {

    /**
     * Return a list of people
     *
     * @return
     * @throws PersonServiceException
     */

    @Override
    public List <Person> getPerson() throws PersonServiceException {

        Session session = getSessionFactory().openSession();
        List <Person> returnValue;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(Person.class);

            returnValue = criteria.list();

        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new PersonServiceException("Can not find data for Person. " + e.getMessage(), e);
        } finally {
            if (transaction != null && transaction.isActive()) {
                transaction.commit();
            }
        }

        return returnValue;

    }

    public static List<Person> getPeep(boolean handleTransaction) {
        Session session = null;
        List <Person> returnValue;
        try

        {
            Transaction transaction = null;
            session = getSessionFactory().openSession();
            if (handleTransaction) {
                transaction = session.beginTransaction();
            }
            Criteria criteria = session.createCriteria(Person.class);

            returnValue = (List <Person>) criteria.list();
            if (handleTransaction) {
                transaction.commit();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return returnValue;
    }

    /**
     * Adding a new person or updating an existing persons data/information
     * @param person a person object to either update or create
     */

    @Override
    public void addOrUpdatePerson(Person person) {

        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(person);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();  // close transaction
            }
        } finally {
            if (transaction != null && transaction.isActive()) {
                transaction.commit();
            }
        }

    }

    /**
     * Get a list of all persons quotes
     *
     * @param person
     * @return a list of quotes
     */

    @Override
    public List<Quotes> getQuotes(Person person) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        List<Quotes> quotes = new ArrayList<>();
        try {
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(Person_Stocks.class);
            criteria.add(Restrictions.eq("person", person));

            List<Person_Stocks> list = criteria.list();
            for (Person_Stocks person_stocks : list) {
                quotes.add(person_stocks.getQuotes());
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();  // close transaction
            }
        } finally {
            if (transaction != null && transaction.isActive()) {
                transaction.commit();
            }
        }
        return quotes;
    }

    /**
     * Adding a quote to a person
     *
     * @param quotes  The quotes to assign
     * @param person The person to assign the quote to.
     */

    @Override
    public void addQuotesToPerson(Quotes quotes, Person person) {

        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Person_Stocks person_stocks = new Person_Stocks();
            person_stocks.setQuotes(quotes);
            person_stocks.setPerson(person);
            session.saveOrUpdate(person_stocks);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            if (transaction != null && transaction.isActive()) {
                transaction.commit();
            }
        }
    }
}
