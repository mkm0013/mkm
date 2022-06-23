package Model;


public class Inhouse extends Part{

   private int machineID;

    public Inhouse(int id, String name, double price, int stock, int max, int min, int machineID) {
        super(id, name, price, stock, max, min);
        this.machineID = machineID;
    }


        /*
        returns the machine ID
         */
        public int getMachineID() {
            return machineID;}

    /*
    sets the machine ID
    * */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
}



