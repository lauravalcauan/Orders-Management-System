package DataAccess;

import Connection.ConnectionFactory;
import Model.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientDAO {

    protected static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO client (name,address,email,age)" + " VALUES(?,?,?,?)";
    private final static String findStatementString  = "SELECT * FROM client where id = ? ";
    private final static String deleteStatementString  = "DELETE FROM client where id = ? ";
    private final static String editStatementString  = "UPDATE client SET name = ?, address = ?, email = ?,  age = ? where id = ? ";
    private final static String findAllStatementString  = "SELECT * FROM client";


    /**
     * @param client
     */
    public void insert(Client client) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(insertStatementString);
            statement.setString(1, client.getName());
            statement.setString(2, client.getAddress());
            statement.setString(3, client.getEmail());
            statement.setInt(4, client.getAge());
            statement.executeUpdate();

        } catch(SQLException e) {
            LOGGER.log(Level.WARNING,  "ClientDAO:add " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * @param id
     * @return
     */
    public Client findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(findStatementString);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next())
            {
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                String email = resultSet.getString("email");
                int age = resultSet.getInt("age");
                return new Client(id, name, address, email, age);
            }
            return null;

        } catch(SQLException e) {
            LOGGER.log(Level.WARNING,  "ClientDAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return null;
    }

    /**
     * @return
     */
    public List<Client> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(findAllStatementString);
            resultSet = statement.executeQuery();
            List<Client> clients = new ArrayList<Client>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                String email = resultSet.getString("email");
                int age = resultSet.getInt("age");
                clients.add(new Client(id, name, address, email, age));
            }
            return clients;

        } catch(SQLException e) {
            LOGGER.log(Level.WARNING,  "ClientDAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return null;
    }

    /**
     * @param clientId
     */
    public void delete(int clientId) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement deleteStatement = null;
        ResultSet resultSet = null;
        try {
            deleteStatement = dbConnection.prepareStatement(deleteStatementString);
            deleteStatement.setInt(1, clientId);
            deleteStatement.executeUpdate();

        } catch(SQLException e) {
            LOGGER.log(Level.WARNING,"ClientDAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(dbConnection);
        }

    }

    /**
     * @param client
     */
    public void edit(Client client) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement editStatement = null;

        try {
            editStatement = dbConnection.prepareStatement(editStatementString);
            editStatement.setString(1, client.getName());
            editStatement.setString(2, client.getAddress());
            editStatement.setString(3, client.getEmail());
            editStatement.setInt(4, client.getAge());
            editStatement.setInt(5, client.getId());
            editStatement.executeUpdate();

        } catch(SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:edit " + e.getMessage());
        } finally {
            ConnectionFactory.close(editStatement);
            ConnectionFactory.close(dbConnection);
        }

    }

}
