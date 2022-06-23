package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;



public class Outsourced extends Part {

    protected StringProperty companyName;

    public Outsourced(int id, String name, double price, int stock, int max, int min, String companyName) {
        super(id, name, price, stock, max, min);
        this.companyName = new SimpleStringProperty(companyName);
    }


    //Returns company name
    public String getCompanyName() {
        return this.companyName.get();
    }

    //sets the company name
    public final void setCompanyName(String cname) {
        this.companyName.set(cname);
    }

}