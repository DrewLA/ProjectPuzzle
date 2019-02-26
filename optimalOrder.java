/**
 * Author: Andrew Lewis
 * 
 * CPSC 352 Project 1: 8 Puzzle Search
 * 2019-02-25
 */
import java.util.Comparator;
//PriorityQueue Adapter
public class optimalOrder implements Comparator<SearchNode> {
    @Override
    public int compare(SearchNode a, SearchNode b) {
        int x = (int)(a.getHCost() + a.cost);
        int y = (int)(b.getHCost() + b.cost);
        //System.out.println("comparing: board "+a.getHCost()+ " "+a.curState.boardState.toString()+"and "+ b.getHCost()+" "+b.curState.boardState.toString());
        if (x > y)   return 1;
        if (x < y)   return -1;
        else         return 0;
    }
}