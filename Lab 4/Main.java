public class Main {
    public static void main(String[] args) {
        
        LaserBeam[] lasers = {
            new LaserBeam(30.0, 100.0),
            new LaserBeam(45.0, 100.0),
            new LaserBeam(60.0, 100.0)
        };

        OpticalElement[][] facilityGrid = {
            { new FrostedGlass("Glass1"), new Prism("Prism1"), new Mirror("Mirror1"), new FrostedGlass("Glass2") },
            { new Prism("Prism2"), new Prism("Prism3"), new Mirror("Mirror2"), new Mirror("Mirror3") },
            { new FrostedGlass("Glass3"), new FrostedGlass("Glass4"), new FrostedGlass("Glass5"), new Mirror("Mirror4") }
        };

        OpticalFacility facility = new OpticalFacility(facilityGrid, lasers);

        System.out.println("--- SIMULATION STARTING ---\n");
        facility.fireBeams();

        System.out.println("--- STATISTICS ---");
        double totalAbsorbed = facility.calculateTotalAbsorbedEnergy();
        System.out.println("Total Energy Absorbed by the Entire Facility: " + totalAbsorbed);
        
        double validation = lasers[0].getEnergy() + lasers[1].getEnergy() + lasers[2].getEnergy() + totalAbsorbed;
        System.out.println("System Validation (Total Entering Energy 300.0 = Outputs + Absorbed): " + validation);
    }
}

class LaserBeam {
    private double angle;
    private double energy;
    
    public LaserBeam(double angle, double energy){
        this.angle = angle;
        this.energy = energy;
    }
    
    public double getAngle(){
        return this.angle;
    }
    
    public double getEnergy(){
        return this.energy;
    }
    
    public void setAngle(double angle){
        this.angle = angle;
    }
    
    public void setEnergy(double energy){
        this.energy = energy;
    }
    
}

interface EnergyAbsorber {
    
    public double getAbsorbedEnergy();
    
}



abstract class OpticalElement {
    private  String name;
    
    public OpticalElement(String name){
        this.name = name;
    }
    
    public String getName(){
        return this.name;
    }
    
    public abstract void processBeam(LaserBeam beam);
    

}



class Mirror extends OpticalElement{
    
    public Mirror(String name){
        super(name);
    }
    
    @Override
    public void processBeam(LaserBeam beam){
        beam.setAngle(180.0 - beam.getAngle());
        
    }
    
    
}



class FrostedGlass extends OpticalElement implements EnergyAbsorber {
    
    private double retainedEnergy = 0.0;
    
    public FrostedGlass(String name){
        super(name);
    }
    
     @Override
     public void processBeam(LaserBeam beam){
         double absorbed = beam.getEnergy() * 0.30;
         beam.setEnergy(beam.getEnergy() - absorbed);
         retainedEnergy += absorbed;
         
     }
     
     @Override
     public double getAbsorbedEnergy(){
        return retainedEnergy;
    }
    

}


class Prism extends OpticalElement implements EnergyAbsorber {
    
    private double retainedEnergy = 0.0;
    
    public Prism(String name){
        super(name);
    }
    
    @Override
    public void processBeam(LaserBeam beam){
        double absorbed = beam.getEnergy() * 0.10;
        beam.setEnergy(beam.getEnergy() - absorbed);
        beam.setAngle(beam.getAngle() / 1.5);
        retainedEnergy += absorbed;
        
    }
    
    @Override
    public double getAbsorbedEnergy(){
        return retainedEnergy;
        
    }
    
}


class OpticalFacility {
    
    private OpticalElement[][] grid ;
    private LaserBeam[] lasers;
    
    public OpticalFacility(OpticalElement[][] grid, LaserBeam[] lasers){
        this.grid = grid;
        this.lasers = lasers;
    }
    
    public void fireBeams(){
        if (grid == null || lasers == null || grid.length == 0 || lasers.length == 0) {
            return;
        }
        for(int i = 0; i < grid.length; i++){
            if (i >= lasers.length) {
                break;
            }
            LaserBeam beam = lasers[i];
            if (grid[i] != null) {
                for(int j = 0; j < grid[i].length; j++){
                    if(grid[i][j] != null){
                        grid[i][j].processBeam(beam);
                    }
                }
            }
            System.out.println("Line " + i + " Output Angle: " + beam.getAngle());
            System.out.println("Line " + i + " Output Energy: " + beam.getEnergy() + "\n");
        }
        
    }
    
    public double calculateTotalAbsorbedEnergy(){
        double total = 0.0;
        if (grid == null) {
            return total;
        }
        for(int i = 0; i < grid.length; i++){
            if(grid[i] != null){
                for(int j = 0; j < grid[i].length; j++){
                    if(grid[i][j] instanceof EnergyAbsorber){
                        total += ((EnergyAbsorber) grid[i][j]).getAbsorbedEnergy();
                    }
                }
            }
        }
        return total; 
        
        
    }
    
    
    
}
