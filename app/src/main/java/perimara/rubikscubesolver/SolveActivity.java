package perimara.rubikscubesolver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import perimara.rubikscubesolver.rubikscube.Cube;
import perimara.rubikscubesolver.rubikscube.Face;
import perimara.rubikscubesolver.rubikscube.utils.MainSolver;

public class SolveActivity extends AppCompatActivity {

    Cube cube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve_it);

        cube = new Cube();

        Bundle b = getIntent().getExtras();
        if (b != null){
            for (int i = 0; i < cube.faces.length; i++){
                Face f = cube.faces[i];
                for (int j = 0; j < f.tiles.length; j++) {
                    f.tiles[j].color = b.getString("face" + i + "-tile" + j + "-color");
                    f.tiles[j].color_value = b.getIntArray("face" + i + "-tile" + j + "-colorValue");
                    f.tiles[j].color_index = b.getInt("face" + i + "-tile" + j + "-colorIndex");
                }
            }
        }

        String[] solution = MainSolver.Solve(cube);
        String result = "";
        for (String s : solution){
            if (s.equals("V")){
                continue;
            }
            result += s + ", ";
        }

        if (!result.equals("")) {
            ((TextView) findViewById(R.id.solution)).setText("Solution steps: " + result.substring(0, result.length() - 2));
        }
    }
}
