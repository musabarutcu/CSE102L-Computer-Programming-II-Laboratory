import java.util.*;

public class FactoryLogisticsSimulator {
    public static void main(String[] args) {
        int factoryX = 0; int factoryY = 0;

        MachineUnit[][] machineGrid = new MachineUnit[2][2];
        machineGrid[0][0] = new WiringMachine("Arm Auto-Wirer");
        machineGrid[0][1] = null; 
        machineGrid[1][0] = new WiringMachine("Core Solderer");
        machineGrid[1][1] = null;

        Queue<TechModule> assemblyQueue = new ArrayDeque<>();
        assemblyQueue.add(new ServerUnit("Enterprise Blade", 10, 15, 4));
        assemblyQueue.add(new DroneUnit("AeroScout Pro", 20, 25, 5.0));

        FactoryFloor floor = new FactoryFloor(machineGrid, assemblyQueue);
        floor.runAssemblyLine();

        System.out.println("--- LOGISTICS SUMMARY ---");
        Stack<TechModule> shippingStack = floor.getReadyModules();
        double totalInvoicedValue = 0.0;
        
        while (!shippingStack.isEmpty()) {
            TechModule module = shippingStack.pop();
            double logisticsFee = module.calculateShippingFee(factoryX, factoryY); 
            double finalInvoicePrice = module.getManufacturingCost() + logisticsFee;
            totalInvoicedValue += finalInvoicePrice;
            
            System.out.printf("Shipping %s. Total: $%.2f\n", module.getModelName(), finalInvoicePrice);
        }
        System.out.printf("Total Value Shipped: $%.2f\n", totalInvoicedValue);
    }
}



interface IShippable{
    
    public double calculateShippingFee(int startX, int startY);
}


abstract class TechModule implements IShippable{
    private String modelName;
    private int bayX;
    private int bayY;
    private double manufacturingCost;
    
    
    public TechModule(String modelName, int bayX, int bayY){
        this.modelName = modelName;
        this.bayX = bayX;
        this.bayY = bayY;
        this.manufacturingCost = 0.0;
    }
    
    public String getModelName(){
        return this.modelName;
    }
    
    public int getBayX(){
        return this.bayX;
    }
    
    public int getBayY(){
        return this.bayY;
    }
    
    public double getManufacturingCost(){
        return this.manufacturingCost;
    }
    
    
    public void setBayX(int bayX){
        this.bayX = bayX;
    }
    
    public void setBayY(int bayY){
        this.bayY = bayY;
    }
    
    public void setManufacturingCost(double manufacturingCost){
        this.manufacturingCost = manufacturingCost;
    }
    
    public double calculateShippingFee(int startX, int startY){
        return (Math.abs(bayX - startX) + Math.abs(bayY - startY)) * 15.0;
    }
    
}

class ServerUnit extends TechModule{
    
    public ServerUnit(String modelName, int bayX, int bayY, int cpuCount){
        super(modelName, bayX, bayY);
        setManufacturingCost(500.0 + (cpuCount * 120.0));
    }
    
    
}



class DroneUnit extends TechModule{
    
    
    public DroneUnit(String modelName, int bayX, int bayY, double cubicFrameSize){
        super(modelName,bayX,bayY);
        setManufacturingCost(100.0 + (cubicFrameSize * cubicFrameSize * cubicFrameSize * 0.2));
    }
    
    
}


abstract class MachineUnit{
    private String name;
    
    public MachineUnit(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    
    public abstract void processHardware(TechModule module);
    
}


class WiringMachine extends MachineUnit{
    
    public WiringMachine(String name){
        super(name);
    }
    
    public void processHardware(TechModule module){
        module.setManufacturingCost(module.getManufacturingCost() + 50.0);
    }
}

class WarehouseBin<T> {
    private Object[] parts;
    private int count;
    
    public WarehouseBin(int capacity){
        this.parts = new Object[capacity];
        this.count = 0;
    }
    
    public void addPart(T part){
        if(count < parts.length){
            parts[count] = part;
            count++;
        }
    }
    
    @SuppressWarnings("unchecked")
    public T retrievePart(){
        if(count > 0){
            count--;
            return (T) parts[count];
        }
        return null;
    }
    

}

class FactoryFloor{
    private MachineUnit[][] grid;
    private Queue<TechModule> chassisQueue;
    private Stack<TechModule> readyModules;
    
    public FactoryFloor(MachineUnit[][] grid, Queue<TechModule> chassisQueue){
        this.grid = grid;
        this.chassisQueue = chassisQueue;
        this.readyModules = new Stack<>();
    }
    
    public void runAssemblyLine(){
        while (!chassisQueue.isEmpty()) {
            TechModule module = chassisQueue.poll();
            for (int i = 0; i < grid.length; i++) {
                if (grid[i] != null) {
                    for (int j = 0; j < grid[i].length; j++) {
                        if (grid[i][j] != null) {
                            grid[i][j].processHardware(module);
                        }
                    }
                }
            }
            readyModules.push(module);
        }
    }
    
    public Stack<TechModule> getReadyModules(){
        return readyModules;
        
    }
    
    
}
