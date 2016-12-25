package perimara.rubikscubesolver.rubikscube;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created by periklismaravelias on 23/12/16.
 */
public class Cube {

    /*
    The cube faces are ordered like:
    [white, red, green, orange, blue, yellow] and by orientation like:
    [bottom, front, right, back, left, top]
     */

    public Face[] faces;

    public Cube(){
        this.faces = new Face[6];
        for (int i = 0; i < this.faces.length; i++){
            this.faces[i] = new Face();
        }
    }

    @Override
    public String toString() {
        String result = "";
        for (Face f : this.faces) {
            result += "[" + f.toString() + "]";
        }
        return result;
    }

    //Top face rotations
    public Cube RotateUpClockwise(){
        //Store front face top tiles in temp to rotate top layer
        Tile[] temp = new Tile[3];
        temp[0] = this.faces[1].tiles[0];
        temp[1] = this.faces[1].tiles[1];
        temp[2] = this.faces[1].tiles[2];

        this.faces[1].tiles[0] = this.faces[2].tiles[0];
        this.faces[1].tiles[1] = this.faces[2].tiles[1];
        this.faces[1].tiles[2] = this.faces[2].tiles[2];

        this.faces[2].tiles[0] = this.faces[3].tiles[0];
        this.faces[2].tiles[1] = this.faces[3].tiles[1];
        this.faces[2].tiles[2] = this.faces[3].tiles[2];

        this.faces[3].tiles[0] = this.faces[4].tiles[0];
        this.faces[3].tiles[1] = this.faces[4].tiles[1];
        this.faces[3].tiles[2] = this.faces[4].tiles[2];

        this.faces[4].tiles[0] = temp[0];
        this.faces[4].tiles[1] = temp[1];
        this.faces[4].tiles[2] = temp[2];

        //Then store top face front tiles in temp to rotate top face
        temp = new Tile[2];
        temp[0] = this.faces[5].tiles[6];
        temp[1] = this.faces[5].tiles[7];

        this.faces[5].tiles[6] = this.faces[5].tiles[8];
        this.faces[5].tiles[7] = this.faces[5].tiles[5];

        this.faces[5].tiles[8] = this.faces[5].tiles[2];
        this.faces[5].tiles[5] = this.faces[5].tiles[1];

        this.faces[5].tiles[2] = this.faces[5].tiles[0];
        this.faces[5].tiles[1] = this.faces[5].tiles[3];

        this.faces[5].tiles[0] = temp[0];
        this.faces[5].tiles[3] = temp[1];

        return this;
    }

    public Cube RotateUpCounterClockwise(){
        //Call three times the clockwise function. That is a single move.
        RotateUpClockwise();
        RotateUpClockwise();
        RotateUpClockwise();

        return this;
    }

    //Bottom face rotations
    public Cube RotateBottomClockwise(){
        //Store front face bottom tiles in temp to rotate bottom layer
        Tile[] temp = new Tile[3];
        temp[0] = this.faces[1].tiles[6];
        temp[1] = this.faces[1].tiles[7];
        temp[2] = this.faces[1].tiles[8];

        this.faces[1].tiles[6] = this.faces[4].tiles[6];
        this.faces[1].tiles[7] = this.faces[4].tiles[7];
        this.faces[1].tiles[8] = this.faces[4].tiles[8];

        this.faces[4].tiles[6] = this.faces[3].tiles[6];
        this.faces[4].tiles[7] = this.faces[3].tiles[7];
        this.faces[4].tiles[8] = this.faces[3].tiles[8];

        this.faces[3].tiles[6] = this.faces[2].tiles[6];
        this.faces[3].tiles[7] = this.faces[2].tiles[7];
        this.faces[3].tiles[8] = this.faces[2].tiles[8];

        this.faces[2].tiles[6] = temp[0];
        this.faces[2].tiles[7] = temp[1];
        this.faces[2].tiles[8] = temp[2];

        //Then store bottom face front tiles in temp to rotate bottom face
        temp = new Tile[2];
        temp[0] = this.faces[0].tiles[1];
        temp[1] = this.faces[0].tiles[2];

        this.faces[0].tiles[1] = this.faces[0].tiles[3];
        this.faces[0].tiles[2] = this.faces[0].tiles[0];

        this.faces[0].tiles[0] = this.faces[0].tiles[6];
        this.faces[0].tiles[3] = this.faces[0].tiles[7];

        this.faces[0].tiles[6] = this.faces[0].tiles[8];
        this.faces[0].tiles[7] = this.faces[0].tiles[5];

        this.faces[0].tiles[8] = temp[0];
        this.faces[0].tiles[5] = temp[1];

        return this;
    }

