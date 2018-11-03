import java.util.ArrayList;
import java.util.LinkedList;

public class SearchAlgorithms {

    int[][] case1 = {{1,2,7,3}, {5,6,11,4}, {9,10,15,8}, {13,14,12,0}};
    int[][] case2 = {{5,1,7,3}, {9,2,11,4}, {13,6,15,8}, {0,10,14,12}};

    SolutionInfo depthFirstSolution = new SolutionInfo(new PuzzleState(case1));
    SolutionInfo aStarSolution = new SolutionInfo(new PuzzleState(case1));
    SolutionInfo iterativeDeepening = new SolutionInfo(new PuzzleState(case2));


    public SolutionInfo aStar() {
        ArrayList<PuzzleState> unexpanded = new ArrayList<>();
        aStarSolution.solutionType = "A*: ";
        unexpanded.add(aStarSolution.start);
        aStarSolution.totalTime = System.currentTimeMillis();

        PuzzleState current;

        while(aStarSolution.expandedNodes <= 1000000){
            int minManSum = unexpanded.get(0).manSum;
            current = unexpanded.get(0);
            for(int i = 1; i < unexpanded.size(); i++){
                if(unexpanded.get(i).manSum <= minManSum){
                    minManSum = unexpanded.get(i).manSum;
                    current = unexpanded.get(i);
                }
            }
            current.printBoard();
            unexpanded.remove(current);

            if(current.manSum == 0){
                aStarSolution.totalTime = System.currentTimeMillis() - aStarSolution.totalTime;
                aStarSolution.solution = current;
                return aStarSolution;
            }

            else {
                current.initializeChildren();
                aStarSolution.expandedNodes++;

                if(current.up != null) {
                    unexpanded.add(current.up);
                }
                if(current.down != null) {
                    unexpanded.add(current.down);
                }
                if(current.left != null) {
                    unexpanded.add(current.left);
                }
                if(current.right != null) {
                    unexpanded.add(current.right);
                }
            }
        }

        aStarSolution.solution = null;
        aStarSolution.totalTime = System.currentTimeMillis() - aStarSolution.totalTime;

        return aStarSolution;
    }

    public SolutionInfo iterativeDeepeningSearch() {
        int depth = 0;
        int expandedNodes = 0;
        iterativeDeepening.solutionType = "Iterative Deepening: ";
        while (depth < Integer.MAX_VALUE && expandedNodes < 1000000) {

            LinkedList<PuzzleState> stack = new LinkedList<>();
            stack.add(iterativeDeepening.start);

            iterativeDeepening.totalTime = System.currentTimeMillis();

            while (!stack.isEmpty() && expandedNodes < 1000000) {
                PuzzleState curr = stack.removeLast();
                curr.printBoard();
                if (curr.testGoal()) {
                    iterativeDeepening.expandedNodes = expandedNodes;
                    iterativeDeepening.solution = curr;
                    iterativeDeepening.totalTime = System.currentTimeMillis() - iterativeDeepening.totalTime;
                    return iterativeDeepening;
                } else if (curr.level < depth){
                    expandedNodes++;
                    curr.initializeChildren();

                    if (curr.left != null) {
                        stack.add(curr.left);
                    }
                    if (curr.right != null) {
                        stack.add(curr.right);
                    }
                    if (curr.down != null) {
                        stack.add(curr.down);
                    }
                    if (curr.up != null) {
                        stack.add(curr.up);
                    }
                }
            }
            depth++;
        }

        iterativeDeepening.solution = null;
        iterativeDeepening.totalTime = System.currentTimeMillis() - iterativeDeepening.totalTime;
        iterativeDeepening.expandedNodes = expandedNodes;

        return iterativeDeepening;
    }


    public SolutionInfo depthFirstSearch() { //tree
        LinkedList<PuzzleState> explored = new LinkedList<>();
        depthFirstSolution.totalTime = System.currentTimeMillis();

        depthFirstSolution.solutionType = "Depth First: ";
        PuzzleState curr = depthFirstSolution.start;

        while(depthFirstSolution.expandedNodes < 1000000){

            curr.printBoard();
            //System.out.println(depthFirstSolution.expandedNodes);
            explored.add(curr);
            if (curr.testGoal()) {
                depthFirstSolution.solution = curr;
                depthFirstSolution.totalTime = System.currentTimeMillis() - depthFirstSolution.totalTime;
                return depthFirstSolution;
            } else {
                curr.initializeChildren();
                depthFirstSolution.expandedNodes++;

                if (curr.down != null){
                    if(curr.down.checkExplored(explored)){
                        curr.down = null;
                    } else {
                        curr = curr.down;
                        continue;
                    }
                }

                if (curr.up != null){
                    if(curr.up.checkExplored(explored)){
                        curr.up = null;
                    } else {
                        curr = curr.up;
                        continue;
                    }
                }

                if (curr.left != null){
                    if(curr.left.checkExplored(explored)){
                        curr.left = null;
                    } else {
                        curr = curr.left;
                        continue;
                    }
                }

                if (curr.right != null){
                    if(curr.right.checkExplored(explored)){
                        curr.right = null;
                    } else {
                        curr = curr.right;
                        continue;
                    }
                }

                curr.expanded = true;
                System.out.println("!!!!!!");
                while(curr.expanded == true){
                    curr = curr.parent;
                }
            }
        }
        depthFirstSolution.solution = null;
        depthFirstSolution.totalTime = System.currentTimeMillis() - depthFirstSolution.totalTime;

        return depthFirstSolution;
    }
}
