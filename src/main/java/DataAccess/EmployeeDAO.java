package DataAccess;

import Model.Client;
import Connection.ConnectionFactory;
import Model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeeDAO {

    protected static final Logger LOGGER = Logger.getLogger(EmployeeDAO.class.getName());
    private final static String findStatementString  = "SELECT * FROM employee where username=? ";

    /**
     * @param username
     * @return
     */
    public Employee findByUsername(String username) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(findStatementString);
            statement.setString(1, username);
            resultSet = statement.executeQuery();
            if (resultSet.next())
            {
                String password = resultSet.getString("password");
                return new Employee(username, password);
            }

        } catch(SQLException e) {
            LOGGER.log(Level.WARNING,  "EmployeeDAO:findByUsername " + e.getMessage());
            System.out.println(e.getMessage());

        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return null;
    }

}
