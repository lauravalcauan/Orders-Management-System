package DataAccess;

import Connection.ConnectionFactory;
import Model.Client;
import Model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderDAO {

    protected static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO Orders (idClient,idProduct,quantity)" + " VALUES(?,?,?)";
    private final static String findStatementString  = "SELECT * FROM Orders where id = ? ";
    private final static String deleteStatementString  = "DELETE FROM Orders where id = ? ";
    private final static String editStatementString  = "UPDATE Orders SET idClient = ?, idProduct = ?, quantity = ? where id = ? ";
    private final static String findAllStatementString  = "SELECT * FROM Orders";
    private final static String findByIdsStatementString = "SELECT * FROM Orders where idClient=? and idProduct=?";


    /**
     * @param order
     */
    public void insert(Order order) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(insertStatementString);
            statement.setInt(1, order.getIdClient());
            statement.setInt(2, order.getIdProduct());
            statement.setInt(3, order.getQuantity());
            statement.executeUpdate();

        } catch(SQLException e) {
            LOGGER.log(Level.WARNING,  "OrderDAO:add " + e.getMessage());
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
    public Order findById(int id) {
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
                int idProduct = resultSet.getInt("idProduct");
                int idClient = resultSet.getInt("idClient");
                int quantity = resultSet.getInt("quantity");
                return new Order(id, idClient, idProduct, quantity);
            }
            return null;

        } catch(SQLException e) {
            LOGGER.log(Level.WARNING,  "OrderDAO:findById " + e.getMessage());
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
    public List<Order> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(findAllStatementString);
            resultSet = statement.executeQuery();
            List<Order> orders = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int idClient = resultSet.getInt("idClient");
                int idProduct = resultSet.getInt("idProduct");
                int quantity = resultSet.getInt("quantity");
                orders.add(new Order(id, idClient, idProduct, quantity));
            }
            return orders;

        } catch(SQLException e) {
            LOGGER.log(Level.WARNING,  "OrderDAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return null;
    }

    /**
     * @param orderId
     */
    public void delete(int orderId) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement deleteStatement = null;
        ResultSet resultSet = null;
        try {
            deleteStatement = dbConnection.prepareStatement(deleteStatementString);
            deleteStatement.setInt(1, orderId);
            deleteStatement.executeUpdate();

        } catch(SQLException e) {
            LOGGER.log(Level.WARNING,"OrderDAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(dbConnection);
        }

    }

    /**
     * @param order
     */
    public void edit(Order order) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement editStatement = null;

        try {
            editStatement = dbConnection.prepareStatement(editStatementString);
            editStatement.setInt(1, order.getId());
            editStatement.setInt(2, order.getIdClient());
            editStatement.setInt(3, order.getIdProduct());
            editStatement.setInt(4, order.getQuantity());
            editStatement.executeUpdate();

        } catch(SQLException e) {
            LOGGER.log(Level.WARNING, "OrderDAO:edit " + e.getMessage());
        } finally {
            ConnectionFactory.close(editStatement);
            ConnectionFactory.close(dbConnection);
        }

    }

    /**
     * @param idClient
     * @param idProduct
     * @return
     */
    public Order findByIds(int idClient, int idProduct) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(findByIdsStatementString);
            statement.setInt(1, idClient);
            statement.setInt(2, idProduct);
            resultSet = statement.executeQuery();
            if (resultSet.next())
            {
                int id = resultSet.getInt("id");
                int quantity = resultSet.getInt("quantity");
                return new Order(id, idClient, idProduct, quantity);
            }
            return null;

        } catch(SQLException e) {
            LOGGER.log(Level.WARNING,  "OrderDAO:findByIds " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return null;
    }

}
