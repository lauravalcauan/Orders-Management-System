package DataAccess;

import Connection.ConnectionFactory;
import Model.Client;
import Model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ProductDAO {

    protected static final Logger LOGGER = Logger.getLogger(ProductDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO product (name,price,currentStock)" + " VALUES(?,?,?)";
    private final static String findStatementString  = "SELECT * FROM product where id = ? ";
    private final static String deleteStatementString  = "DELETE FROM product where id = ? ";
    private final static String editStatementString  = "UPDATE product SET name = ?, price = ?, currentStock = ? where id = ? ";
    private final static String findAllStatementString  = "SELECT * FROM product";


    /**
     * @param product
     */
    public void insert(Product product) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(insertStatementString);
            statement.setString(1, product.getName());
            statement.setFloat(2, product.getPrice());
            statement.setInt(3, product.getCurrentStock());
            statement.executeUpdate();

        } catch(SQLException e) {
            LOGGER.log(Level.WARNING,  "ProductDAO:add " + e.getMessage());
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
    public Product findById(int id) {
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
                int price = resultSet.getInt("price");
                int currentStock = resultSet.getInt("currentStock");
                return new Product(id, name, price, currentStock);
            }
            return null;

        } catch(SQLException e) {
            LOGGER.log(Level.WARNING,  "ProductDAO:findById " + e.getMessage());
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
    public List<Product> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(findAllStatementString);
            resultSet = statement.executeQuery();
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                int currentStock = resultSet.getInt("currentStock");
                products.add(new Product(id, name, price, currentStock));
            }
            return products;

        } catch(SQLException e) {
            LOGGER.log(Level.WARNING,  "ProductDAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return null;
    }

    /**
     * @param productId
     */
    public void delete(int productId) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement deleteStatement = null;
        ResultSet resultSet = null;
        try {
            deleteStatement = dbConnection.prepareStatement(deleteStatementString);
            deleteStatement.setInt(1, productId);
            deleteStatement.executeUpdate();

        } catch(SQLException e) {
            LOGGER.log(Level.WARNING,"ProductDAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(dbConnection);
        }

    }

    /**
     * @param product
     */
    public void edit(Product product) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement editStatement = null;

        try {
            editStatement = dbConnection.prepareStatement(editStatementString);
            editStatement.setString(1, product.getName());
            editStatement.setFloat(2, product.getPrice());
            editStatement.setInt(3, product.getCurrentStock());
            editStatement.setInt(4, product.getId());
            editStatement.executeUpdate();

        } catch(SQLException e) {
            LOGGER.log(Level.WARNING, "ProductDAO:edit " + e.getMessage());
        } finally {
            ConnectionFactory.close(editStatement);
            ConnectionFactory.close(dbConnection);
        }

    }

}
