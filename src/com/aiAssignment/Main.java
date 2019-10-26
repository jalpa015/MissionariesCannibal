package com.aiAssignment;

import java.util.ArrayList;
import java.util.List;

enum Position {RIGHT, LEFT}

public class Main {

    public static void main(String[] args) {
        final int MAXDEPTH = 5000;

        // set up the "standard" 331 problem:
        MissionaryCannibalNode mcProblem = new MissionaryCannibalNode(3,3, 1, 0, 0, 0);
        System.out.println("Breadth First Search:");
        long startTime = System.nanoTime();
        State initialState = new State (3, 3, Position.LEFT, 0, 0);
        BreadthFirstSearch search = new BreadthFirstSearch();
        State solution = search.exec(initialState);
        printSolution(solution);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime)/1000000 ;
        System.out.println("Elapsed time in Breadth First Search: "+duration+"ms");
        System.out.println();
        System.out.println();
        System.out.println("Depth First Search:");
        startTime = System.nanoTime();
        List<DFSAndIDS.Node> path = mcProblem.depthFirstPathCheckingSearch(MAXDEPTH);
        System.out.println("DFS path: "+ path);
        mcProblem.printStats();
        endTime = System.nanoTime();
        duration = (endTime - startTime)/1000000 ;
        System.out.println("Elapsed time in DFS Path Checking Search: "+duration+"ms");
        System.out.println();
        System.out.println();

        System.out.println("Iterative Deepening Search:");
        startTime = System.nanoTime();
        path = mcProblem.IDSearch(MAXDEPTH);
        System.out.println("Iterative deepening DFS path: "+ path);
        mcProblem.printStats();
        endTime = System.nanoTime();
        duration = (endTime - startTime)/1000000 ;
        System.out.println("Elapsed time in Iterative deepening: "+duration+"ms");
    }
    private static void printSolution(State solution) {
        if (null == solution) {
            System.out.print("\nNo solution found.");
        } else {
            System.out.print("\nBFS path:");
            List<State> path = new ArrayList<State>();
            State state = solution;
            while(null!=state) {
                path.add(state);
                state = state.getParentState();
            }

            int depth = path.size() - 1;
            System.out.print(" [");
            for (int i = depth; i >= 0; i--) {
                state = path.get(i);
                int pos;
                if(state.getBoat() == Position.LEFT) {
                    pos = 1;
                } else {
                    pos = 0;
                }
                System.out.print("("+state.getCannibalLeft()+","+state.getMissionaryLeft()+","+pos+")");
            }
            System.out.print("]");
            System.out.println("\nDepth: " + depth);
        }
    }
}
