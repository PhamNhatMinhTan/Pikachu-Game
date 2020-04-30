
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SnowGroup
 */
public class Randomize {
    //class random number
    private static Random ran = new Random();
    
    /**
     * random from min to max value
     * @param min
     * @param max
     * @return 
     */
    public static int Random(int min, int max) {
        
        return ran.nextInt((max - min) + 1) + min;
    }
}
