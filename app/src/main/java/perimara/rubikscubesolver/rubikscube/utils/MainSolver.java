package perimara.rubikscubesolver.rubikscube.utils;

import android.content.Context;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;
import java.util.Stack;

import perimara.rubikscubesolver.R;
import perimara.rubikscubesolver.rubikscube.Cube;
import perimara.rubikscubesolver.rubikscube.State;

/**
 * Created by periklismaravelias on 24/12/16.
 */
public class MainSolver {

    public static String Solve(Cube cube) {
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
            if (!current.actionFromParent.equals("Bottom")) {
                frontier.add(new State(cube.RotateBottomClockwise()).setParent(current, "Bottom"));
                cube.RotateBottomCounterClockwise();
            }
            if (!current.actionFromParent.equals("Bottom Inverted")) {
                frontier.add(new State(cube.RotateBottomCounterClockwise()).setParent(current, "Bottom Inverted"));
                cube.RotateBottomClockwise();
            }
            if (!current.actionFromParent.equals("Up")) {
                frontier.add(new State(cube.RotateUpClockwise()).setParent(current, "Up"));
                cube.RotateUpCounterClockwise();
            }
            if (!current.actionFromParent.equals("Up Inverted")) {
                frontier.add(new State(cube.RotateUpCounterClockwise()).setParent(current, "Up Inverted"));
                cube.RotateUpClockwise();
            }
            if (!current.actionFromParent.equals("Front")) {
                frontier.add(new State(cube.RotateFrontClockwise()).setParent(current, "Front"));
                cube.RotateFrontCounterClockwise();
            }
            if (!current.actionFromParent.equals("Front Inverted")) {
                frontier.add(new State(cube.RotateFrontCounterClockwise()).setParent(current, "Front Inverted"));
                cube.RotateFrontClockwise();
            }
            if (!current.actionFromParent.equals("Back")) {
                frontier.add(new State(cube.RotateBackClockwise()).setParent(current, "Back"));
                cube.RotateBackCounterClockwise();
            }
            if (!current.actionFromParent.equals("Back Inverted")) {
                frontier.add(new State(cube.RotateBackCounterClockwise()).setParent(current, "Back Inverted"));
                cube.RotateBackClockwise();
            }
            if (!current.actionFromParent.equals("Left")) {
                frontier.add(new State(cube.RotateLeftClockwise()).setParent(current, "Left"));
                cube.RotateLeftCounterClockwise();
            }
            if (!current.actionFromParent.equals("Left Inverted")) {
                frontier.add(new State(cube.RotateLeftCounterClockwise()).setParent(current, "Left Inverted"));
                cube.RotateLeftClockwise();
            }
            if (!current.actionFromParent.equals("Right")) {
                frontier.add(new State(cube.RotateRightClockwise()).setParent(current, "Right"));
                cube.RotateRightCounterClockwise();
            }
            if (!current.actionFromParent.equals("Right Inverted")) {
                frontier.add(new State(cube.RotateRightCounterClockwise()).setParent(current, "Right Inverted"));
                cube.RotateRightClockwise();
            }
            //8) Add current state in the closed set
            closed_set.add(current);
        }

        //if current state has been initialized and it is the same as the final state...
        if (current != null && current.CheckFinalState()){
            //create a stack for backtracking from final state to root
            Stack<String> solution = new Stack();
            //while the parent of the current state is not null...
            while (current.parent != null){
                solution.push(current.counterActionFromParent);
                current = current.parent;
            }
            //initialize an array for the returned solution
            String[] result_arr = new String[solution.size()];
            solution.toArray(result_arr);
            String result = "";
            for (String s : result_arr){
                if (s.equals("V")){
                    continue;
                }
                result += s + ", ";
            }
            return result.substring(0, result.length() - 2);
        } else {
            return "No solution could be found";
        }
    }

    public static void TestCases(Context context, Cube cube){
        System.out.println(new State(cube));
        System.out.println(context.getResources().getString(R.string.back) + new State(cube.RotateBackClockwise()));
        System.out.println(context.getResources().getString(R.string.back_inv) + new State(cube.RotateBackCounterClockwise()));
        System.out.println(context.getResources().getString(R.string.front) + new State(cube.RotateFrontClockwise()));
        System.out.println(context.getResources().getString(R.string.front_inv) + new State(cube.RotateFrontCounterClockwise()));
        System.out.println(context.getResources().getString(R.string.up) + new State(cube.RotateUpClockwise()));
        System.out.println(context.getResources().getString(R.string.up_inv) + new State(cube.RotateUpCounterClockwise()));
        System.out.println(context.getResources().getString(R.string.bottom) + new State(cube.RotateBottomClockwise()));
        System.out.println(context.getResources().getString(R.string.bottom_inv) + new State(cube.RotateBottomCounterClockwise()));
        System.out.println(context.getResources().getString(R.string.left) + new State(cube.RotateLeftClockwise()));
        System.out.println(context.getResources().getString(R.string.left_inv) + new State(cube.RotateLeftCounterClockwise()));
        System.out.println(context.getResources().getString(R.string.right) + new State(cube.RotateRightClockwise()));
        System.out.println(context.getResources().getString(R.string.right_inv) + new State(cube.RotateRightCounterClockwise()));
    }

    public static void TestAlgorithm(Cube cube, String algorithm){
        System.out.println(new State(cube.Algorithm(algorithm)));
    }

    public static String[] SolveIDAStar(Cube cube){
        //TODO TO BE IMPLEMENTED
        /*
         node              current node
         g                 the cost to reach current node
         f                 estimated cost of the cheapest path (root..node..goal)
         h(node)           estimated cost of the cheapest path (node..goal)
         cost(node, succ)  step cost function
         is_goal(node)     goal test
         successors(node)  node expanding function, expand nodes ordered by g + h(node)

         procedure ida_star(root)
           bound := h(root)
           loop
             t := search(root, 0, bound)
             if t = FOUND then return bound
             if t = ∞ then return NOT_FOUND
             bound := t
           end loop
         end procedure

         function search(node, g, bound)
           f := g + h(node)
           if f > bound then return f
           if is_goal(node) then return FOUND
           min := ∞
           for succ in successors(node) do
             t := search(succ, g + cost(node, succ), bound)
             if t = FOUND then return FOUND
             if t < min then min := t
           end for
           return min
         end function
         */

        return null;
    }

}
