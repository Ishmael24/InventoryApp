package app.models;

/**
 * <h1>In house model class</h1>
 * InHouse class is a model of a part that is made in house.
 * This class inherits from the class Part.
 *
 * @author Ishmauel Martin
 */
public class InHouse extends Part {
    // machine id
    private int machineId;

    public InHouse(
            int id,
            String name,
            double price,
            int stock,
            int min,
            int max,
            int machineId
    ){
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

/**
 * This method sets the part machine id
 *
 * @param machineId
 * */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

/**
 * This method returns the part machineId
 *
 * @return machineId
 **/
    public int getMachineId(){
        return machineId;
    }
}
