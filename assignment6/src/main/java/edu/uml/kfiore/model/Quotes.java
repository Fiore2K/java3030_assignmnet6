package model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Models the Person table
 */
@Entity
@Table(name="person")
public class Quotes implements DatabaseAccessObject {

    private int id;
    private String stockSymbol;
    private Timestamp time;
    private int price;

    /**
     * Primary Key - Unique ID for a particular row in the person table.
     *
     * @return an integer value
     */
    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    /**
     * Set the unique ID for a particular row in the person table.
     * This method should not be called by client code. The value is managed in internally.
     *
     * @param id a unique value.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return the person's first name
     */
    @Basic
    @Column(name = "symbol", nullable = false, insertable = true, updatable = true, length = 256)
    public String getStockSymbol() {
        return stockSymbol;
    }

    /**
     * Specify the person's first name
     * @param stockSymbol a String value
     */
    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    /**
     *
     * @return the person's last name
     */
    @Basic
    @Column(name = "price", nullable = false, insertable = true, updatable = true, length = 256)
    public float getPrice() {
        return price;
    }

    /**
     * Specify the person's last name
     * @param price a float value
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     *
     * @return the stock date.
     */
    @Basic
    @Column(name = "time", nullable = false, insertable = true, updatable = true)
    public Timestamp getTime() {
        return time;
    }

    /**
     * Specify the person's date of birth.
     * @param time the time the stock was created.
     */
    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quotes quotes = (Quotes) o;

        if (id != quotes.id) return false;
        if (!Objects.equals(time, quotes.time))
            return false;
        if (!Objects.equals(stockSymbol, quotes.stockSymbol))
            return false;
        if (!Objects.equals(price, quotes.price))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (stockSymbol != null ? stockSymbol.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + price;
        return result;
    }


    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", symbol='" + stockSymbol + '\'' +
                ", price='" + price + '\'' +
                ", time=" + time +
                '}';
    }
}

