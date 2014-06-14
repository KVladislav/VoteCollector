package org.hillel.it.votecollector.IOopperations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 15.12.13
 * Time: 14:26
 */
public class ReusableConnectionPool implements ConnectionPool {
    final private int maxPoolSize;
    private List<ReusableConnection> connections;
    final private String url;
    private AtomicBoolean closedState = new AtomicBoolean(false);

    public ReusableConnectionPool(int maxPoolSize, String url) {
        if (maxPoolSize < 0)
            throw new RuntimeException("PoolSize can't be less then 0");
        if (url == null)
            throw new RuntimeException("Url should not be null");
        this.maxPoolSize = maxPoolSize;
        this.url = url;
    }

    /**
     * Returns free connection from pool, if no free connection returns null
     */
    @Override
    public Connection getConnection() throws SQLException {
        if (closedState.get()) throw new RuntimeException("Poll is closed");
        if (connections == null) {
            connections = new ArrayList<>();
        }

        for (ReusableConnection connection : connections) {
            synchronized (connection) {
                if (connection != null && !connection.isBusy()) {
                    System.out.println("taking free connection " + connections.size());
                    connection.setBusy(true);


                    return connection;
                }
            }
        }
        synchronized (connections) {
            if (connections.size() < maxPoolSize) {

                System.out.println("adding new connection " + connections.size());
                Connection connection = DriverManager.getConnection(url);
                ReusableConnection reusableConnection = new ReusableConnection(connection);
                connections.add(reusableConnection);

                return reusableConnection;
            }
        }

        while (true) {
            System.out.println("Waiting for new connection " + connections.size());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return getConnection();
        }
    }

//    @Override
//    public void releaseConnection(Connection connection) {
//        connection
//
//    }

    @Override
    public void closePool() {
        closedState.set(true);
        for (ReusableConnection connection : connections) {
            try {
                connection.shutdown();
            } catch (SQLException e) {
                System.out.println("cannot close connection");
                e.printStackTrace();
            }
        }


    }
}
