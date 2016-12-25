package perimara.rubikscubesolver.rubikscube;

/**
 * Created by periklismaravelias on 25/12/16.
 */
public class State {

    public int[] state;
    public State parent;
    public String actionFromParent;

    public State(Cube cube){
        this.state = GetState(cube);
    }

    public State setParent(State parent, String actionFromParent){
        this.parent = parent;
        this.actionFromParent = actionFromParent;
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

}
