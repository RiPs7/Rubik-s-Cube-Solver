package perimara.rubikscubesolver.rubikscube;

import android.widget.ImageView;

/**
 * Created by periklismaravelias on 24/12/16.
 */
public class Tile {

    public int[] color_value;
    public int color_index;
    public String color;
    public ImageView preview;

    public void setProperties(int color_index){
        this.color_index = color_index;
        this.color_value = Colors.colors_value[this.color_index];
        this.color = Colors.colors[this.color_index];
    }

}
