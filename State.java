/**
 * Author: Andrew Lewis
 * 
 * CPSC 352 Project 1: 8 Puzzle Search
 * 2019-02-25
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public final class State{

	private int outOfPlace = 0;
	private int manDist = 0;
    //Possible goals
    private final int[] GOAL1 = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 0};
    private final int[] GOAL2 = new int[]{1, 2, 3, 8, 0, 4, 7, 6, 5};
    private final int[] GOAL;

    public ArrayList<Integer> boardState = new ArrayList<>();
    public int parity;

	/**
	 * Constructor for Search state
	 * 
	 * @param board :An ArrayList<Integer> board representation for the new state to be constructed
	 */
	public State(ArrayList<Integer> board, int parity){
        this.boardState = board;
        this.parity = parity;
        if(parity == 0){
            GOAL = GOAL1;
        }
        else{
            GOAL = GOAL2;
        }
        setOutOfPlace();
		setManDist();
	}

	/*
	 * Set the h1 out of place distance for the current state
	 */
	private void setOutOfPlace(){ 
        for (int i = 0; i < boardState.size(); i++){
            if (boardState.get(i) != GOAL[i]){
                outOfPlace++;                
            }
        }
	}

	/*
	 * Set the h2 Manhattan Distance for the current state
	 */
	private void setManDist(){
		int index = -1;
		for (int y = 0; y < 3; y++){
			for (int x = 0; x < 3; x++){
				index++;
				int val = (boardState.get(index) - 1);
				if (val != -1){
					int horiz = val % 3;
                    int vert = val / 3;
                    manDist += Math.abs(vert - (y)) + Math.abs(horiz - (x));
				}
			}
		}
	}
	/**
	 * Getter for the Manhattan Distance value
	 * 
	 * @return the Manhattan Distance h(n) value #h2
	 */
	public int getManDist(){
		return manDist;
	}
	/*
	 * locate the "0" spot as empty space on the current board
	 * 
	 * @return the index of the spot
	 */
	private int getHole(){
		int holeIndex = -1;
		for (int i = 0; i < 9; i++){
			if (boardState.get(i) == 0){
                holeIndex = i;
                break;
            }
        }
		return holeIndex;
	}

	/**
	 * Getter for the outOfPlace value
	 * 
	 * @return the outOfPlace h(n) value
	 */
	public int getOutOfPlace(){
		return outOfPlace;
	}
	/*
	 * Makes a copy of the array passed to it
	 */
	private ArrayList<Integer> copyBoard(ArrayList<Integer> state){
        ArrayList<Integer> copy = new ArrayList<>();
        Iterator iterator = state.iterator();
        while (iterator.hasNext()) {
            copy.add((Integer)iterator.next());
        }
		return copy;
	}

	/**
	 * Is thought about in terms of NO MORE THAN 4 operations. Can slide tiles
	 * from 4 directions if hole is in middle Two directions if hole is at a
	 * corner three directions if hole is in middle of a row
	 * 
	 * @return an ArrayList containing all of the successors for that state
	 */
	
	public ArrayList<State> nextStates(){
        final ArrayList<State> nextStates = new ArrayList<State>();
        nextStates.ensureCapacity(30);
        int hole = getHole();
        // Move hole left (Slide right)
		if (hole != 2 && hole != 5 && hole != 8){
			createState(hole + 1, hole, nextStates);
		}
		// Move hole right (Slide left)
		if (hole != 0 && hole != 3 && hole != 6){
			createState(hole - 1, hole, nextStates);
		}
		// Move hole down (Slide up)
		if (hole != 6 && hole != 7 && hole != 8){
			createState(hole + 3, hole, nextStates);
		}
		// Move hole up (Slide down)
		if (hole != 0 && hole != 1 && hole != 2){
			createState(hole - 3, hole, nextStates);
		}
		return nextStates;
	}
    /**
	 * Getter to return the current board array state
	 * 
	 * @return the current state: curState
	 */
	public ArrayList<Integer> getboardState(){
		return boardState;
	}

	/*
	 * Switches the tiles at hole and next selected valid action.
	 * creates a new state based on this new board and pushes into s.
	 */
	private void createState(int tileIndex, int holeIndex, ArrayList<State> s){
        //System.out.println("Swapping");
		ArrayList<Integer> cpy = copyBoard(boardState);
        int temp = cpy.get(tileIndex);
        //System.out.println("before swap: " +cpy.toString());
        cpy.add(tileIndex, 0);
        cpy.remove(tileIndex+1);        
        cpy.add(holeIndex, temp);
        cpy.remove(holeIndex+1);
        //System.out.println("after Swap: "+ cpy.toString());
        State state = new State(cpy, this.parity);
        //System.out.println("state made");
        s.add(state);
	}

	/**
	 * Check to see if the current state is the goal state.
	 * 
	 * @return true or false, depending on whether the current state matches
	 *         the goal
	 */
	
	public boolean isGoal(){
        ArrayList<Integer> tempArray = intToArray(this.GOAL);
        //System.out.println("Boardstate: "+boardState.toString());
		if (boardState.equals(tempArray)){
			return true;
		}
		return false;
	}
    /**
     * Convert int[] to ArrrayList<Integer> object
     * @param goal int[]
     * @return ArrayList<Integer>
     */
    public ArrayList<Integer> intToArray(int[] goal){
        ArrayList<Integer> list = new ArrayList<>();
        for(int i = 0; i < goal.length; i++){
            list.add(goal[i]);
        }
        return list;
	}
	/**
	 * Method to print out the goal state. Prints the puzzle board.
	 */
    public void printGoal(){
        ArrayList<Integer> goalState = intToArray(this.GOAL);
        System.out.println(goalState.get(0) + " | " + goalState.get(1) + " | "+ goalState.get(2));
		System.out.println("---------");
		System.out.println(goalState.get(3) + " | " + goalState.get(4) + " | "+ goalState.get(5));
		System.out.println("---------");
		System.out.println(goalState.get(6) + " | " + goalState.get(7) + " | "+ goalState.get(8));
    }
	/**
	 * Method to print out the current state. Prints the puzzle board.
	 */
	public void printState(){
		System.out.println(boardState.get(0) + " | " + boardState.get(1) + " | "+ boardState.get(2));
		System.out.println("---------");
		System.out.println(boardState.get(3) + " | " + boardState.get(4) + " | "+ boardState.get(5));
		System.out.println("---------");
		System.out.println(boardState.get(6) + " | " + boardState.get(7) + " | "+ boardState.get(8));
	}

    public boolean equals(State s){
		if (boardState.equals(s.getboardState())){
			return true;
		}
		else
			return false;
	}
}