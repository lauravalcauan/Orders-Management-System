package BLL;

import BLL.Validators.ProductValidator;
import BLL.Validators.Validator;
import DataAccess.ProductDAO;
import Model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ProductBLL {
    private ProductValidator validator;
    private ProductDAO productDAO;

    public ProductBLL() {
        this.validator = new ProductValidator();
        this.productDAO = new ProductDAO();
    }

    /**
     * @param id
     * @return
     */
    public Product findProductById(int id) {
        Product product = productDAO.findById(id);
        if(product == null) {
            throw new NoSuchElementException("The product with id= " + id + " was not found!");
        }

        return product;
    }

    /**
     * @param product
     */
    public void insertProduct(Product product) {
        validator.validate(product);
        productDAO.insert(product);
    }

    /**
     * @param id
     */
    public void deleteProduct(int id){
        productDAO.delete(id);
    }

    /**
     * @param product
     */
    public void editProduct(Product product) {
        validator.validate(product);
        productDAO.edit(product);
    }

    /**
     * @return
     */
    public List<Product> findAllProduct(){
        return productDAO.findAll();
    }
}
