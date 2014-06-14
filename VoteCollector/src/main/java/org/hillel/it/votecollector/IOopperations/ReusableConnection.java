package org.hillel.it.votecollector.IOopperations;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 15.12.13
 * Time: 14:52
 */
public class ReusableConnection implements Connection {
    private Connection connection;
    private AtomicBoolean busy = new AtomicBoolean();

    public ReusableConnection(Connection connection) {
        this.connection = connection;
        busy.set(true);
    }

    public void shutdown() throws SQLException {
        busy.set(false);
        connection.close();
    }

    public void setBusy(boolean busy) {
        this.busy.set(busy);
    }

    public boolean isBusy() {
        return busy.get();
    }

    @Override
    public Statement createStatement() throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        return connection.createStatement();
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        return connection.prepareStatement(sql);
    }

    @Override
    public CallableStatement prepareCall(String sql) throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        return connection.prepareCall(sql);
    }

    @Override
    public String nativeSQL(String sql) throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        return connection.nativeSQL(sql);
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        connection.setAutoCommit(autoCommit);

    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        return connection.getAutoCommit();
    }

    @Override
    public void commit() throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        connection.commit();

    }

    @Override
    public void rollback() throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        connection.rollback();
    }

    @Override
    public void close() throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        busy.set(false);
        System.out.println("releasing connection");
    }

    @Override
    public boolean isClosed() throws SQLException {
        return busy.get();
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        return connection.getMetaData();
    }

    @Override
    public void setReadOnly(boolean readOnly) throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        connection.setReadOnly(readOnly);
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        return connection.isReadOnly();
    }

    @Override
    public void setCatalog(String catalog) throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        connection.setCatalog(catalog);
    }

    @Override
    public String getCatalog() throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        return connection.getCatalog();
    }

    @Override
    public void setTransactionIsolation(int level) throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        connection.setTransactionIsolation(level);

    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        return connection.getTransactionIsolation();
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        return connection.getWarnings();
    }

    @Override
    public void clearWarnings() throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        connection.clearWarnings();
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        return connection.createStatement(resultSetType, resultSetConcurrency);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        return connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        return connection.prepareCall(sql, resultSetType, resultSetConcurrency);
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        return connection.getTypeMap();
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        connection.setTypeMap(map);

    }

    @Override
    public void setHoldability(int holdability) throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        connection.setHoldability(holdability);

    }

    @Override
    public int getHoldability() throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        return connection.getHoldability();
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        return connection.setSavepoint();
    }

    @Override
    public Savepoint setSavepoint(String name) throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        return connection.setSavepoint(name);
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        connection.rollback(savepoint);
    }

    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        connection.releaseSavepoint(savepoint);
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        return connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        return connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        return connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        return connection.prepareStatement(sql, autoGeneratedKeys);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        return connection.prepareStatement(sql, columnIndexes);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        return connection.prepareStatement(sql, columnNames);
    }

    @Override
    public Clob createClob() throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        return connection.createClob();
    }

    @Override
    public Blob createBlob() throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        return connection.createBlob();
    }

    @Override
    public NClob createNClob() throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        return connection.createNClob();
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        return connection.createSQLXML();
    }

    @Override
    public boolean isValid(int timeout) throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        return connection.isValid(timeout);
    }

    @Override
    public void setClientInfo(String name, String value) throws SQLClientInfoException {
        if (!isBusy()) {
            throw new RuntimeException("Connection is closed");
        }
        connection.setClientInfo(name, value);

    }

    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        if (!isBusy()) {
            throw new RuntimeException("Connection is closed");
        }
        connection.setClientInfo(properties);

    }

    @Override
    public String getClientInfo(String name) throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        return connection.getClientInfo(name);
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        return connection.getClientInfo();
    }

    @Override
    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        return connection.createArrayOf(typeName, elements);
    }

    @Override
    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        return connection.createStruct(typeName, attributes);
    }

    @Override
    public void setSchema(String schema) throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        connection.setSchema(schema);

    }

    @Override
    public String getSchema() throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        return connection.getSchema();
    }

    @Override
    public void abort(Executor executor) throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        connection.abort(executor);

    }

    @Override
    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        connection.setNetworkTimeout(executor, milliseconds);
    }

    @Override
    public int getNetworkTimeout() throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        return connection.getNetworkTimeout();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        return connection.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        if (!isBusy()) {
            throw new SQLException("Connection is closed");
        }
        return connection.isWrapperFor(iface);
    }
}
