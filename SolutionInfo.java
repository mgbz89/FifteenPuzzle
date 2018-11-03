public class SolutionInfo {

    String solutionType;
    int expandedNodes;
    PuzzleState solution;
    long totalTime;
    String path = null;
    PuzzleState start;

    public SolutionInfo(PuzzleState start) {
        this.start = start;
        expandedNodes = 0;
    }

    public void printSolution() {
        System.out.println(solutionType);

        if(solution == null){
            System.out.println("Start: " );
            start.printBoard();
            System.out.println("Solution: None");
            System.out.println("Time: " + totalTime + " ms");
            System.out.println("Expanded Nodes: " + expandedNodes + "\n\n");
            return;
        }

        PuzzleState current = solution;
        StringBuilder build = new StringBuilder();

        while(current.parent != null){
            build.append(current.move);
            current = current.parent;
        }
        path = build.reverse().toString();
        path = path.substring(0,path.length() - 2);


        System.out.println("Start: " );
        start.printBoard();
        System.out.println("Solution: ");
        solution.printBoard();
        System.out.println("Path: " + path);
        System.out.println("Time: " + totalTime + " ms");
        System.out.println("Expanded Nodes: " + expandedNodes + "\n\n");
    }
}
