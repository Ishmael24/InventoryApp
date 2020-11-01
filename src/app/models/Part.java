package app.models;


/**
 * Part class is an abstract of a Part
 *
 * @author Ishmauel Martin
 */
public abstract class Part {
    // part id
    protected int id;

    // part name
    private String name;

    // part price
    private double price;

    // part stock
    private int stock;

    // part min
    private int min;

    // part max
    private int max;

    public  Part(){}
    public Part(int id,
                String name,
                double price,
                int stock,
                int min,
                int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * This method sets the ID of a part.
     *
     * @param id is the id of the part
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * This method sets the name of a part.
     *
     * @param name is the name of the part
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * This method sets the price of a part.
     *
     * @param price is the price of a part
     */
    public void setPrice(double price){
        this.price = price;
    }

    /**
     * This method sets the stock/inventory level of the part.
     *
     * @param stock is the stock/inventory level
     */
    public void setStock(int stock){
        this.stock = stock;
    }

    /**
     * This method sets the min value.
     *
     * @param min is the min  value
     */
    public void setMin(int min){
        this.min = min;
    }

    /**
     * Sets the max amount of the part
     *
     * @param max
     */
    public void setMax(int max){
        this.max = max;
    }

    /**
     * This method gets the part id.
     *
     * @return id is the id of the product
     */
    public int getId(){
        return id;
    }

    /**
     * This methods get the name of a part.
     *
     * @return name is the name of the part
     */
    public String getName(){
        return name;
    }

    /**
     * Gets the part price
     *
     * @return price
     */
    public double getPrice(){
        return price;
    }

    /**
     * Gets the part stock
     *
     * @return stock
     */
    public int getStock(){
        return stock;
    }

    /**
     * Gets the part min amount
     *
     * @return min
     */
    public int getMin(){
        return min;
    }

    /**
     * Gets the part max
     *
     * @return max
     */
    public int getMax(){
        return max;
    }

}
