package perimara.rubikscubesolver.rubikscube;

/**
 * Created by periklismaravelias on 25/12/16.
 */
public class State {

    public int[] state;
    public State parent;
    public String actionFromParent;
    public String counterActionFromParent;

    public State(Cube cube){
        this.state = GetState(cube);
    }

    public State setParent(State parent, String actionFromParent){
        this.parent = parent;
        this.actionFromParent = actionFromParent;
        if (actionFromParent.equals("Up")){
            counterActionFromParent = "Up Inverted";
        } else if (actionFromParent.equals("Up Inverted")){
            counterActionFromParent = "Up";
        } else if (actionFromParent.equals("Bottom")){
            counterActionFromParent = "Bottom Inverted";
        } else if (actionFromParent.equals("Bottom Inverted")){
            counterActionFromParent = "Bottom";
        } else if (actionFromParent.equals("Front")){
            counterActionFromParent = "Front Inverted";
        } else if (actionFromParent.equals("Front Inverted")){
            counterActionFromParent = "Front";
        } else if (actionFromParent.equals("Back")){
            counterActionFromParent = "Back Inverted";
        } else if (actionFromParent.equals("Back Inverted")){
            counterActionFromParent = "Back";
        } else if (actionFromParent.equals("Left")){
            counterActionFromParent = "Left Inverted";
        } else if (actionFromParent.equals("Left")){
            counterActionFromParent = "Left Inverted";
        } else if (actionFromParent.equals("Right")){
            counterActionFromParent = "Right Inverted";
        } else if (actionFromParent.equals("Right Inverted")){
            counterActionFromParent = "Right";
        }
        return this;
    }

    public static int[] finalState = new int[] {
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            1, 1, 1, 1, 1, 1, 1, 1, 1,
            2, 2, 2, 2, 2, 2, 2, 2, 2,
            3, 3, 3, 3, 3, 3, 3, 3, 3,
            4, 4, 4, 4, 4, 4, 4, 4, 4,
            5, 5, 5, 5, 5, 5, 5, 5, 5
    };

    public boolean CheckFinalState(){
        for (int i = 0; i < state.length; i++){
            if (state[i] != finalState[i]){
                return false;
            }
        }
        return true;
    }

    public boolean CheckEqualStates(State other){
        for (int i = 0; i < other.state.length; i++){
            if (this.state[i] != other.state[i]){
                return false;
            }
        }
        return true;
    }

    private int[] GetState(Cube cube){
        int[] result = new int[cube.faces.length * cube.faces[0].tiles.length];
        for (int i = 0; i < cube.faces.length; i++){
            for (int j = 0; j < cube.faces[i].tiles.length; j++){
                result[i * cube.faces[i].tiles.length + j] = cube.faces[i].tiles[j].color_index;
            }
        }
        return result;
    }

    @Override
    public String toString(){
        String result = "";
        for (int i = 0; i < state.length; i++){
            result += ((i % 9 == 0) ? ("[") : ("")) + Colors.colors[state[i]] + ((i % 9 != 8) ? (", ") : ("]"));
        }
        return result;
    }

}
