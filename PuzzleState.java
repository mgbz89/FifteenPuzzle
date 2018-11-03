import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class PuzzleState {

    int[][] state;
    int openSpaceXValue;
    int openSpaceYValue;
    String move;
    PuzzleState up;
    PuzzleState down;
    PuzzleState right;
    PuzzleState left;
    PuzzleState parent;
    boolean expanded;
    int level;
    int manSum;
    int[][] solution = {{1,2,3,4}, {5,6,7,8}, {9,10,11,12}, {13,14,15,0}};


    public PuzzleState(int[][] state){
        this.state = state;
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                if(state[i][j] == 0){
                    openSpaceXValue = j;
                    openSpaceYValue = i;
                }
            }
        }

        this.move = null;
        this.up = null;
        this.down = null;
        this.right = null;
        this.left = null;
        this.parent = null;
        this.level = 0;
        this.expanded = false;
        setManSum();
    }

    public int[][] up(){
        int next[][] = copyBoard();
        next[openSpaceYValue][openSpaceXValue] = next[openSpaceYValue + 1][openSpaceXValue];
        next[openSpaceYValue + 1][openSpaceXValue] = 0;
        return next;
    }

    public int[][] down(){
        int next[][] = copyBoard();
        next[openSpaceYValue][openSpaceXValue] = next[openSpaceYValue - 1][openSpaceXValue];
        next[openSpaceYValue - 1][openSpaceXValue] = 0;
        return next;
    }

    public int[][] left(){
        int next[][] = copyBoard();
        next[openSpaceYValue][openSpaceXValue] = next[openSpaceYValue][openSpaceXValue + 1];
        next[openSpaceYValue][openSpaceXValue + 1] = 0;
        return next;
    }

    public int[][] right(){
        int next[][] = copyBoard();
        next[openSpaceYValue][openSpaceXValue] = next[openSpaceYValue][openSpaceXValue - 1];
        next[openSpaceYValue][openSpaceXValue - 1] = 0;
        return next;
    }

    public void setManSum() {
        this.manSum = 0;
        int value;
        int goalRow;
        int goalCol;

        for(int row = 0; row < 4; row++){
            for(int col = 0; col < 4; col++){
                if(state[row][col] == 0)
                    continue;
                value = state[row][col];
                goalRow = (value - 1) / 4;
                goalCol = (value - 1) % 4;

                manSum += Math.abs(row - goalRow) + Math.abs(col - goalCol);
            }
        }
    }

    public void initializeChildren() {
        if(openSpaceYValue != 3){
            this.up = new PuzzleState(this.up());
            this.up.move = " ,pU";
            this.up.level = this.level + 1;
            this.up.parent = this;
        }
        if(openSpaceYValue != 0){
            this.down = new PuzzleState(this.down());
            this.down.move = " ,nwoD";
            this.down.level = this.level + 1;
            this.down.parent = this;
        }
        if(openSpaceXValue != 3){
            this.left = new PuzzleState(this.left());
            this.left.move = " ,tfeL";
            this.left.level = this.level + 1;
            this.left.parent = this;
        }
        if(openSpaceXValue != 0){
            this.right = new PuzzleState(this.right());
            this.right.move = " ,thgiR";
            this.right.level = this.level + 1;
            this.right.parent = this;
        }
    }

    public PuzzleState getBestManSum() {
        PuzzleState best = up;
        if(best.manSum > down.manSum)
            best = down;
        if(best.manSum > left.manSum)
            best = left;
        if(best.manSum > right.manSum)
            best = right;

        return best;
    }

    public boolean testGoal() {
        return Arrays.deepEquals(solution, state);
    }

    public void printBoard(){
        for(int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                System.out.print(state[row][col] + ", ");
            }
            System.out.println("");
        }
        System.out.println("-----------------");
    }

    public int[][] copyBoard() {
        int[][] copy = new int[4][4];
        for(int i = 0; i < 4; i++){
            copy[i] = state[i].clone();
        }
        return copy;
    }

    public boolean checkExplored(LinkedList <PuzzleState> explored){

        for (PuzzleState visited : explored) {
            if(Arrays.deepEquals(this.state,visited.state)){
                return true;
            }
        }

        return false;
    }
}
