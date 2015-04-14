package bitpikteam.project.bitcamp.ba.bitpik.Models;

/**
 * Created by nedzadhamzic on 14/04/15.
 */
public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private boolean isSold;
    private boolean isSpecial;
    private String productImagePath;

    public Product() {

    }

    public Product(int id, String name, String description, double price, boolean isSold, boolean isSpecial, String productImagePath) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.isSold = isSold;
        this.isSpecial = isSpecial;
        this.productImagePath = productImagePath;
    }

    public String toString() {
        return name + "       \n      " + String.valueOf(price);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isSold() {
        return isSold;
    }

    public void setSold(boolean isSold) {
        this.isSold = isSold;
    }

    public boolean isSpecial() {
        return isSpecial;
    }

    public void setSpecial(boolean isSpecial) {
        this.isSpecial = isSpecial;
    }

    public String getProductImagePath() {
        return productImagePath;
    }

    public void setProductImagePath(String productImagePath) {
        this.productImagePath = productImagePath;
    }

}
