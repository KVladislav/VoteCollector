package org.hillel.it.votecollector.IOopperations;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 15.12.13
 * Time: 14:02
 */
public interface ConnectionPool {
    Connection getConnection() throws SQLException;

    //    void releaseConnection(Connection connection);
    void closePool();


}
