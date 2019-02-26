/**
 * Author: Andrew Lewis
 * 
 * CPSC 352 Project 1: 8 Puzzle Search
 * 2019-02-25
 * README
 */

The Package ProjectPuzzle contains 3 data classes and one comparator class

The data classes are as follows: 

    State.java:
        - This class contains the State representation of the board using an ArrayList<Integer>
        It contains the main functions for calculating the appropriate heuristics for a state branch.
        In addtion it holds the GOALS

        -Initialized from main driver with ArrayList representation of board and parity

    SearchNode.java:
        -This class wraps the State object in a typical node class for traversal of path and storage of state data

        -Initialized within search functions

    Solver.java:
        -The main driver class hosting the search functions; A* and BFS.

        -Uses a random array shuffler to generate new board instances on an array numbered 0-8


Driver:

    To run the driver, first unpack the jar file and simply compile the Solver.java class using "javac Solver" within the extracted directory
    run with "java Solver" 


Observations:

    (1.5Ghz CPU) Windows 10 Pro

    On average, the BFS algorithm always performed worst. It explored the most nodes, about 40K+ on average*, and likewise had the most runtime with an average of 849ms.
    
    A* with Out of place heuristic perfromed best in terms of runtime, averaging <100ms. It sometimes explores more or less nodes than a Manhattan Distance heuristic, about  but far less
    than BFS.

    A* wih Manhattan Distance seems to elapse, on average, <200ms

    *average per 20 random runs