package BLL.Validators;

import Model.Product;

public class ProductValidator implements Validator<Product> {


    /**
     * @param product
     */
    public void validate(Product product) {
        String error = "";
        if (product.getName().length() == 0)
            error += "invalid name!\n";
        if (product.getPrice() < 0)
            error += "invalid price!\n";
        if (product.getCurrentStock() < 0)
            error += "invalid stock!\n";
        if (error.length()!=0)
            throw new IllegalArgumentException(error);
    }

}
