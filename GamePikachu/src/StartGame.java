
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author SnowGroup
 */
public class StartGame extends javax.swing.JFrame {

    //declare variables
    public static final int Rows = 10;      // Number of rows of Game
    public static final int Cols = 12;      // Number of column of Game
    public static final int boardRows = Rows + 2;   //Total rows include 2 rows boder
    public static final int boardCols = Cols + 2;   //Total column include 2 rows boder
    private static boolean isWin = false;   // Final result is winner 
    private static boolean isLose = false, isTimeUp = false; //Final result is loser
    private final int couples = 20 * 3;     // Number of pair of image
    private int countCorrects = 0;          // Var count amount pair of image is correct
    private int point;                      // Scores of players
    private int live;                       // Number of times allows to wrong move
    private RankManagement rm;              // Module class RankManagement 
    public int[][] map;                     // Array 2D to create map game
    public Card[][] cards;                  // Array 2D to contain coordinates, value for image
    private int time;                       // Times to play game
    private ArrayList<Card> list;           // Array use to keep position (from, to) if image is clicked
    private int tmp;                        // var temp
    Thread timer;                           // Thread to set time
    Router router;                          // Module class router

    /**
     * Creates new form StartGame
     */
    public StartGame() {
        initComponents();
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/img_test/1.png"));
        this.setIconImage(icon);
        showGame();
        this.setLocationRelativeTo(null);
        router = new Router(this);
    }

