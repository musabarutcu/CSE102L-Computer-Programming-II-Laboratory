public class towerDefense {
    public static void main(String[] args) {

        // TEST 1
        System.out.println("[Wave 1]");
        double[][] wave1 = {
            {10.5, 20.0, 2.0, 50.0},
            {50.0, 50.0, 5.0, 100.0},
            {5.0, 5.0, 0.0, 30.0}
        };
        SniperTower tower1 = new SniperTower(0.0, 0.0, 30.0, 15, 5, 3);
        int defeated1 = tower1.engageEnemies(wave1);
        System.out.println("Enemies Defeated: " + defeated1 + " / 3");
        System.out.println("Remaining Ammo: " + tower1.getAmmo() + "\n");

        // TEST 2
        System.out.println("[Wave 2]");
        double[][] wave2 = {
            {10.0, 10.0, 50.0, 100.0}
        };
        SniperTower tower2 = new SniperTower(0.0, 0.0, 30.0, 15, 5, 3);
        int defeated2 = tower2.engageEnemies(wave2);
        System.out.println("Enemies Defeated: " + defeated2 + " / 1");
        System.out.println("Remaining Ammo: " + tower2.getAmmo() + "\n");

        // TEST 3
        System.out.println("[Wave 3]");
        double[][] wave3 = {
            {0.0, 5.0, 1.0, 10.0},
            {0.0, -5.0, 1.0, 10.0},
            {5.0, 0.0, 1.0, 10.0},
            {-5.0, 0.0, 1.0, 10.0}
        };
        SniperTower tower3 = new SniperTower(0.0, 0.0, 30.0, 15, 5, 3);
        int defeated3 = tower3.engageEnemies(wave3);
        System.out.println("Enemies Defeated: " + defeated3 + " / 4");
        System.out.println("Remaining Ammo: " + tower3.getAmmo());
    }
}

class Tower {
    private double x;
    private double y;
    private double range;
    private int baseDamage;
    
    Tower(double x, double y, double range, int baseDamage){
        this.x = x;
        this.y = y;
        this.range = range;
        this.baseDamage = baseDamage;
    }
    
    public double calculateDistance(double enemyX, double enemyY){
        return Math.sqrt(Math.pow(enemyX - x, 2) + Math.pow(enemyY - y, 2));
    }
    
    public boolean canHit(double enemyX, double enemyY){
        return calculateDistance(enemyX, enemyY) <= range;
            
    }
    
    public int getBaseDamage(){
        return baseDamage;    
    } 
    
    
}

class SniperTower extends Tower {
    private int ammo;
    private int criticalMultiplier;
    private int shotsFired;
    
    SniperTower(double x, double y, double range, int baseDamage, int ammo, int criticalMultiplier){
        super(x, y, range, baseDamage);
        this.ammo = ammo;
        this.criticalMultiplier = criticalMultiplier;
        this.shotsFired = 0;
    }
    
    public int getAmmo(){
        return ammo;
    }
    
    public int fire(double enemyX, double enemyY, int enemyArmor){
        if(!canHit(enemyX, enemyY) || ammo <= 0){
            return 0;
        }
        ammo--;
        shotsFired++;
        int damage = getBaseDamage();
        
        if(shotsFired % 3 == 0){ 
            damage *= criticalMultiplier;
        }
        
        damage -= enemyArmor;
        if(damage < 0){
            damage = 0;
        }
        return damage;
    }

    public void reload(int bulletsToAdd){
        for(int i = 0; i < bulletsToAdd; i++){
            ammo++; 
        }
    }
    
    public int engageEnemies(double[][] enemies){
        if(enemies.length == 0){
            return 0;
        }
        
        int defeated = 0;
        
        for(int i = 0; i < enemies.length; i++) {
            double enemyX = enemies[i][0];
            double enemyY = enemies[i][1];
            int armor = (int)enemies[i][2];
            int health = (int)enemies[i][3];
            
            if(!canHit(enemyX, enemyY)){
                continue;
            }
            while(health > 0){
                if(ammo == 0){
                    reload(5);
                }
                int damage = fire(enemyX, enemyY, armor);
                
                if(damage == 0){
                    break;
                }
                
                health  -= damage;
                
                if(health <= 0){
                    defeated++;
                }
            }
        }
        
        return defeated;
    }
    
    
}
