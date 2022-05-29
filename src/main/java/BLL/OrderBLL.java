package BLL;

import DataAccess.OrderDAO;
import DataAccess.ProductDAO;
import Model.Order;
import Model.Product;
import Model.Client;
import BLL.Validators.ClientValidator;
import BLL.Validators.Validator;
import DataAccess.ClientDAO;
import Model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class OrderBLL {
    private ProductDAO productDAO;
    private OrderDAO orderDAO;

    public OrderBLL() {
        this.productDAO = new ProductDAO();
        this.orderDAO = new OrderDAO();
    }

    /**
     * @param id
     * @return
     */
    public Order findById(int id)
    {
        return orderDAO.findById(id);
    }


    /**
     * @param order
     * @throws Exception
     */
    public void insertOrder(Order order) throws Exception {
        Product product = productDAO.findById(order.getIdProduct());
        int stock = product.getCurrentStock();
        if (order.getQuantity() < 1)
            throw new Exception("invalid quantity!");

        if(stock < order.getQuantity()) {
            throw new Exception("Not enough in stock!");
        } else {
            product.setCurrentStock(product.getCurrentStock() - order.getQuantity());
            productDAO.edit(product);
            orderDAO.insert(order);
        }
    }

    /**
     * @param order
     */
    public void deleteOrder(Order order) {
        orderDAO.delete(order.getId());
        Product product = productDAO.findById(order.getIdProduct());
        product.setCurrentStock(product.getCurrentStock() + order.getQuantity());
        productDAO.edit(product);

    }

    /**
     * @return
     */
    public List<Order> findAllOrders(){
        return orderDAO.findAll();
    }
}