    public Cube RotateBottomCounterClockwise(){
        //Call three times the clockwise function. That is a single move.
        RotateBottomClockwise();
        RotateBottomClockwise();
        RotateBottomClockwise();

        return this;
    }

    //Front face rotations
    public Cube RotateFrontClockwise(){
        //Store top face bottom tiles in temp to rotate bottom layer
        Tile[] temp = new Tile[3];
        temp[0] = this.faces[5].tiles[6];
        temp[1] = this.faces[5].tiles[7];
        temp[2] = this.faces[5].tiles[8];

        this.faces[5].tiles[6] = this.faces[4].tiles[8];
        this.faces[5].tiles[7] = this.faces[4].tiles[5];
        this.faces[5].tiles[8] = this.faces[4].tiles[2];

        this.faces[4].tiles[8] = this.faces[0].tiles[2];
        this.faces[4].tiles[5] = this.faces[0].tiles[1];
        this.faces[4].tiles[2] = this.faces[0].tiles[0];

        this.faces[0].tiles[2] = this.faces[2].tiles[6];
        this.faces[0].tiles[1] = this.faces[2].tiles[3];
        this.faces[0].tiles[0] = this.faces[2].tiles[0];

        this.faces[2].tiles[6] = temp[0];
        this.faces[2].tiles[3] = temp[1];
        this.faces[2].tiles[0] = temp[2];

        //Then store bottom face front tiles in temp to rotate bottom face
        temp = new Tile[2];
        temp[0] = this.faces[1].tiles[1];
        temp[1] = this.faces[1].tiles[2];

        this.faces[1].tiles[1] = this.faces[1].tiles[3];
        this.faces[1].tiles[2] = this.faces[1].tiles[0];

        this.faces[1].tiles[0] = this.faces[1].tiles[6];
        this.faces[1].tiles[3] = this.faces[1].tiles[7];

        this.faces[1].tiles[6] = this.faces[1].tiles[8];
        this.faces[1].tiles[7] = this.faces[1].tiles[5];

        this.faces[1].tiles[8] = temp[0];
        this.faces[1].tiles[5] = temp[1];

        return this;
    }

    public Cube RotateFrontCounterClockwise(){
        //Call three times the clockwise function. That is a single move.
        RotateFrontClockwise();
        RotateFrontClockwise();
        RotateFrontClockwise();

        return this;
    }

    //Back face rotations
    public Cube RotateBackClockwise(){
        //Store top face top tiles in temp to rotate back layer
        Tile[] temp = new Tile[3];
        temp[0] = this.faces[5].tiles[0];
        temp[1] = this.faces[5].tiles[1];
        temp[2] = this.faces[5].tiles[2];

        this.faces[5].tiles[0] = this.faces[2].tiles[2];
        this.faces[5].tiles[1] = this.faces[2].tiles[5];
        this.faces[5].tiles[2] = this.faces[2].tiles[8];

        this.faces[2].tiles[2] = this.faces[0].tiles[8];
        this.faces[2].tiles[5] = this.faces[0].tiles[7];
        this.faces[2].tiles[8] = this.faces[0].tiles[6];

        this.faces[0].tiles[8] = this.faces[4].tiles[6];
        this.faces[0].tiles[7] = this.faces[4].tiles[3];
        this.faces[0].tiles[6] = this.faces[4].tiles[0];

        this.faces[4].tiles[6] = temp[0];
        this.faces[4].tiles[3] = temp[1];
        this.faces[4].tiles[0] = temp[2];

        //Then store bottom face front tiles in temp to rotate bottom face
        temp = new Tile[2];
        temp[0] = this.faces[3].tiles[1];
        temp[1] = this.faces[3].tiles[2];

        this.faces[3].tiles[1] = this.faces[3].tiles[3];
        this.faces[3].tiles[2] = this.faces[3].tiles[0];

        this.faces[3].tiles[0] = this.faces[3].tiles[6];
        this.faces[3].tiles[3] = this.faces[3].tiles[7];

        this.faces[3].tiles[6] = this.faces[3].tiles[8];
        this.faces[3].tiles[7] = this.faces[3].tiles[5];

        this.faces[3].tiles[8] = temp[0];
        this.faces[3].tiles[5] = temp[1];

        return this;
    }

