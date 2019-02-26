/**
 * Author: Andrew Lewis
 * 
 * CPSC 352 Project 1: 8 Puzzle Search
 * 2019-02-25
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.io.File;
import java.util.PriorityQueue;
import java.util.Stack;
/**This class is the solver class and contains the search function for A* and BFS*/
public class Solver{

	/**
	 * Initialization function for 8puzzle A*Search
	 * 
	 * @param board
	 *            - The starting state, represented as a linear array of length
	 *            9 forming 3 meta-rows.
	 */
	public static void searchA(ArrayList<Integer> board, String heuristic, int parity){
        State rootState =  new State(board, parity);
        SearchNode root = new SearchNode(rootState, rootState.getOutOfPlace());
        Comparator<SearchNode> comparator = new optimalOrder();
        PriorityQueue<SearchNode> q = new PriorityQueue<SearchNode>(comparator);
        HashMap<ArrayList<Integer>, Double> statesExplored = new HashMap<>();
        ArrayList<State> frontier = new ArrayList<>();
        SearchNode tempNode = root;
        int searchCount = 0; // counter for number of iterations
        q.add(root);
        statesExplored.put((ArrayList<Integer>)root.getCurState().boardState, Double.MAX_VALUE); //Prevent reordering of root as leaf in Queue
        int nodes = 0;      //Number of expanded nodes
        int traversed = 0;   //Layer count of depth
        boolean goalFound = false;
        
		while (!q.isEmpty()) {
            tempNode = (SearchNode) q.remove();   
            frontier = tempNode.getCurState().nextStates();  // generate tempNode's immediate successors  
            searchCount++;          
            if (frontier.size() == 0) {
                continue;
            }
            for (int i = 0; i < frontier.size(); i++){
                nodes++;
                if (frontier.get(i).isGoal()) {
                    goalFound = true;
                    SearchNode openNode = new SearchNode(tempNode, frontier.get(i), tempNode.getCost() + 1, frontier.get(i).getOutOfPlace());
                    tempNode = openNode;
                    break;
                }
                ArrayList<Integer> edgeState = frontier.get(i).boardState;                    
                if (heuristic == "h1"){                                      
                    if (statesExplored.containsKey(edgeState)){
                        continue;
                    }
                    else{
                        //Create new state with h1 score
                        SearchNode openNode = new SearchNode(tempNode, frontier.get(i), tempNode.getCost() + 1, frontier.get(i).getOutOfPlace());
                        q.add(openNode);
                        statesExplored.put(edgeState, openNode.getFCost());
                    }
                }
                else if(heuristic == "h2"){
                    if (statesExplored.containsKey(edgeState)){
                        continue;
                    }
                    else{
                        //Create new state with h2 score
                        SearchNode openNode = new SearchNode(tempNode, frontier.get(i), tempNode.getCost() + 1, frontier.get(i).getManDist());
                        q.add(openNode);
                        statesExplored.put(edgeState, openNode.getFCost());
                    }
                }
            }
            if (goalFound) {
                break;
            }
        }
        if (goalFound){
            Stack<SearchNode> solutionPath = new Stack<SearchNode>();
            solutionPath.push(tempNode);
            //System.out.println("Printing path to goal");      
            System.out.println("");      
            while (tempNode.getParent() != null){
                solutionPath.push(tempNode.getParent());
                tempNode = tempNode.getParent();
            }
            System.out.println("First 3 states in solution: ");
            System.out.println("");
            for (int i = 0; i < 3; i++){
                tempNode = solutionPath.pop();
                tempNode.getCurState().printState();
                System.out.println();
                System.out.println();
            }
            for (int i = 0; i < solutionPath.size(); i++){
                solutionPath.pop();
            }
            System.out.println("The total moves required: " + tempNode.getCost());
            System.out.println("The number of nodes explored: " + searchCount);
            System.out.println("The state space size: "+nodes);
        }
        else{
            System.out.println("No solution");
            System.out.println("The number of possible states: " + nodes);
            System.out.println("The number of nodes expanded is: " + searchCount);
        }
       
    }
    /**
     * Helper function to get parity from input problem
     */
    public static int getParity(ArrayList<Integer> input){
        int parity = 0;
        for(int i = 0; i < 9; i++){
            for(int j = i+1; j < 9; j++){
                if (input.get(j) > input.get(i) && input.get(i) != 0) {
                    parity++;
                }
            }
        }
        return parity % 2;
    }
    /**
	 * Performs a BFSearch using a queue to hold the next states
     * @Parem board: ArrayList representation of state
     * @Parem parity: parity fir search goal
	 *       
	 */
    public static void searchBFS(ArrayList<Integer> board, int parity){
        int searchCount = 1; // counter for number of iterations
        int stateSpaceSize = 0;
        State rootState = new State(board, parity);
        SearchNode root = new SearchNode(rootState, rootState.getOutOfPlace());
		Queue<SearchNode> q = new LinkedList<SearchNode>();
        HashSet<ArrayList<Integer>> statesExplored = new HashSet<ArrayList<Integer>>();
        ArrayList<Integer> frontierState = new ArrayList<Integer>(root.getCurState().boardState);
        q.add(root); 
        statesExplored.add(frontierState);
        int nodes = 0;

		while (!q.isEmpty()){
			SearchNode tempNode = (SearchNode) q.poll();

            if (!tempNode.getCurState().isGoal()){
				ArrayList<State> frontier = tempNode.getCurState().nextStates(); // generate tempNode's immediate successors
				/*
				 * Loop through the successors and expand only new nodes
				 */
				for (int i = 0; i < frontier.size(); i++){
                    frontierState = frontier.get(i).boardState;
                    stateSpaceSize++;
                    if (statesExplored.contains(frontierState)){
                        continue;
                    }
					SearchNode newNode = new SearchNode(tempNode,frontier.get(i), tempNode.getCost()+ 1, 0);
				    q.add(newNode);
                    frontierState = (ArrayList<Integer>)(newNode.getCurState().boardState);
                    statesExplored.add(frontierState);
                    nodes++;
				}				
				searchCount++;
			}
			else{
    				Stack<SearchNode> solutionPath = new Stack<SearchNode>();
                    solutionPath.push(tempNode);
                    //System.out.println("Printing path to goal");      
                    System.out.println("");      
                    while (tempNode.getParent() != null){
                        solutionPath.push(tempNode.getParent());
                        tempNode = tempNode.getParent();
                    }
                    System.out.println("First 3 states in solution: ");
                    System.out.println("");
                    for (int i = 0; i < 3; i++){
                        tempNode = solutionPath.pop();
                        tempNode.getCurState().printState();
                        System.out.println();
                        System.out.println();
                    }
                    for (int i = 0; i < solutionPath.size(); i++){
                        solutionPath.pop();
                    }
                    System.out.println("The total moves required: " + tempNode.getCost());
                    System.out.println("The number of nodes explored: " + searchCount);
                    System.out.println("The state space size: "+stateSpaceSize);
			}
		}
    }

