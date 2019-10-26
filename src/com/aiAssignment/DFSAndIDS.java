package com.aiAssignment;

import java.util.*;

class DFSAndIDS {

    private int nodesExplored;
    private int maxMemory;

    Node startNode;

    public interface Node {
        ArrayList<Node> getSuccessors();

        boolean goalTest();

        int getDepth();
    }

    private List<Node> dfsrm(Node currentNode,
                                     HashMap<Node, Integer> visited, int depth, int maxDepth) {

        LinkedList<Node> path = new LinkedList<Node>();
        visited.put(currentNode, depth);
        updateMemory(visited.size());
        incrementNodeCount();

        if (depth > maxDepth)
            return null;

        if (currentNode.goalTest()) {
            path.addFirst(currentNode);
            return path;
        }

        ArrayList<Node> nodeArrayList = currentNode.getSuccessors();
        for (Node i : nodeArrayList) {

            if (!visited.containsKey(i)
                    || (visited.containsKey(i) && depth < visited.get(i))) {
                path = (LinkedList<Node>) dfsrm(i, visited, depth + 1,
                        maxDepth);
                if (path != null) {
                    path.addFirst(currentNode);
                    return path;
                }
            }
        }
        return null;
    }

    // set up the iterative deepening search, and make use of dfspc
    public List<Node> IDSearch(int maxDepth) {
        resetStats();
        HashSet<Node> currentPath = new HashSet<>();
        HashMap<Node, Integer> currentPaths = new HashMap<>();
        List<Node> path;
        for (int i = 0; i <= maxDepth; i++) {
            path = dfsrm(startNode, currentPaths, 0, i);
            if (path != null) {
                return path;
            }
        }
        return null;
    }

    // set up the depth-first-search
    List<Node> depthFirstPathCheckingSearch(int maxDepth) {
        resetStats();
        HashSet<Node> currentPath = new HashSet<Node>();
        return dfsrpc(startNode, currentPath, 0, maxDepth);
    }
    // Depth First Recursive Search
    private List<Node> dfsrpc(Node currentNode,
                                      HashSet<Node> currentPath, int depth, int maxDepth) {

        currentPath.add(currentNode);
        LinkedList<Node> path = new LinkedList<Node>();
        incrementNodeCount();
        updateMemory(currentPath.size());

        if (currentNode.goalTest()) {
            path.addFirst(currentNode);
            return path;
        }
        ArrayList<Node> nodeArrayList = currentNode.getSuccessors();

        for (Node i : nodeArrayList) {

            if (!currentPath.contains(i) && depth < maxDepth) {

                path = (LinkedList<Node>) dfsrpc(i, currentPath,
                        depth + 1, maxDepth);
                if (path != null) {
                    path.addFirst(currentNode);
                    return path;
                }
                currentPath.remove(i);
            }

        }
        return null;
    }

    private void resetStats() {
        nodesExplored = 0;
        maxMemory = 0;
    }

    void printStats() {
        System.out.println("Nodes:  " + nodesExplored);
//        System.out.println("Maximum memory usage during last search "
//                + maxMemory);
    }

    private void updateMemory(int currentMemory) {
        maxMemory = Math.max(currentMemory, maxMemory);
    }

    private void incrementNodeCount() {
        nodesExplored++;
    }
}
