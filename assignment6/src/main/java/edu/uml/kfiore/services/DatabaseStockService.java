package edu.uml.kfiore.services;


import edu.uml.kfiore.model.StockData;
import edu.uml.kfiore.model.StockQuote;
import edu.uml.kfiore.util.DatabaseConnectionException;
import edu.uml.kfiore.util.DatabaseUtils;
import edu.uml.kfiore.util.Interval;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * An implementation of the StockService interface that gets
 * stock data from a database.
 */
class DatabaseStockService implements StockService {

    /**
     * Return the current price for a share of stock  for the given symbol
     *
     * @param symbol the stock symbol of the company you want a quote for.
     *               e.g. APPL for APPLE
     * @return a  <CODE>BigDecimal</CODE> instance
     * @throws StockServiceException if using the service generates an exception.
     *                               If this happens, trying the service may work, depending on the actual cause of the
     *                               error.
     */
    @Override
    public StockQuote getQuote(String symbol) throws StockServiceException {
        // todo - this is a pretty lame implementation why?
        List<StockQuote> stockQuotes = null;
        try {
            Connection connection = DatabaseUtils.getConnection();
            Statement statement = connection.createStatement();
            String queryString = "select * from quotes where symbol = '" + symbol + "'";

            ResultSet resultSet = statement.executeQuery(queryString);
            stockQuotes = new ArrayList<>(resultSet.getFetchSize());
            while (resultSet.next()) {
                String symbolValue = resultSet.getString("symbol");
                Date time = resultSet.getDate("time");
                BigDecimal price = resultSet.getBigDecimal("price");
                stockQuotes.add(new StockQuote(price, time, symbolValue));
            }

        } catch (DatabaseConnectionException | SQLException exception) {
            throw new StockServiceException(exception.getMessage(), exception);
        }
        if (stockQuotes.isEmpty()) {
            throw new StockServiceException("There is no stock data for:" + symbol);
        }
        return stockQuotes.get(0);
    }

    /**
     * Get a historical list of stock quotes for the provide symbol
     *
     * @param symbol   the stock symbol to search for
     * @param from     the date of the first stock quote
     * @param until    the date of the last stock quote
     * @param interval the number of stockquotes to get per a 24 hour period.
     * @return a list of StockQuote instances
     * @throws StockServiceException if using the service generates an exception.
     *                               If this happens, trying the service may work, depending on the actual cause of the
     *                               error.
     */
    @Override
    public List<StockQuote> getQuote(String symbol, Calendar from, Calendar until, Interval interval) throws StockServiceException {
        List<StockQuote> stockQuotes = null;
        try {
            Connection connection = DatabaseUtils.getConnection();
            Statement statement = connection.createStatement();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(StockData.dateFormat);

            String fromString = simpleDateFormat.format(from.getTime());
            String untilString = simpleDateFormat.format(until.getTime());

            String queryString = "select * from quotes where symbol = '" + symbol + "'"
                    + "and time BETWEEN '" + fromString + "' and '" + untilString + "'";

            ResultSet resultSet = statement.executeQuery(queryString);
            stockQuotes = new ArrayList<>();
            StockQuote previousStockQuote = null;

            /**
             *
             * Here is the general idea behind filtering on interval.
             * It is not perfect, it would be better to filter using SQL and let the DBMS to it,
             * but this way will work OK for small or medium data sets, and since this class
             * (90.303 - not this actual  Java class) is focused on Java and not SQL
             * I thought this approach was appropriate.
             *
             * SIDE BAR:
             *
             * It also highlights a consistent tension which is the question of what should the
             * DBMS be responsible for and what should the Java code be responsible. There is
             * no one answer here as there are lots of factors to consider including:
             *
             * SQL compatibility (complex logic in the DBMS might not be portable to another DBMS)
             *
             * Where do you want your application's business logic to exist?
             * In the DBMS only, in the Java code only? Spread out between the two?
             * Keeping it in one place makes a lot more sense than spreading it out? But where is
             * best - that depends on your application design, and capacity requirements, your teams'
             * skill set (Do you have a lot of db experts on staff?) and whether or not DBMS vendor
             * lock in is a concern or not.
             *
             * Performance and scalability
             * DBMS intensive application will benefit from having the DBMS do the heavy data
             * sorting.
             *
             * Ease of maintenance  and test
             * This questions really relates to your teams' skill set.
             *
             */
            Calendar calendar = Calendar.getInstance();
            while (resultSet.next()) {
                String symbolValue = resultSet.getString("symbol");
                Timestamp timeStamp = resultSet.getTimestamp("time");
                calendar.setTimeInMillis(timeStamp.getTime());
                BigDecimal price = resultSet.getBigDecimal("price");
                java.util.Date time = calendar.getTime();
                StockQuote currentStockQuote = new StockQuote(price, time, symbolValue);

                if (previousStockQuote == null) { // first time through always add stockquote

                    stockQuotes.add(currentStockQuote);

                } else if (isInterval(currentStockQuote.getDate(),
                        interval,
                        previousStockQuote.getDate())) {

                    stockQuotes.add(currentStockQuote);
                }

                previousStockQuote = currentStockQuote;
            }

        } catch (DatabaseConnectionException | SQLException exception) {
            throw new StockServiceException(exception.getMessage(), exception);
        }
        if (stockQuotes.isEmpty()) {
            throw new StockServiceException("There is no stock data for:" + symbol);
        }

        return stockQuotes;
    }


    /**
     * Returns true of the currentStockQuote has a date that is later by the time
     * specified in the interval value from the previousStockQuote time.
     *
     * @param endDate   the end time
     * @param interval  the period of time that must exist between previousStockQuote and currentStockQuote
     *                  in order for this method to return true.
     * @param startDate the starting date
     * @return
     */
    private boolean isInterval(java.util.Date endDate, Interval interval, java.util.Date startDate) {
        Calendar startDatePlusInterval = Calendar.getInstance();
        startDatePlusInterval.setTime(startDate);
        startDatePlusInterval.add(Calendar.MINUTE, interval.getMinutes());
        return endDate.after(startDatePlusInterval.getTime());
    }
}

