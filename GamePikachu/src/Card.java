
import com.sun.istack.internal.logging.Logger;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.Border;
import sun.util.logging.PlatformLogger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author SnowGroup
 *
 */
public class Card extends JLabel {

    //declare variables 
    public static final Color boderColor = new Color(169, 169, 169);    //set color is Dark Gray
    public static final int faceHighlight = 50;     //set value image for path between 2 image
    public static final int faceEmpty = 0;          //set value to hide image
    private final static int deplayTime = 500;      //value to sleep thread
    private static ArrayList<Card> cells;           //cell of image
    private MouseListener mouseClicked;             //event mouse cliked
    private boolean isSelect;                       //check image is selected or not
    private StartGame parent;                       //module class StartGame
    private int value;                              //Value of each image
    private int row;
    private int col;
    private Border normalBorder = BorderFactory.createLineBorder(boderColor);   //set color for normal border
    private Border hoverBorder = BorderFactory.createLineBorder(Color.RED);     //set color for hover border

    /**
     * Unit object Algorithm
     *
     * @param parent
     * @param row
     * @param col
     * @param value
     */
    public Card(StartGame parent, int row, int col, int value) {
        this.col = col;
        this.row = row;
        this.setValue(value);
        this.parent = parent;
        //if value of image greater than 0
        if (value > 0) {
            this.isSelect = false;
            //set cursor for label 
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            //create event mouse clicked
            this.mouseClicked = new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    cardClicked();
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    hover();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    out();
                }
            };
            this.addMouseListener(mouseClicked);
        }
    }

    /**
     * catch event player click to image
     */
    public void cardClicked() {
        //if player click less than 2 image then allow they click image
        if (cells.size() < 2) {
            this.select();
            //add position of image that player just clicked into arraylist
            cells.add(this);
        }

        //check 2 image
        new Thread() {
            public void run() {
                try {
                    Card from, to;
                    //check image player just clicked
                    if (cells.size() >= 2) {
                        from = cells.get(0);
                        to = cells.get(1);
                        //if  player click 2 image in same position then remove 1 click
                        if (from.getRow() == to.getRow() && from.getCol() == to.getCol()) {
                            cells.remove(cells.size() - 1);
                        } else {
                            //if 2 image is same then check it's path
                            if (from.getValue() == to.getValue()) {
                                parent.findPath(from, to);
                                //if has path to match between 2 image
                                if (Router.hasPath) {
                                    if (Router.path.size() > 0) {
                                        Point p;
                                        //display path match 2 image
                                        for (int i = 0; i < Router.path.size(); i++) {
                                            p = Router.path.get(i);
                                            //change image of cards on path
                                            parent.cards[(int) p.getY()][(int) p.getX()].setValue(faceHighlight);
                                        }
                                        Thread.sleep(deplayTime); //delay 0.5s
                                        //set disable for 2 image
                                        for (int i = 0; i < Router.path.size(); i++) {
                                            p = Router.path.get(i);
                                            //hide image
                                            parent.cards[(int) p.getY()][(int) p.getX()].setValue(faceEmpty);
                                            //hide border
                                            parent.cards[(int) p.getY()][(int) p.getX()].setBorder(null);
                                        }
                                    }
                                    //hide 2 image that player choose 
                                    from.hideFace();
                                    to.hideFace();
                                    //set result = true
                                    parent.setResult(true);
                                } else {
                                    Thread.sleep(deplayTime);   //delay 0.5s
                                    //unselected 2 image
                                    from.unselect();
                                    to.unselect();
                                    //set rusult = false
                                    parent.setResult(false);
                                }
                            } else {
                                //if 2 image not has path then unselected 2 image
                                Thread.sleep(deplayTime);
                                from.unselect();
                                to.unselect();
                                //set result = false
                                parent.setResult(false);
                            }
                            //remove 2 image out the Array 
                            if (!cells.isEmpty()) {
                                cells.remove(0);
                            }
                            if (!cells.isEmpty()) {
                                cells.remove(0);
                            }
                        }
                    }
                } catch (Exception ex) {
                    java.util.logging.Logger.getLogger(Card.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();  //start thread
    }

    //reset cell
    public static void reset() {
        cells = new ArrayList<Card>();
    }

    /**
     * selected image
     */
    public void select() {
        //set hover border when drag mouse into image
        this.setBorder(hoverBorder);
        //remove event mouseclicked of image when player clicked
        this.removeMouseListener(this.mouseClicked);
        //change cursor from "hand" to "default"
        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        //set select = true
        isSelect = true;
    }

    /**
     * unselected image
     */
    public void unselect() {
        this.setBorder(normalBorder); //set normal border when drag mouse into image
        this.addMouseListener(this.mouseClicked); //add event mouse clicked back
        //change cursor from "default" to "hand"
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        //set select = false
        isSelect = false;
    }

    /**
     * set border when player drag mouse into the image
     */
    public void hover() {
        this.setBorder(BorderFactory.createLineBorder(Color.RED));
    }

    /**
     * set border when player drag mouse out the image
     */
    public void out() {
        if (!isSelect) {
            this.setBorder(normalBorder);
        }
    }

    /**
     * hide the image
     */
    public void hideFace() {
        this.setValue(0);   //set value image to 0
        this.removeMouseListener(mouseClicked); //remove event mouseclicked
        //change cursor to default
        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
//        this.setBorder(normalBorder);
        this.setBorder(null);
    }

    /**
     * set value for image
     *
     * @param value
     */
    public void setValue(int value) {
        this.value = value;
        //set image for label
        this.setIcon(getFace());
        if (value == faceHighlight) {
            this.setBorder(hoverBorder);
        } else if (value != 0) {
            //if value image != 0 then set border to Dark Gray
            this.setBorder(normalBorder);
        }
    }

    /**
     * get image from file folder "img"
     *
     * @return
     */
    public ImageIcon getFace() {
        return new ImageIcon(getClass().getResource("/img/img_test2/" + value + ".png"));
    }

    /**
     * get column
     *
     * @return
     */
    public int getCol() {
        return col;
    }

    /**
     * get row
     *
     * @return
     */
    public int getRow() {
        return row;
    }

    /**
     * get value
     *
     * @return
     */
    public int getValue() {
        return value;
    }
}
