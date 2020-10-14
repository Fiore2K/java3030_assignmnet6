package edu.uml.kfiore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Models a table the combines person with stocks they are interested in.
 */
@Entity
@Table(name = "person_stocks")
public class Person_Stocks implements DatabaseAccessObject {
    private int id;
    private Person person;
    private Quotes quotes;

    /**
     * Create a Person_Stocks that needs to be initialized
     */
    public Person_Stocks() {
        // this empty constructor is required by hibernate framework

    }

    /**
     * Create a valid Person_Stocks
     *
     * @param person the person to assign the hobby to
     * @param quotes the quotes to associate the person with
     */
    public Person_Stocks(Person person, Quotes quotes) {
        setQuotes(quotes);
        setPerson(person);
    }

    /**
     * Primary Key - Unique ID for a particular row in the person_stocks table.
     *
     * @return an integer value
     */
    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    /**
     * Set the unique ID for a particular row in the person_hobby table.
     * This method should not be called by client code. The value is managed in internally.
     *
     * @param id a unique value.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return get the Person associated with this stock
     */
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "ID", nullable = false)
    public Person getPerson() {
        return person;
    }

    /**
     * Specify the Person associated with the stock.
     *
     * @param person a person instance
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     *
     * @return get the Quotes associated with this Person
     */
    @ManyToOne
    @JoinColumn(name = "quotes_id", referencedColumnName = "ID", nullable = false)
    public Quotes getQuotes() {
        return quotes;
    }

    /**
     * Specify the Quotes associated with the Person.
     *
     * @param quotes a person instance
     */
    public void setQuotes(Quotes quotes) {
        this.quotes = quotes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person_Stocks that = (Person_Stocks) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + person.hashCode();
        result = 31 * result + quotes.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PersonHobby{" +
                "id=" + id +
                ", person=" + person +
                ", quotes=" + quotes +
                '}';
    }
}


