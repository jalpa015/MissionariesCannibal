package com.aiAssignment;

import java.util.ArrayList;
import java.util.Arrays;

public class MissionaryCannibalNode extends DFSAndIDS {

    /**
     * The following are the only instance variables you should need.(some others might be inherited from
     * UUSearchProblem, but worry about that later.)
     * <p>
     * TODO what is goalm here? Write comments here
     */
    private int goalm;
    /**
     * TODO what is goalc here? Write comments here
     */
    private int goalc;
    /**
     * TODO what is goalb here? Write comments here
     */
    private int goalb;
    /**
     * TODO what is totalMissionaries here? Write comments here
     */
    private int totalMissionaries;
    /**
     * TODO what is totalCannibals here? Write comments here
     */
    private int totalCannibals;

    public MissionaryCannibalNode(int sm, int sc, int sb, int gm, int gc, int gb) {
        startNode = new CannibalNode(sm, sc, sb, 0);
        goalm = gm;
        goalc = gc;
        goalb = gb;
        totalMissionaries = sm;
        totalCannibals = sc;
    }

    /**
     * Node class used by searches. Searches themselves are implemented in UUSearchProblem.
     */
    private class CannibalNode implements Node {
        /**
         * Do not change BOAT_SIZE without considering how it affect getSuccessors.
         */
        private final static int BOAT_SIZE = 2;

        /**
         * How many missionaries, cannibals, and boats are on the starting shore
         */
        private int[] state;

        /**
         * How far the current node is from the start. Not strictly required for search, but useful information for
         * debugging, and for comparing paths
         */
        private int depth;

        public CannibalNode(int m, int c, int b, int d) {
            state = new int[3];
            this.state[0] = m;
            this.state[1] = c;
            this.state[2] = b;
            depth = d;
        }

        /**
         * Add actions (denoted by how many missionaries and cannibals to put in the boat) to current state.
         */
        public ArrayList<DFSAndIDS.Node> getSuccessors() {
            ArrayList<Node> list = new ArrayList<Node>();
            int m = this.state[0];
            int c = this.state[1];
            int b;

            // Get the bank
            if (this.state[2] == 0)
                b = 1;
            else
                b = 0;

            // Current state is 1, so you are bringing across
            if (b == 0) {

                if (isSafeState(m, c - 3))
                    list.add(new CannibalNode(m, c - 3, b, depth + 1));

                if (isSafeState(m - 3, c))
                    list.add(new CannibalNode(m - 3, c, b, depth + 1));

                if (isSafeState(m - 1, c - 2))
                    list.add(new CannibalNode(m - 1, c - 2, b, depth + 1));

                if (isSafeState(m - 2, c - 1))
                    list.add(new CannibalNode(m - 2, c - 1, b, depth + 1));

                if (isSafeState(m, c - 2))
                    list.add(new CannibalNode(m, c - 2, b, depth + 1));

                if (isSafeState(m, c - 1))
                    list.add(new CannibalNode(m, c - 1, b, depth + 1));

                if (isSafeState(m - 1, c))
                    list.add(new CannibalNode(m - 1, c, b, depth + 1));

                if (isSafeState(m - 2, c))
                    list.add(new CannibalNode(m - 2, c, b, depth + 1));

                if (isSafeState(m - 1, c - 1))
                    list.add(new CannibalNode(m - 1, c - 1, b, depth + 1));
            }

            // Current state is 0, so you are bringing back
            if (b == 1) {

                if (isSafeState(m, c + 3))
                    list.add(new CannibalNode(m, c + 3, b, depth + 1));

                if (isSafeState(m + 3, c))
                    list.add(new CannibalNode(m + 3, c, b, depth + 1));

                if (isSafeState(m + 1, c + 2))
                    list.add(new CannibalNode(m + 1, c + 2, b, depth + 1));

                if (isSafeState(m + 2, c + 1))
                    list.add(new CannibalNode(m + 2, c + 1, b, depth + 1));

                if (isSafeState(m + 1, c + 1))
                    list.add(new CannibalNode(m + 1, c + 1, b, depth + 1));

                if (isSafeState(m, c + 1))
                    list.add(new CannibalNode(m, c + 1, b, depth + 1));

                if (isSafeState(m + 1, c))
                    list.add(new CannibalNode(m + 1, c, b, depth + 1));

                if (isSafeState(m + 2, c))
                    list.add(new CannibalNode(m + 2, c, b, depth + 1));

                if (isSafeState(m, c + 2))
                    list.add(new CannibalNode(m, c + 2, b, depth + 1));
            }

            for (int i = 0; i <= BOAT_SIZE; i++) {
                for (int j = 0; j <= BOAT_SIZE; j++) {
                    if (isSafeState(i, j))
                        list.add(new CannibalNode(i, j, b, depth + 1));
                }
            }


            return list;
        }

        /**
         * TODO write comments here
         * @param a
         * @param b
         * @return
         */
        private boolean isSafeState(int a, int b) {
            if (a > totalMissionaries || b > totalCannibals) {
                return false;
            }

            if (a < 0 || b < 0) {
                return false;
            }

            if (a < b && a != 0) {
                return false;
            }

            return (totalMissionaries - a) >= (totalCannibals - b) || (totalMissionaries - a) == 0;
        }

        /**
         * TODO write comments here
         * @return
         */
        @Override
        public boolean goalTest() {
            return (this.state[0] == goalm && this.state[1] == goalc && this.state[2] == goalb);
        }

        /**
         * An equality test is required so that visited lists in searches can check for containment of states
         * @param other
         * @return
         */
        @Override
        public boolean equals(Object other) {
            return Arrays.equals(state, ((CannibalNode) other).state);
        }

        @Override
        public int hashCode() {
            return state[0] * 100 + state[1] * 10 + state[2];
        }

        @Override
        public String toString() {
            String output = "(" + this.state[0]
                    + "," + this.state[1] + ","
                    + this.state[2] + ")";
            return output;
        }

        @Override
        public int getDepth() {
            return depth;
        }

    }

}