    public static ArrayList<Integer> randomInput(){
        ArrayList<Integer> input = new ArrayList<Integer>(Arrays.asList(0,1,2,3,4,5,6,7,8));
        Collections.shuffle(input);
        return input;
    }
    /**Main driver
     * 
     * Initiates a scanner object to read puzzle from test file in local directory
     * 
     * Scanner object takes in type of search value as first input String
     */
    public static void main(String[] args){
        long startTime;
        long endTime;
        long elapsedTime;
        //String algo = args[0];
        ArrayList<Integer> input = randomInput();
        int parity = getParity(input);
        State inputState = new State(input, parity);
        System.out.println("Solving puzzle: ----->");
        System.out.println(" ");
        inputState.printState();
        System.out.println("");
        System.out.println("Aiming for: ------>");
        System.out.println("");
        inputState.printGoal();
        System.out.println("");

        System.out.println("Running A* with h1: Out of place heuristic");

        startTime = System.currentTimeMillis();
        searchA(input, "h1", parity);
        endTime = System.currentTimeMillis();
        elapsedTime = endTime-startTime;

        System.out.println("Time: "+elapsedTime+"ms");
        System.out.println("##########################################");
        System.out.println("");
        System.out.println("Running A* with h2: Manhattan distance");

        startTime = System.currentTimeMillis();
        searchA(input, "h2", parity);
        endTime = System.currentTimeMillis();
        elapsedTime = endTime-startTime;

        System.out.println("Time: "+elapsedTime+"ms");
        System.out.println("##########################################");
        System.out.println("");
        System.out.println("Running BFS");

        startTime = System.currentTimeMillis();
        searchBFS(input, parity);
        endTime = System.currentTimeMillis();
        elapsedTime = endTime-startTime;
        System.out.println("Time: "+elapsedTime+"ms");
        System.out.println("##########################################");
        System.out.println("");
    }
}
