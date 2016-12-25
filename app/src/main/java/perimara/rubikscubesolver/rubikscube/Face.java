package perimara.rubikscubesolver.rubikscube;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.Arrays;

/**
 * Created by periklismaravelias on 23/12/16.
 */
public class Face {

    public Tile[] tiles;

    public Face(){
        tiles = new Tile[9];
        for (int i = 0; i < tiles.length; i++){
            tiles[i] = new Tile();
        }
    }

    @Override
    public String toString() {
        String result = "";
        for (Tile t : this.tiles) {
            result += t.color + ", ";
        }
        return result.substring(0, result.length() - 2);
    }

    public TableLayout getPreview(Context c){
        for (int i = 0; i < tiles.length; i++){
            tiles[i].preview = new ImageView(c);
            tiles[i].preview.setBackgroundColor(Color.argb(255, tiles[i].color_value[0], tiles[i].color_value[1], tiles[i].color_value[2]));
            TableRow.LayoutParams trlp = new TableRow.LayoutParams(200, 200);
            trlp.setMargins(10, 5, 10, 5);
            tiles[i].preview.setLayoutParams(trlp);
        }
        TableLayout result = new TableLayout(c);
        TableRow tr = null;
        for (int i = 0; i < tiles.length; i++){
            if (i % 3 == 0) {
                tr = new TableRow(c);
                tr.setBackgroundColor(Color.BLACK);
                tr.setPadding(10, 5, 10, 5);
                result.addView(tr);
            }
            tr.addView(tiles[i].preview);
        }
        return result;
    }
}