    public Cube RotateBackCounterClockwise(){
        //Call three times the clockwise function. That is a single move.
        RotateBackClockwise();
        RotateBackClockwise();
        RotateBackClockwise();

        return this;
    }

    //Left face rotations
    public Cube RotateLeftClockwise(){
        //Store top face left tiles in temp to rotate left layer
        Tile[] temp = new Tile[3];
        temp[0] = this.faces[5].tiles[0];
        temp[1] = this.faces[5].tiles[3];
        temp[2] = this.faces[5].tiles[6];

        this.faces[5].tiles[0] = this.faces[3].tiles[8];
        this.faces[5].tiles[3] = this.faces[3].tiles[5];
        this.faces[5].tiles[6] = this.faces[3].tiles[2];

        this.faces[3].tiles[8] = this.faces[0].tiles[0];
        this.faces[3].tiles[5] = this.faces[0].tiles[3];
        this.faces[3].tiles[2] = this.faces[0].tiles[6];

        this.faces[0].tiles[0] = this.faces[1].tiles[0];
        this.faces[0].tiles[3] = this.faces[1].tiles[3];
        this.faces[0].tiles[6] = this.faces[1].tiles[6];

        this.faces[1].tiles[0] = temp[0];
        this.faces[1].tiles[3] = temp[1];
        this.faces[1].tiles[6] = temp[2];

        //Then store bottom face front tiles in temp to rotate bottom face
        temp = new Tile[2];
        temp[0] = this.faces[4].tiles[1];
        temp[1] = this.faces[4].tiles[2];

        this.faces[4].tiles[1] = this.faces[4].tiles[3];
        this.faces[4].tiles[2] = this.faces[4].tiles[0];

        this.faces[4].tiles[3] = this.faces[4].tiles[7];
        this.faces[4].tiles[0] = this.faces[4].tiles[6];

        this.faces[4].tiles[7] = this.faces[4].tiles[5];
        this.faces[4].tiles[6] = this.faces[4].tiles[8];

        this.faces[4].tiles[5] = temp[1];
        this.faces[4].tiles[8] = temp[0];

        return this;
    }

    public Cube RotateLeftCounterClockwise(){
        //Call three times the clockwise function. That is a single move.
        RotateLeftClockwise();
        RotateLeftClockwise();
        RotateLeftClockwise();

        return this;
    }

    //Right face rotations
    public Cube RotateRightClockwise(){
        //Store top face right tiles in temp to rotate right layer
        Tile[] temp = new Tile[3];
        temp[0] = this.faces[5].tiles[2];
        temp[1] = this.faces[5].tiles[5];
        temp[2] = this.faces[5].tiles[8];

        this.faces[5].tiles[2] = this.faces[1].tiles[2];
        this.faces[5].tiles[5] = this.faces[1].tiles[5];
        this.faces[5].tiles[8] = this.faces[1].tiles[8];

        this.faces[1].tiles[2] = this.faces[0].tiles[2];
        this.faces[1].tiles[5] = this.faces[0].tiles[5];
        this.faces[1].tiles[8] = this.faces[0].tiles[8];

        this.faces[0].tiles[2] = this.faces[3].tiles[6];
        this.faces[0].tiles[5] = this.faces[3].tiles[3];
        this.faces[0].tiles[8] = this.faces[3].tiles[0];

        this.faces[3].tiles[6] = temp[0];
        this.faces[3].tiles[3] = temp[1];
        this.faces[3].tiles[0] = temp[2];

        //Then store bottom face front tiles in temp to rotate bottom face
        temp = new Tile[2];
        temp[0] = this.faces[2].tiles[1];
        temp[1] = this.faces[2].tiles[2];

        this.faces[2].tiles[1] = this.faces[2].tiles[3];
        this.faces[2].tiles[2] = this.faces[2].tiles[0];

        this.faces[2].tiles[3] = this.faces[2].tiles[7];
        this.faces[2].tiles[0] = this.faces[2].tiles[6];

        this.faces[2].tiles[7] = this.faces[2].tiles[5];
        this.faces[2].tiles[6] = this.faces[2].tiles[8];

        this.faces[2].tiles[5] = temp[1];
        this.faces[2].tiles[8] = temp[0];

        return this;
    }

    public Cube RotateRightCounterClockwise(){
        //Call three times the clockwise function. That is a single move.
        RotateRightClockwise();
        RotateRightClockwise();
        RotateRightClockwise();

        return this;
    }


}