/**
 * Author: Andrew Lewis
 * 
 * CPSC 352 Project 1: 8 Puzzle Search
 * 2019-02-25
 */
import java.util.Objects;

public class SearchNode implements Comparable<SearchNode>{

	public State curState;
	private SearchNode parent;
    public double cost; // cost to get to this state
	public double hCost; // heuristic cost
	public double fCost; // f(n) cost

	/**
	 * Constructor for the root SearchNode
	 * 
	 * @param s
	 *            the state passed in
	 */
	public SearchNode(State s, double h){
		curState = s;
		parent = null;
		cost = 0;
        hCost = 0;
		fCost = h;
	}

	/**
	 * Constructor for all states after root states
	 * 
	 * @param prev
	 *            the parent node
	 * @param s
	 *            the state
	 * @param c
	 *            the g(n) cost to get to this node, depth
	 * @param h
	 *            the h(n) cost to get to the goal state
	 */
	public SearchNode(SearchNode prev, State s, double c, double h){
		parent = prev;
		curState = s;
        cost = c;
		hCost = h;
		fCost = cost + hCost;
	}

	/**
	 * @return the curState
	 */
	public State getCurState(){
		return curState;
	}

	/**
	 * @return the parent
	 */
	public SearchNode getParent(){
		return parent;
	}

	/**
	 * @return the cost
	 */
	public double getCost(){
		return cost;
	}

	/**
	 * 
	 * @return the heuristic cost
	 */
	public double getHCost(){
		return hCost;
	}

	/**
	 * 
	 * @return the f(n) cost for A*
	 */
	public double getFCost(){
		return fCost;
    }

    @Override
    public boolean equals(Object o){
        if (this == o){
			return true;
		} 
		if (o == null || getClass() != o.getClass()){
			return false;
		}
        SearchNode searchNode = (SearchNode) o;
        return Double.compare(searchNode.getFCost(), fCost) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fCost, curState);
    }

    @Override
    public String toString() {
        return ""+fCost;
    }

    @Override
    public int compareTo(SearchNode searchNode) {
        if(this.getFCost() > searchNode.getFCost()) {
            //System.out.println("comparing: board "+this.getFCost()+ " "+this.curState.boardState.toString()+"and "+ searchNode.getFCost()+" "+searchNode.curState.boardState.toString());
            return 1;
        } else if (this.getFCost() < searchNode.getFCost()) {
            return -1;
        } else {
            return 0;
        }
    }
}