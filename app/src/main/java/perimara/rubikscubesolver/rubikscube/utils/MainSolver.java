package perimara.rubikscubesolver.rubikscube.utils;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;
import java.util.Stack;

import perimara.rubikscubesolver.rubikscube.Cube;
import perimara.rubikscubesolver.rubikscube.State;

/**
 * Created by periklismaravelias on 24/12/16.
 */
public class MainSolver {

    public static String[] Solve(Cube cube) {
        //queue for the frontier
        Queue<State> frontier = new ArrayDeque();
        //arraylist for the closed set
        ArrayList<State> closed_set = new ArrayList();
        //current state variable
        State current = null;
        //1) Push starting state in the frontier
        frontier.add(new State(cube).setParent(null, "Start"));
        //2) While the frontier is not empty...
        while (!frontier.isEmpty()){
            //3) Pop state from frontier and let it be the current state
            current = frontier.remove();
            //4) Check if current state is final
            if (current.CheckFinalState()){
                //if so, break out of the loop
                break;
            }
            //5) Check if the current state is in the closed set
            boolean exists = false;
            for (State s : closed_set) {
                if (current.CheckEqualStates(s)){
                    //if so, set a flag 'exists' to true and break
                    exists = true;
                }
            }
            //if flag 'exists' is true, continue the loop
            if (exists){
                continue;
            }
            //6-7) Get children states and push them in the frontier
            //after each children, the revert rotation is applied
            frontier.add(new State(cube.RotateBottomClockwise()).setParent(current, "Bottom"));
            cube.RotateBottomCounterClockwise();
            frontier.add(new State(cube.RotateBottomCounterClockwise()).setParent(current, "Bottom'"));
            cube.RotateBottomClockwise();
            frontier.add(new State(cube.RotateUpClockwise()).setParent(current, "Up"));
            cube.RotateUpCounterClockwise();
            frontier.add(new State(cube.RotateUpCounterClockwise()).setParent(current, "Up'"));
            cube.RotateUpClockwise();
            frontier.add(new State(cube.RotateFrontClockwise()).setParent(current, "Front"));
            cube.RotateFrontCounterClockwise();
            frontier.add(new State(cube.RotateFrontCounterClockwise()).setParent(current, "Front'"));
            cube.RotateFrontClockwise();
            frontier.add(new State(cube.RotateBackClockwise()).setParent(current, "Back"));
            cube.RotateBackCounterClockwise();
            frontier.add(new State(cube.RotateBackCounterClockwise()).setParent(current, "Back'"));
            cube.RotateBackClockwise();
            frontier.add(new State(cube.RotateLeftClockwise()).setParent(current, "Left"));
            cube.RotateLeftCounterClockwise();
            frontier.add(new State(cube.RotateLeftCounterClockwise()).setParent(current, "Left'"));
            cube.RotateLeftClockwise();
            frontier.add(new State(cube.RotateRightClockwise()).setParent(current, "Right"));
            cube.RotateRightCounterClockwise();
            frontier.add(new State(cube.RotateRightCounterClockwise()).setParent(current, "Right'"));
            cube.RotateRightClockwise();
            //8) Add current state in the closed set
            closed_set.add(current);
        }

        //if current state has been initialized...
        if (current != null){
            //create a stack for backtracking from final state to root
            Stack<String> solution = new Stack();
            //while the parent of the current state is not null...
            while (current.parent != null){
                solution.push(current.actionFromParent);
                current = current.parent;
            }
            //initialize an array for the returned solution
            String[] result = new String[solution.size()];
            return solution.toArray(result);
        } else {
            return new String[] {"No solution could be found"};
        }

    }

}
