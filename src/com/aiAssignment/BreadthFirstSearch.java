package com.aiAssignment;

import java.util.*;

class BreadthFirstSearch {
    State exec(State initialState) {
        if (initialState.isGoal()) {
            return initialState;
        }
        Queue<State> frontier = new LinkedList<>();	// FIFO queue
        Set<State> explored = new HashSet<>();
        frontier.add(initialState);
        while (true) {
            if (frontier.isEmpty()) {
                return null;	// failure
            }
            State state = frontier.poll();
            explored.add(state);
            List<State> successors = state.generateSuccessors();
            for (State child : successors) {
                if (!explored.contains(child) || !frontier.contains(child)) {
                    if (child.isGoal()) {
                        return child;
                    }
                    frontier.add(child);
                }
            }
        }
    }
}
