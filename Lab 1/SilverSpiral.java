import java.util.Scanner;

/**
 * CS1 Lab B: The Silver Spiral
 * Fill a 2D matrix with Lucas numbers in a counter-clockwise spiral pattern.
 * 
 * @author [Musa Barutcu]
 * @version 1.0
 */
public class SilverSpiral {
    
    /**
     * Main method - PROVIDED TO STUDENTS (DO NOT MODIFY)
     */
    public static void main(String[] args) {
        System.out.println("=== Lab B: The Silver Spiral ===");
        System.out.println("Fill a matrix with Lucas numbers in counter-clockwise spiral.");
        
        int n = getMatrixSize();
        
        int[][] spiral = fillCounterClockwiseSpiral(n);
        
        int diagonalSum = calculateDiagonalSum(spiral);
        System.out.println("\nDiagonal Sum (Main + Anti): " + diagonalSum);
    }
    
    // STUDENT IMPLEMENTATION SECTION - COMPLETE THE METHODS BELOW    

    public static int getMatrixSize() {
        
        Scanner input = new Scanner(System.in);
        System.out.print("Please enter  size of matrix: ");
        int size = input.nextInt();
        if( size < 1 || 6 < size){
            System.out.println("ERROR: N must be between 1 and 6."); 
            System.exit(1);
        }
        return size;
           
        
    }
    
    public static int getLucas(int n) {
        if(n == 0){
            return 2;
        }
        else if(n == 1){
            return 1;
        }
        else{
            int a = 2;
            int b = 1;
            int c = 0;
            for(int i = 2; i <= n; i++){
                c = a + b;
                a = b;
                b = c;
            }
            return b;
        }

    }

    public static int[][] fillCounterClockwiseSpiral(int n) {
        
        int matrix[][] = new int[n][n];
        int index = 0;
        int top = 0;
        int bottom = n - 1;
        int left = 0;
        int right = n - 1;

        
        
        while(top <= bottom && left <= right){
            
            for(int i = top; i <= bottom; i++){
                matrix[i][left] = getLucas(index++);
            }
            left++;
            
            for(int i = left; i <= right; i++){
                matrix[bottom][i] = getLucas(index++);
            }
            bottom--;
            
            if (left <= right) {
                for(int i = bottom; i >= top; i--){
                    matrix[i][right] = getLucas(index++);  
                }
                right--;
            }
            
            if (top <= bottom) {
                for(int i = right; i >= left; i--){
                    matrix[top][i] = getLucas(index++);  
                }
                top++;
            }
        
        }      
        return matrix;
        
    }
    
    public static int calculateDiagonalSum(int[][] matrix) {
        int size = matrix.length;
        int main_d = 0;
        int anti_d = 0;
        int d_sum = 0;
        
        for(int i = 0; i <= size - 1; i++){
            main_d += matrix[i][i];
            anti_d += matrix[i][size - 1 - i];
        }

        
        d_sum = main_d + anti_d;
        
        if (size % 2 != 0) {
            d_sum -= matrix[size / 2][size / 2];
        }
        
        return d_sum;

    }
}