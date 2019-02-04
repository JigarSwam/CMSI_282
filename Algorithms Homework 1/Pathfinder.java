// Sam Gibson, Jigar Swaminarayan
package pathfinder.uninformed;

import java.util.*;
import java.util.Map.Entry;

/**
 * Maze Pathfinding algorithm that implements a basic, uninformed, breadth-first tree search.
 */
public class Pathfinder {

	/**
	 * Given a MazeProblem, which specifies the actions and transitions available in the
	 * search, returns a solution to the problem as a sequence of actions that leads from
	 * the initial to a goal state.
	 *
	 * @param problem A MazeProblem that specifies the maze, actions, transitions.
	 * @return An ArrayList of Strings representing actions that lead from the initial to
	 * the goal state, of the format: ["R", "R", "L", ...]
	 */
	public static ArrayList<String> solve (MazeProblem problem) {
		// TODO: Initialize frontier -- what data structure should you use here for
		// breadth-first search? Recall: The frontier holds SearchTreeNodes!
		Queue <SearchTreeNode> frontier = new LinkedList<>();

		// TODO: Add new SearchTreeNode representing the problem's initial state to the
		// frontier. Since this is the initial state, the node's action and parent will
		// be null
		SearchTreeNode initialState = new SearchTreeNode(problem.INITIAL_STATE, null, null);
		frontier.add(initialState);

		SearchTreeNode current = initialState;

		// TODO: Loop: as long as the frontier is not empty...
		while (frontier != null) {
			// TODO: Get the next node to expand by the ordering of breadth-first search
			Map<String, MazeState> transitions = problem.getTransitions(frontier.peek().state);

			for (Entry<String, MazeState> x : transitions.entrySet()) {
				MazeState xMod = x.getValue();
				SearchTreeNode xNode = new SearchTreeNode(x.getValue(), x.getKey(), current);
				if (problem.isGoal(xMod)) {
					return xNode.getPath();
					// return xMod and path to get there
					// call helper method which holds the strings of the actions, set current to parent
					// until you hit the root
				}
				frontier.add(xNode);
				// make a new treeNode and add it to the frontier and it consists of value, key, current
			}
			frontier.remove();
			current = frontier.peek();
			// pop the head of the LinkedList
			// set current = new head of LinkedList
		}


		// TODO: If that node's state is the goal (see problem's isGoal method),
		// you're done! Return the solution
		// [Hint] Use a helper method to collect the solution from the current node!

		// TODO: Otherwise, must generate children to keep searching. So, use the
		// problem's getTransitions method from the currently expanded node's state...

		// TODO: ...and *for each* of those transition states...
		// [Hint] Look up how to iterate through <key, value> pairs in a Map -- an
		// example of this is already done in the MazeProblem's getTransitions method

		// TODO: ...add a new SearchTreeNode to the frontier with the appropriate
		// action, state, and parent

		// Should never get here, but just return null to make the compiler happy
		return null;
	}

	// Helper that starts at goal and goes up the tree to root which holds moves

}

/**
 * SearchTreeNode that is used in the Search algorithm to construct the Search
 * tree.
 */
class SearchTreeNode {

	MazeState state;
	String action;
	SearchTreeNode parent;

	/**
	 * Constructs a new SearchTreeNode to be used in the Search Tree.
	 *
	 * @param state The MazeState (col, row) that this node represents.
	 * @param action The action that *led to* this state / node.
	 * @param parent Reference to parent SearchTreeNode in the Search Tree.
	 */
	SearchTreeNode (MazeState state, String action, SearchTreeNode parent) {
		this.state = state;
		this.action = action;
		this.parent = parent;
	}

	public ArrayList<String> getPath(){
		ArrayList<String> path = new ArrayList<String>();
		path.add(this.action);
		SearchTreeNode p;
		p = this.parent;
		while(p.parent != null){
			path.add(0, p.action);
			p = p.parent;
		}
		return path;
	}
}