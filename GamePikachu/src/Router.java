
import java.awt.Point;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author SnowGroup
 */
public class Router {

    public static Point from;
    public static Point to;
    //check position 
    public static boolean[][] a = new boolean[StartGame.boardRows][StartGame.boardCols];
    public static ArrayList<Point> path = new ArrayList<Point>();
    public static boolean hasPath = false;
    StartGame parent;

    /**
     * find path to match between 2 image
     */
    public void findPath() {
        Point p;
        //clear path
        path.clear();
        //get coordinate x, y of image have been choose
        int from_Col = (int) from.getX();
        int from_Row = (int) from.getY();
        int to_Col = (int) to.getX();
        int to_Row = (int) to.getY();
        //get min, max of x, y 
        int min_Col = (int) Math.min(from.getX(), to.getX());
        int max_Col = (int) Math.max(from.getX(), to.getX());
        int min_Row = (int) Math.min(from.getY(), to.getY());
        int max_Row = (int) Math.max(from.getY(), to.getY());

        //var check horizontal & vertical line 
        boolean check_1Line_Horizontal = false;
        boolean check_1Line_Vertical = false;
        boolean check_2Line_Horizontal = false;
        boolean check_2Line_Vertical = false;
        boolean check_3Line_left = false;
        boolean check_3Line_right = false;
        boolean check_3Line_up = false;
        boolean check_3Line_down = false;
        boolean check_v, check_v1, check_v2;
        boolean check_h, check_h1, check_h2;

        //check horizontal line if 2 image in same horizontal line
        if (from_Row == to_Row) {
            check_1Line_Horizontal = checkHorizontalLine(min_Col, max_Col, from_Row);
        }
        if (check_1Line_Horizontal) {
            getHorizontalLine(min_Col, max_Col, from_Row);
            System.out.println("check 1 line horizontal");
        } else {
            //check vertical
            if (from_Col == to_Col) {
                check_1Line_Vertical = checkVerticalLine(min_Row, max_Row, from_Col);
            }
            if (check_1Line_Vertical) {
                getVerticalLine(min_Row, max_Row, from_Col);
                System.out.println("check 1 line vertical");
            } else {
                //check 2 line horizontal
                for (int col = from_Col; col <= to_Col; col++) {
                    check_v = checkVerticalLine(min_Row, max_Row, col);
                    check_h1 = checkHorizontalLine(from_Col, col, from_Row);
                    check_h2 = checkHorizontalLine(col, to_Col, to_Row);
                    check_2Line_Horizontal = check_v && check_h1 && check_h2;
                    if (check_2Line_Horizontal) {
                        getHorizontalLine(from_Col, col, from_Row);
                        getVerticalLine(min_Row, max_Row, col);
                        getHorizontalLine(col, to_Col, to_Row);
                        System.out.println("check 2 line horizontal");
                        break;
                    }
                }
                if (!check_2Line_Horizontal) {
                    //check 2 line vertical
                    for (int row = min_Row; row < max_Row; row++) {
                        check_h = checkHorizontalLine(from_Col, to_Col, row);
                        check_v1 = checkVerticalLine(Math.min(from_Row, row), Math.max(from_Row, row), from_Col);
                        check_v2 = checkVerticalLine(Math.min(to_Row, row), Math.max(to_Row, row), to_Col);
                        check_2Line_Vertical = check_h && check_v1 && check_v2;
                        if (check_2Line_Vertical) {
                            getVerticalLine(Math.min(from_Row, row), Math.max(from_Row, row), from_Col);
                            getHorizontalLine(from_Col, to_Col, row);
                            getVerticalLine(Math.min(to_Row, row), Math.max(to_Row, row), to_Col);
                            System.out.println("2 line vertical");
                            break;
                        }
                    }
                    if (!check_2Line_Vertical) {
                        //check 3 line left
                        for (int col = from_Col - 1; col >= 0; col--) {
                            check_v = checkVerticalLine(min_Row, max_Row, col);
                            check_h1 = checkHorizontalLine(col, from_Col, from_Row);
                            check_h2 = checkHorizontalLine(col, to_Col, to_Row);
                            check_3Line_left = check_v && check_h1 && check_h2;
                            if (check_v && !check_3Line_left) {
                                break;
                            }
                            if (check_3Line_left) {
                                getHorizontalLine(col, from_Col, from_Row);
                                getVerticalLine(min_Row, max_Row, col);
                                getHorizontalLine(col, to_Col, to_Row);
                                System.out.println("3 line left");
                                break;
                            }
                        }
                        if (!check_3Line_left) {
                            //check 3 line right
                            for (int col = to_Col + 1; col < StartGame.boardCols; col++) {
                                check_v = checkVerticalLine(min_Row, max_Row, col);
                                check_h1 = checkHorizontalLine(from_Col, col, from_Row);
                                check_h2 = checkHorizontalLine(to_Col, col, to_Row);
                                check_3Line_right = check_v && check_h1 && check_h2;
                                if (check_v && !check_3Line_right) {
                                    break;
                                }
                                if (check_3Line_right) {
                                    getHorizontalLine(from_Col, col, from_Row);
                                    getVerticalLine(min_Row, max_Row, col);
                                    getHorizontalLine(to_Col, col, to_Row);
                                    System.out.println("3 line right");
                                    break;
                                }
                            }
                            if (!check_3Line_right) {
                                //check 3 line up
                                for (int row = min_Row - 1; row >= 0; row--) {
                                    check_h = checkHorizontalLine(from_Col, to_Col, row);
                                    check_v1 = checkVerticalLine(row, from_Row, from_Col);
                                    check_v2 = checkVerticalLine(row, to_Row, to_Col);
                                    check_3Line_up = check_h && check_v1 && check_v2;
                                    if (check_h && !check_3Line_up) {
                                        break;
                                    }
                                    if (check_3Line_up) {
                                        getVerticalLine(row, from_Row, from_Col);
                                        getHorizontalLine(from_Col, to_Col, row);
                                        getVerticalLine(row, to_Row, to_Col);
                                        System.out.println("3 line up");
                                        break;
                                    }
                                }
                                if (!check_3Line_up) {
                                    //check 3 line down
                                    for (int row = max_Row + 1; row < StartGame.boardRows; row++) {
                                        check_h = checkHorizontalLine(from_Col, to_Col, row);
                                        check_v1 = checkVerticalLine(from_Row, row, from_Col);
                                        check_v2 = checkVerticalLine(to_Row, row, to_Col);
                                        check_3Line_down = check_h && check_v1 && check_v2;
                                        if (check_h && !check_3Line_down) {
                                            break;
                                        }
                                        if (check_3Line_down) {
                                            getVerticalLine(from_Row, row, from_Col);
                                            getHorizontalLine(from_Col, to_Col, row);
                                            getVerticalLine(to_Row, row, to_Col);
                                            System.out.println("3 line down");
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        //if 1 of check line is true then has path
        hasPath = check_1Line_Horizontal || check_1Line_Vertical
                || check_2Line_Horizontal || check_2Line_Vertical
                || check_3Line_left || check_3Line_right
                || check_3Line_up || check_3Line_down;
    }

    /**
     * check 2 image in same row
     * @param col1
     * @param col2
     * @param row
     * @return 
     */
    private boolean checkHorizontalLine(int col1, int col2, int row) {
        //check have another image between 2 image 
        for (int i = col1; i <= col2; i++) {
            //check horizontal from image 1 to image 2
            if (!((i == (int) from.getX() && row == (int) from.getY())
                    || (i == (int) to.getX() && row == (int) to.getY()))) {
                //if have any image on path then return false
                if (!a[row][i]) {
                    return false;
                }
            }
        }
        //if not return true
        return true;
    }

    /**
     * get path and add that path into ArrayList
     * @param col1
     * @param col2
     * @param row 
     */
    private void getHorizontalLine(int col1, int col2, int row) {
        for (int i = col1; i <= col2; i++) {
            //Get path that have not image (barrier) on that path and add to path
            if (!((i == (int)from.getX() && row == (int)from.getY()) || 
                    (i == (int)to.getX() && row == (int)to.getY()))) {
                path.add(new Point(i, row));
            }
        }
    }

    /**
     * check 2 image on the column
     * @param row1
     * @param row2
     * @param col
     * @return 
     */
    private boolean checkVerticalLine(int row1, int row2, int col) {
         //check have another image between 2 image 
        for (int i = row1; i <= row2; i++) {
            //check vertical from image 1 to image 2
            if (!((i == (int) from.getY() && col == (int) from.getX())
                 || (i == (int) to.getY() && col == (int) to.getX()))) {
                //if have any image on path then return false
                if (!a[i][col]) {
                    return false;
                }
            }
        }
        //if not, return true
        return true;
    }

    /**
     * get path and add that path into ArrayList
     * @param row1
     * @param row2
     * @param col 
     */
    private void getVerticalLine(int row1, int row2, int col) {
        for (int i = row1; i <= row2; i++) {
            //Get path that have not image (barrier) on that path and add to path
            if (!((i == (int)from.getY() && col == (int)from.getX()) || 
                    (i == (int)to.getY() && col == (int)to.getX()))) {
                path.add(new Point(col, i));
            }
        }
    }
    
    /**
     * set value for 2 image and direction of path
     * @param cards
     * @param from
     * @param to 
     */
    public void reset(Card[][] cards, Card from, Card to) {
        //if 2 image can match then set value to 0
        for (int i = 0; i < StartGame.boardRows; i++) {
            for (int j = 0; j < StartGame.boardCols; j++) {
                a[i][j] = cards[i][j].getValue() == 0;
            }
        }
        
        //make sure the path from left to right
        if (from.getCol() < to.getCol()) {
            //if image 1 to the left of image 2 then unchanged
            this.from = new Point(from.getCol(), from.getRow());
            this.to = new Point(to.getCol(), to.getRow());
        } else {
            //if image 1 to the right of image 2 then swap "Point" of "from" and "to"
            this.from = new Point(to.getCol(), to.getRow());
            this.to = new Point(from.getCol(), from.getRow());
        }
    }
    
    /**
     * Initialization router
     * @param parent 
     */
    public Router(StartGame parent) {
        this.parent = parent;
    }
}
