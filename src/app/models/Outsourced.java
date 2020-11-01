package app.models;

/**
 * OutHouse class is a model for a part that is made out source.
 * This class inherits from the class Part.
 *
 * @author Ishmauel Martin
 */
public class Outsourced extends Part {
    private String companyName;

    public Outsourced(
            int id,
            String name,
            double price,
            int stock,
            int min,
            int max,
            String companyName
    ){
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * This method sets the company name
     *
     * @param companyName
     * */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * This method gets the company name
     *
     * @return companyName
     * */
    public String getCompanyName(){
        return companyName;
    }
}
