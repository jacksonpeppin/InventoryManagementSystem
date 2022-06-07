package sample.Model;

/**
 * This class represents the Outsourced object, inheriting from the part class.
 */
public class Outsourced extends Part {
    private String companyName;

    /**
     * outsourced part constructor
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param CompanyName
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String CompanyName) {
        super(id, name, price, stock, min, max);
    }

    /**
     * set company name
     * @param companyName
     */
    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }

    /**
     * get company name
     * @return company name
     */
    public String getCompanyName() {
        return companyName;
    }

}
