
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author SnowGroup
 */
public class RankManagement {

    //declare variables
    private int numberOfPlayer;     //declare number of player
    private ArrayList<Rank> rank;   //declare array list contain player's name and score
    private String pathFile;        //declare URL to get players information

    /**
     * init object RankManagement
     *
     * @param RankFile
     * @throws Exception
     */
    public RankManagement(String pathFile) throws Exception {
        //create a array list to store information of player
        this.pathFile = pathFile;
        this.rank = new ArrayList<Rank>();
        //set number of player to 0
        numberOfPlayer = 0;
    }

    /**
     * Add name and score of user
     *
     * @param name
     * @param score
     */
    public void add(String name, int score) {
        this.rank.add(new Rank(name, score));
        this.numberOfPlayer++;
        //use insertsort to sort scores after add data
        //InsertionSort();
        //BubbleSort();
    }

    /**
     * User bubble sort to sort score of players
     */
    public void BubbleSort() {
        for (int i = 0; i < this.numberOfPlayer; i++) {
            for (int j = 0; j < this.numberOfPlayer; j++) {
                //assign scores of player for score1 and score2
                int score1 = this.rank.get(i).getScore();
                int score2 = this.rank.get(j).getScore();
                //compares scores of two player and swaps location of them
                if (score1 < score2) {
                    int tmp = score1;
                    score1 = score2;
                    score2 = tmp;
                }
            }
        }
    }
    
    /**
     * sort scores
     */
    public void sort() {
        Collections.sort(rank, new SortPlayersByScores());
    }

    /**
     * Load file
     *
     * @throws IOException
     */
    public void loadFile() throws IOException {
        //String path = getClass().getResource("/data/rank.txt").toString();
        //System.out.println(path);
        File rankFile = new File(pathFile);
        //If file not exist
        if (!rankFile.exists()) {
            rankFile.createNewFile();
            System.out.println("Please wait to create save file! So sorry because this problem!"
                    + "Done!");
            //create a number of player in file
            this.numberOfPlayer = 0;
        } else {
            //if file exist
            //Load data file to buffer
            BufferedReader br = new BufferedReader(new FileReader(this.pathFile));
            try {
                String line, name, score;
                //read number of player
                line = br.readLine();
                this.numberOfPlayer = Integer.parseInt(line);
                for (int i = 0; i < this.numberOfPlayer; i++) {
                    //read data rank file
                    name = br.readLine();
                    score = br.readLine();

                    //Create new instance of rank and add list rank of players
                    this.rank.add(new Rank(name, Integer.parseInt(score)));
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    //    }

    /**
     * save file
     *
     * @throws IOException
     */
    public void saveFile() throws IOException {
        //OverWrite file data
        FileWriter fw = new FileWriter(new File(pathFile), false);

        try {
            System.out.println("Saving data ...");

            //write number of players
            fw.append(String.valueOf(this.numberOfPlayer) + "\n");

            for (int i = 0; i < this.numberOfPlayer; i++) {
                //Init player's data
                String name = this.rank.get(i).getName();
                int score = this.rank.get(i).getScore();

                //Write player's data into data file
                fw.append(name + "\n");
                fw.append(String.valueOf(score) + "\n");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            //Save data file (from RAm to HDD)
            fw.close();
            System.out.println("Save Done!");
        }
    }

    /**
     * Find index of rank and return index
     *
     * @param ID
     * @return
     */
    public int findRank(String name) {
        for (int i = 0; i < this.rank.size(); i++) {
            //get information of rank at i
            Rank r = this.rank.get(i);
            //if ID inputed is same with ID in file then return i 
            if (r.getName().equals(name)) {
                return i;
            }
        }
        //if not found ID valid then return -1;
        return -1;
    }

    /**
     * Find rank by index of Rank ID
     *
     * @param ID
     * @return
     */
    public Rank getRank(String name) {
        int viTri = findRank(name);
        //check result from method findRank if it return -1 then return null
        if (viTri == -1) {
            return null;
        } else {
            //return information at that index
            return this.rank.get(viTri);
        }
    }

    /**
     * get rank list
     * @return 
     */
    public ArrayList<Rank> getListRank() {
        return rank;
    }

}
