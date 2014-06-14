package org.hillel.it.votecollector.repository.Impl;

import org.hillel.it.votecollector.IOopperations.ConnectionPool;
import org.hillel.it.votecollector.IOopperations.ReusableConnectionPool;
import org.hillel.it.votecollector.model.entity.Subject;
import org.hillel.it.votecollector.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.sql.*;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 09.12.13
 * Time: 22:22
 */
@Service("SubjectRepositoryImpl")
public class SubjectRepositoryImpl implements SubjectRepository {

    private String dsn = "jdbc:derby:VoteCollectorDb;create=true";
    private String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private ConnectionPool connectionPool;

    public SubjectRepositoryImpl() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.out.println("Driver " + driver + " not found");
            e.printStackTrace();
        }

        connectionPool = new ReusableConnectionPool(10, dsn);
        try (Connection connection = connectionPool.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.execute("CREATE TABLE SUBJECTS(id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), creationDate DATE , changeDate DATE , subject varchar(30), description varchar(100))");
            }
        } catch (SQLException e) {
        }

    }

    @Override
    public Subject getSubjectById(int id) {
        String stateString = "SELECT * FROM SUBJECTS WHERE ID=?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(stateString)) {
                statement.setInt(1, id);
                ResultSet rs = statement.executeQuery();
                statement.close();
                if (!rs.next()) return null;
                Subject subject = new Subject();
                subject.setId(rs.getInt("id"));
                subject.setCreationDate(new java.util.Date(rs.getDate("creationDate").getTime()));
                subject.setChangeDate(new java.util.Date(rs.getDate("changeDate").getTime()));
                subject.setSubject(rs.getString("subject"));
                subject.setDescription(rs.getString("description"));
                rs.close();
                return subject;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void saveSubject(Subject subject) {
        String stateString;
        boolean isNew = false;
        if (subject.getId() == 0) isNew = true;
        if (!isNew)
            stateString = "UPDATE SUBJECTS SET creationDate = ?, changeDate = ?, subject = ?, description = ? WHERE ID=?";
        else
            stateString = "INSERT INTO SUBJECTS (creationDate, changeDate, subject, description) VALUES (?, ? ,? , ?)";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(stateString)) {
                statement.setDate(1, new java.sql.Date(subject.getCreationDate().getTime()));
                statement.setDate(2, new java.sql.Date(subject.getChangeDate().getTime()));
                statement.setString(3, subject.getSubject());
                statement.setString(4, subject.getDescription());
                if (!isNew) statement.setInt(5, subject.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int countSubjects() {
        int count;
        String query = "SELECT COUNT(*) AS count FROM SUBJECTS";
        try (Connection connection = connectionPool.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                ResultSet rs = statement.executeQuery(query);
                if (!rs.next()) return 0;
                count = rs.getInt("count");
                statement.close();
                rs.close();
                return count;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error count subjects");
        }
    }

    @Override
    public void deleteSubject(int id) {
        String stateString = "DELETE FROM SUBJECTS WHERE id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(stateString)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy

    public void shutDown() {
        connectionPool.closePool();
    }
}