    /**
     * reset game
     */
    public void reset() {
        try {
            //Generate Map >>> generateBorad Game
            generateMap();
            generateBoard();
            Card.reset();
            //set basic values for game: live, time and point
            live = 1;
            time = 300;
            point = 3000;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Generate Map to load image
     */
    public void generateMap() {
        //allocates memory for the matrix
        map = new int[boardRows][boardCols];
        Randomize ran = new Randomize();

        //create a border have the value = 0 around the image matrix
        //Top Rows and bottom Rows
        for (int i = 0; i < boardCols; i++) {
            map[0][i] = map[boardRows - 1][i] = 0;
        }
        //Frist col and last col
        for (int i = 0; i < boardRows; i++) {
            map[i][0] = map[i][boardCols - 1] = 0;
        }
        //Init the matrix with 1,1,1,1,1,1,2,2,2,2,2,2,3,3,3...        
        for (int i = 1, k = 6; i < boardRows - 1; i++) {
            for (int j = 1; j < boardCols - 1; j++, k++) {
                map[i][j] = (k / 6);
            }
        }
        showArray();
        //Generate the randomize matrix
        int r, c, temp;
        for (int i = 1; i < boardRows - 1; i++) {
            for (int j = 1; j < boardCols - 1; j++) {
                //random row (from 1 to 10) and col (from 1 to 12)
                r = Randomize.Random(1, boardRows - 2);
                c = Randomize.Random(1, boardCols - 2);

                System.out.println(r + "\t" + c);
                //Swap position for value of images
                temp = map[i][j];
                map[i][j] = map[r][c];
                map[r][c] = temp;
            }
        }
        showArray();
    }

    /**
     * Generate Board to insert image
     */
    public void generateBoard() {
        //allocates memory for cards
        cards = new Card[boardRows][boardCols];

        try {
            // Use GridLayout to create Labels to insert image
            pnlGame.setLayout(new GridLayout(boardRows, boardCols));
            pnlGame.setSize(50, 50);
            pnlGame.removeAll();
            pnlGame.revalidate();
            pnlGame.repaint();

            // Insert image into board by values of image have been save in "img_test2" file
            for (int i = 0; i < boardRows; i++) {
                for (int j = 0; j < boardCols; j++) {
                    cards[i][j] = new Card(this, i, j, map[i][j]);
                    pnlGame.add(cards[i][j]);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * show array
     */
    public void showArray() {
        for (int i = 0; i < boardRows; i++) {
            for (int j = 0; j < boardCols; j++) {
                System.out.printf("%3d", map[i][j]);
            }
            System.out.println("\n");
        }
    }

    /**
     * Set result after user's click
     *
     * @param correct
     * @throws Exception
     */
    public void setResult(boolean correct) throws Exception {
        //if incorect then -1 live and if live less than 0 then lose
        if (!correct) {
            live -= 1;
            if (live <= -1) {
                timer.stop();
                //set isLose = true
                isLose = true;
                //Notify for players that they have lose
                EndGame();
                //Save information of players
                saveScores();

            }
        } else {
            //if correct then plus point
            countCorrects++;
            //if correct 5 times in a row >>> live +1 and point +30;
            if (countCorrects % 5 == 0) {
                live += 1;
                point += 30;
            } else {
                //point +20;
                point += 20;
            }

            //if couple courrect = couples given then win
            if (countCorrects == couples) {
                timer.stop();
                //set isWin = true;
                isWin = true;
                //Notify players are winner
                EndGame();
                // Save information of players
                saveScores();
            }
        }
        //Display point and live for user after each time choose a pair of image
        lblPointValue.setText(point + "");
        //if player lose then display live = 0
        if (live <= -1) {
            lblLiveValue.setText("0");
        } else {
            //if not, display the remaining live
            lblLiveValue.setText(live + "");
        }

    }

    /**
     * Save scores of players
     */
    public void saveScores() {
        if (isWin == true || isLose == true || isTimeUp == true) {
            //set icon for dialog
            Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/img_test/1.png"));
            DLogSaveInformation.setIconImage(icon);
            //set title for dialog
            DLogSaveInformation.setTitle("Save Information Players");
            //display
            DLogSaveInformation.setVisible(true);
            DLogSaveInformation.setSize(536, 250);
            DLogSaveInformation.setLocationRelativeTo(null);
        }
    }

    /**
     * Display game
     */
    public void showGame() {
        //reset the game and display live & point
        reset();
        lblLiveValue.setText(live + "");
        lblPointValue.setText(point + "");

        //Set time after a second
        timer = new Thread() {
            public void run() {
                try {
                    //whenever time >= 0
                    while (time >= 0) {
                        //set time for JProgressBar
                        progrbTime.setValue(time);
                        //time - 1 for each second
                        sleep(1000);
                        time--;
                        // -5 point/5s
                        if (time % 5 == 0) {
                            point -= 5;
                            tmp = point;
                        }
                        //set point for each second
                        lblPointValue.setText(point + "");
                        //if time less than 0 or point equal 0 then player is loser
                        if (time <= 0 || point == 0) {
                            //set isTimeUp = true
                            isTimeUp = true;
                            //Notify players are winner
                            EndGame();
                            //Save information of player
                            saveScores();
                            break;
                        }
                    }
                    //stop count time
                    timer.stop();
                } catch (InterruptedException ex) {
                    Logger.getLogger(StartGame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        //start count time
        timer.start();
    }

    /**
     * format time
     *
     * @param time
     * @return
     */
    private String int2time(int time) {
        return String.format("%02d:%02d", time / 60, time % 60);
    }

    /**
     * find path to match 2 image
     *
     * @param from
     * @param to
     */
    public void findPath(Card from, Card to) {
        router.reset(cards, from, to);
        router.findPath();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        DLogGameOver = new javax.swing.JDialog();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnNewGame = new javax.swing.JButton();
        btnReturnMenu = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        DLogRunOutOfLive = new javax.swing.JDialog();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblTest = new javax.swing.JLabel();
        btnNewGameOutMove = new javax.swing.JButton();
        btnReturnMenuOutMove = new javax.swing.JButton();
        DLogSaveInformation = new javax.swing.JDialog();
        SaveInfo = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        btnOK = new javax.swing.JButton();
        pnlGame = new javax.swing.JPanel();
        btnReturn = new javax.swing.JButton();
        btnQuit = new javax.swing.JButton();
        progrbTime = new javax.swing.JProgressBar();
        pnlStatus = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lblLiveValue = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblPointValue = new javax.swing.JLabel();
        btnNGame = new javax.swing.JButton();
        lblBackgroundGame = new javax.swing.JLabel();

        DLogGameOver.setTitle("GameOver");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 30)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Time is up!");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 30)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Game Over");

        btnNewGame.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18)); // NOI18N
        btnNewGame.setText("Try Again");
        btnNewGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewGameActionPerformed(evt);
            }
        });

        btnReturnMenu.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18)); // NOI18N
        btnReturnMenu.setText("Return Menu");
        btnReturnMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReturnMenuActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tempus Sans ITC", 0, 24)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Do you want to try again?");

        javax.swing.GroupLayout DLogGameOverLayout = new javax.swing.GroupLayout(DLogGameOver.getContentPane());
        DLogGameOver.getContentPane().setLayout(DLogGameOverLayout);
        DLogGameOverLayout.setHorizontalGroup(
            DLogGameOverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DLogGameOverLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(DLogGameOverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DLogGameOverLayout.createSequentialGroup()
                        .addGroup(DLogGameOverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(DLogGameOverLayout.createSequentialGroup()
                                .addGap(106, 106, 106)
                                .addGroup(DLogGameOverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(DLogGameOverLayout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(59, Short.MAX_VALUE))
                    .addGroup(DLogGameOverLayout.createSequentialGroup()
                        .addComponent(btnNewGame, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnReturnMenu)
                        .addGap(40, 40, 40))))
        );
        DLogGameOverLayout.setVerticalGroup(
            DLogGameOverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DLogGameOverLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addGroup(DLogGameOverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReturnMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNewGame, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46))
        );

        DLogRunOutOfLive.setTitle("GameOver");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 30)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Wrong Move!");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 30)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Game Over");

