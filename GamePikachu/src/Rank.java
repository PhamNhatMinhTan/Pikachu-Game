/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Minh Tân
 */
public class Rank {

    //declare variables
    private String name;
    private int score;

    /**
     * init object rank
     * @param name
     * @param score 
     */
    public Rank(String name, int score) {
        this.name   = name;
        this.score  = score;
    }
    
    /**
     * get name
     * @return 
     */
    public String getName() {
        return name;
    }

    /**
     * set name
     * @param name
     * @throws Exception 
     */
    public void setName(String name) throws Exception {
        if (name.equals("")) {
            throw new Exception("Please enter you name");
        } else {
            this.name = name;
        }
    }

    /**
     * get scores
     * @return 
     */
    public int getScore() {
        return score;
    }

    /**
     * set scores
     * @param score 
     */
    public void setScore(int score) {
        this.score = score;
    }

}
