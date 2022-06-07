package sample.Model;

/**
 * This class represents the inhouse object, inheriting from the part class.
 */
public class InHouse extends Part
{
    private int machineID;

    /**
     * construct inhouse object.
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machineID
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID)
    {
        super(id, name, price, stock, min, max);
    }

    /**
     * set machine id of inhouse object.
     * @param machineID
     */
    public void setMachineID(int machineID)
    {
        this.machineID = machineID;
    }

    /**
     * get machine id of inhouse object.
     * @return machine id
     */
    public int getMachineID()
    {
        return machineID;
    }

}
