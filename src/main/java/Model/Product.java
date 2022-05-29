package Model;

public class Product {
    private String name;
    private float price;
    private int currentStock;
    private int id;

    /**
     * @param id
     * @param name
     * @param price
     * @param currentStock
     */
    public Product(int id, String name, float price, int currentStock) {
        super();
        this.name = name;
        this.price = price;
        this.currentStock = currentStock;
        this.id = id;
    }

    /**
     * @param name
     * @param price
     * @param currentStock
     */
    public Product(String name, float price, int currentStock) {
        super();
        this.name = name;
        this.price = price;
        this.currentStock = currentStock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(int currentStock) {
        this.currentStock = currentStock;
    }
}