        jLabel1.setFont(new java.awt.Font("Tempus Sans ITC", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Do you want to try again?");

        lblTest.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        btnNewGameOutMove.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18)); // NOI18N
        btnNewGameOutMove.setText("Try Again");
        btnNewGameOutMove.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnNewGameOutMove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewGameOutMoveActionPerformed(evt);
            }
        });

        btnReturnMenuOutMove.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18)); // NOI18N
        btnReturnMenuOutMove.setText("Return Menu");
        btnReturnMenuOutMove.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnReturnMenuOutMove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReturnMenuOutMoveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout DLogRunOutOfLiveLayout = new javax.swing.GroupLayout(DLogRunOutOfLive.getContentPane());
        DLogRunOutOfLive.getContentPane().setLayout(DLogRunOutOfLiveLayout);
        DLogRunOutOfLiveLayout.setHorizontalGroup(
            DLogRunOutOfLiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DLogRunOutOfLiveLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(btnNewGameOutMove, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnReturnMenuOutMove, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DLogRunOutOfLiveLayout.createSequentialGroup()
                .addContainerGap(63, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
            .addGroup(DLogRunOutOfLiveLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTest, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(DLogRunOutOfLiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DLogRunOutOfLiveLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(DLogRunOutOfLiveLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(87, Short.MAX_VALUE))
        );
        DLogRunOutOfLiveLayout.setVerticalGroup(
            DLogRunOutOfLiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DLogRunOutOfLiveLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(DLogRunOutOfLiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(DLogRunOutOfLiveLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblTest, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                .addGroup(DLogRunOutOfLiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReturnMenuOutMove, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNewGameOutMove, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38))
        );

        DLogSaveInformation.setResizable(false);

        SaveInfo.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        SaveInfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SaveInfo.setText("Enter Your Name");

        btnOK.setText("Save");
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout DLogSaveInformationLayout = new javax.swing.GroupLayout(DLogSaveInformation.getContentPane());
        DLogSaveInformation.getContentPane().setLayout(DLogSaveInformationLayout);
        DLogSaveInformationLayout.setHorizontalGroup(
            DLogSaveInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DLogSaveInformationLayout.createSequentialGroup()
                .addGroup(DLogSaveInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DLogSaveInformationLayout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(DLogSaveInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtName)
                            .addComponent(SaveInfo, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE)))
                    .addGroup(DLogSaveInformationLayout.createSequentialGroup()
                        .addGap(182, 182, 182)
                        .addComponent(btnOK, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        DLogSaveInformationLayout.setVerticalGroup(
            DLogSaveInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DLogSaveInformationLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(SaveInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(btnOK, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addGap(26, 26, 26))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pikachu Game");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlGame.setBackground(new java.awt.Color(255, 255, 255));
        pnlGame.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pnlGame.setOpaque(false);
        pnlGame.setPreferredSize(new java.awt.Dimension(720, 600));

        javax.swing.GroupLayout pnlGameLayout = new javax.swing.GroupLayout(pnlGame);
        pnlGame.setLayout(pnlGameLayout);
        pnlGameLayout.setHorizontalGroup(
            pnlGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 730, Short.MAX_VALUE)
        );
        pnlGameLayout.setVerticalGroup(
            pnlGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        getContentPane().add(pnlGame, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 730, 600));

        btnReturn.setFont(new java.awt.Font("Tahoma", 1, 32)); // NOI18N
        btnReturn.setText("Return Menu");
        btnReturn.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 0, 204), new java.awt.Color(0, 255, 0)));
        btnReturn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReturnActionPerformed(evt);
            }
        });
        getContentPane().add(btnReturn, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 300, 220, 60));

        btnQuit.setFont(new java.awt.Font("Tahoma", 1, 32)); // NOI18N
        btnQuit.setText("Quit");
        btnQuit.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 255), new java.awt.Color(0, 255, 204)));
        btnQuit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnQuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitActionPerformed(evt);
            }
        });
        getContentPane().add(btnQuit, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 400, 220, 60));

        progrbTime.setBackground(new java.awt.Color(51, 255, 204));
        progrbTime.setForeground(new java.awt.Color(0, 180, 124));
        progrbTime.setMaximum(300);
        progrbTime.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)));
        progrbTime.setOpaque(true);
        getContentPane().add(progrbTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, 700, 33));

        pnlStatus.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)));
        pnlStatus.setOpaque(false);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 0));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Live:");

        lblLiveValue.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblLiveValue.setForeground(new java.awt.Color(255, 255, 0));
        lblLiveValue.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 0));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Scores:");

        lblPointValue.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblPointValue.setForeground(new java.awt.Color(255, 255, 0));
        lblPointValue.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout pnlStatusLayout = new javax.swing.GroupLayout(pnlStatus);
        pnlStatus.setLayout(pnlStatusLayout);
        pnlStatusLayout.setHorizontalGroup(
            pnlStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStatusLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblLiveValue, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(187, 187, 187)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPointValue, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(67, Short.MAX_VALUE))
        );
        pnlStatusLayout.setVerticalGroup(
            pnlStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStatusLayout.createSequentialGroup()
                .addGroup(pnlStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPointValue, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(lblLiveValue, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(pnlStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 80, 600, 40));

        btnNGame.setFont(new java.awt.Font("Tahoma", 1, 32)); // NOI18N
        btnNGame.setText("New Game");
        btnNGame.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 0, 0), new java.awt.Color(102, 255, 255)));
        btnNGame.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNGameActionPerformed(evt);
            }
        });
        getContentPane().add(btnNGame, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 190, 220, 60));

        lblBackgroundGame.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblBackgroundGame.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Background Ice Age Start.jpg"))); // NOI18N
        getContentPane().add(lblBackgroundGame, new org.netbeans.lib.awtextra.AbsoluteConstraints(-5, -1, 1180, 750));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * this button is for users who want to return main game menu while playing game
     *
     * @param evt
     */
    private void btnReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturnActionPerformed
        this.setVisible(false);
        GamePikachu pikachu = new GamePikachu();
        pikachu.setVisible(true);
    }//GEN-LAST:event_btnReturnActionPerformed

    /**
     * this button is for users who want to exit the game while playing game
     *
     * @param evt
     */
    private void btnQuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnQuitActionPerformed

    /**
     * warning users have lose the game because time is up
     */
    public void gameOver() {
        //set icon for game
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/img_test/1.png"));
        DLogGameOver.setIconImage(icon);
        //set title for dialog
        DLogGameOver.setTitle("Pikachu Game");
        DLogGameOver.setVisible(true);
        DLogGameOver.setSize(482, 309);
        DLogGameOver.setLocationRelativeTo(null);
    }

    /**
     * Waring players have lose this game because wrong move
     */
    public void gameOverOutMove() {
        //set icon for game
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/img_test/1.png"));
        DLogRunOutOfLive.setIconImage(icon);
        //set title for dialog
        DLogRunOutOfLive.setTitle("Pikachu Game");
        DLogRunOutOfLive.setVisible(true);
        DLogRunOutOfLive.setSize(482, 309);
        lblTest.setText(point + "");
        DLogRunOutOfLive.setLocationRelativeTo(null);
    }

    /**
     * Show dialog notify lose or win
     *
     * @throws InterruptedException
     */
    public void EndGame() throws InterruptedException {
        if (isLose || isTimeUp) {
            //show dialog notify that "You Lose"
            JOptionPane.showMessageDialog(this, "You Lose!", null,
                    JOptionPane.INFORMATION_MESSAGE);
        } else if (isWin) {
            //show dialog notify that "You Win"
            JOptionPane.showMessageDialog(this, "You Win!", null,
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * This button use to return the menu after end game.
     *
     * @param evt
     */
    private void btnReturnMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturnMenuActionPerformed
        //hide dialog and show main menu
        DLogGameOver.setVisible(false);
        this.setVisible(false);
        GamePikachu pikachu = new GamePikachu();
        pikachu.setVisible(true);
    }//GEN-LAST:event_btnReturnMenuActionPerformed

    /**
     * This button use to create a new game after end game.
     *
     * @param evt
     */
    private void btnNewGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewGameActionPerformed
        //hide dialog and reset the game
        DLogGameOver.setVisible(false);
        showGame();
    }//GEN-LAST:event_btnNewGameActionPerformed

    /**
     * This button use to create a new game after end game.
     *
     * @param evt
     */
    private void btnNewGameOutMoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewGameOutMoveActionPerformed
        //hide dialog and reset the game
        DLogRunOutOfLive.setVisible(false);
        showGame();
    }//GEN-LAST:event_btnNewGameOutMoveActionPerformed

    /**
     * This button use to return the menu after end game.
     *
     * @param evt
     */
    private void btnReturnMenuOutMoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturnMenuOutMoveActionPerformed
        //hide dialog and show main menu
        DLogRunOutOfLive.setVisible(false);
        this.setVisible(false);
        GamePikachu pikachu = new GamePikachu();
        pikachu.setVisible(true);
    }//GEN-LAST:event_btnReturnMenuOutMoveActionPerformed

    /**
     * Button to play new game while playing game
     *
     * and confirm that player is sure to play again
     *
     * @param evt
     */
    private void btnNGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNGameActionPerformed
        int choice = JOptionPane.showOptionDialog(this, "Are you sure?"
                + "\nYou will lose all the points that you are!", "Warning",
                JOptionPane.WARNING_MESSAGE, JOptionPane.OK_OPTION, null, new String[]{"Yes", "No"}, "Yes");

        //if player choose "Yes" then reset the game
        if (choice == 0) {
            showGame();
        }
    }//GEN-LAST:event_btnNGameActionPerformed
    /**
     * Save information and back main menu
     *
     * @param evt
     */
    private void btnOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOKActionPerformed
        try {
            //get name of user 
            String name = txtName.getText();
            //allocates memory for file
            //load file >>> add name of players into data file >>> save data
            rm = new RankManagement("src/data/rank.txt");
            rm.loadFile();
            rm.add(name, point);
            rm.saveFile();
            DLogSaveInformation.setVisible(false);
            
            //Base on final result(isWin, isLose and isTimeUp) to show dialog  
            if (isLose) {
                gameOverOutMove();
            } else if (isTimeUp) {
                gameOver();
            } else if (isWin) {
                int choice = JOptionPane.showOptionDialog(this, "Congratulation! You are win."
                        + "\nDo you want to play another game?", "Winner!",
                        JOptionPane.INFORMATION_MESSAGE, JOptionPane.OK_OPTION, null, new String[]{"Play agian!", "Return menu"}, "Yes");

                //if player choose "Play agian" then reset the game
                if (choice == 0) {
                    showGame();
                } else if (choice == 1) {
                    //if player choose "Return menu" then return main menu
                    GamePikachu pikachu = new GamePikachu();
                    pikachu.setVisible(true);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(StartGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnOKActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(StartGame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StartGame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StartGame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StartGame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StartGame().setVisible(true);
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog DLogGameOver;
    private javax.swing.JDialog DLogRunOutOfLive;
    private javax.swing.JDialog DLogSaveInformation;
    private javax.swing.JLabel SaveInfo;
    private javax.swing.JButton btnNGame;
    private javax.swing.JButton btnNewGame;
    private javax.swing.JButton btnNewGameOutMove;
    private javax.swing.JButton btnOK;
    private javax.swing.JButton btnQuit;
    private javax.swing.JButton btnReturn;
    private javax.swing.JButton btnReturnMenu;
    private javax.swing.JButton btnReturnMenuOutMove;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel lblBackgroundGame;
    private javax.swing.JLabel lblLiveValue;
    private javax.swing.JLabel lblPointValue;
    private javax.swing.JLabel lblTest;
    private javax.swing.JPanel pnlGame;
    private javax.swing.JPanel pnlStatus;
    private javax.swing.JProgressBar progrbTime;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
}
