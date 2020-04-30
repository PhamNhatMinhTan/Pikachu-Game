
import java.util.Comparator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author GroupSnow
 */
public class SortPlayersByScores implements Comparator<Rank>{
    /**
     * compare 2 scores
     * @param Player1
     * @param Player2
     * @return 
     */
    @Override
    public int compare(Rank Player1, Rank Player2) {
        if (Player1.getScore() < Player2.getScore()) {
            return 1;
        }
        return -1;
    }
    
}
