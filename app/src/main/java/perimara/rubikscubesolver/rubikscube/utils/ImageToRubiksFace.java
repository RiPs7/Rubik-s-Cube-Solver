package perimara.rubikscubesolver.rubikscube.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import perimara.rubikscubesolver.rubikscube.Colors;
import perimara.rubikscubesolver.rubikscube.Face;

/**
 * Created by periklismaravelias on 23/12/16.
 */
public class ImageToRubiksFace {

    public static Face process(Context context, byte[] image, int x_offset, int y_offset){

        /*
        For every tile looking like this the x pixels are checked:
        (they are covering just 100 pixels area (-5 to 5 from the very center vertically and horizontally))
        .----------------------------.
        |                            |
        |                            |
        |         xxxxxxxxxx         |
        |         xxxxxxxxxx         |
        |         xxxxxxxxxx         |
        |         xxxxxxxxxx         |
        |         xxxxxxxxxx         |
        |         xxxxxxxxxx         |
        |                            |
        |                            |
        .----------------------------.
         */

        Face result = new Face();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);

        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        int height = bitmap.getHeight() - 2 * y_offset;
        int width = bitmap.getWidth() - 2 * x_offset;

        int tile_height = height / 3;
        int tile_width = width / 3;

        for (int i = 0; i < 3; i++){ //for every tile row..
            for (int j = 0; j < 3; j++){ //for every tile column
                int[] records = new int[Colors.colors_value.length]; //init records array to count how many instances of each color are recognised
                for (int k = -5; k < 5; k++){ //for each of the 5 pixels above and below
                    for (int l = -5; l < 5; l++){ //for each of 5 pixels
                        int index_i = Math.round(i * (float)tile_height + (tile_height / 2.0f) + k) + y_offset; //find index i
                        int index_j = Math.round(j * (float) tile_width + (tile_width / 2.0f) + l) + x_offset; //find index j
                        int rgb = bitmap.getPixel(index_j, index_i);
                        int r = (rgb >> 16) & 0x000000FF;
                        int g = (rgb >> 8) & 0x000000FF;
                        int b = (rgb) & 0x000000FF;
                        int closer_diff = Integer.MAX_VALUE;
                        int closer_color_index = -1;
                        for (int c = 0; c < Colors.colors_value.length; c++){
                            int diff =
                                    (r - Colors.colors_value[c][0]) * (r - Colors.colors_value[c][0]) +
                                    (g - Colors.colors_value[c][1]) * (g - Colors.colors_value[c][1]) +
                                    (b - Colors.colors_value[c][2]) * (b - Colors.colors_value[c][2]);
                            if (diff < closer_diff){
                                closer_diff = diff;
                                closer_color_index = c;
                            }
                        }
                        records[closer_color_index]++;
                    }
                }
                int maximum_record = 0;
                int maximum_record_index = -1;
                for (int a = 0; a < records.length; a++){
                    if (records[a] > maximum_record){
                        maximum_record = records[a];
                        maximum_record_index = a;
                    }
                }
                result.tiles[i * 3 + j].setProperties(maximum_record_index);
                /*result.tiles[i * 3 + j].color = Colors.colors[maximum_record_index];
                result.tiles[i * 3 + j].color_value = Colors.colors_value[maximum_record_index];
                result.tiles[i * 3 + j].color_index = maximum_record_index;*/
            }
        }

        return result;
    }

}
