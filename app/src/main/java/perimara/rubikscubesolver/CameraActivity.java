package perimara.rubikscubesolver;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;

import butterknife.ButterKnife;
import butterknife.InjectView;
import perimara.rubikscubesolver.rubikscube.Cube;
import perimara.rubikscubesolver.rubikscube.Face;
import perimara.rubikscubesolver.rubikscube.Tile;
import perimara.rubikscubesolver.rubikscube.utils.ImageToRubiksFace;

/**
 * Created by periklismaravelias on 23/12/16.
 */
public class CameraActivity extends AppCompatActivity implements SurfaceHolder.Callback{
    Camera camera;
    @InjectView(R.id.surfaceView)
    SurfaceView surfaceView;
    @InjectView(R.id.btn_take_photo)
    FloatingActionButton btn_take_photo;
    SurfaceHolder surfaceHolder;
    Camera.PictureCallback jpegCallback;

    int x_offset = 0, y_offset = 0;
    int step = 1;

    Cube cube = new Cube();
    Face f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        ButterKnife.inject(this);

        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        btn_take_photo.setOnClickListener(new FloatingActionButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraImage();
            }
        });
        jpegCallback = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(final byte[] data, Camera camera) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        f = ImageToRubiksFace.process(CameraActivity.this, data, x_offset, y_offset);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TableLayout preview = f.getPreview(getApplication());
                                ((RelativeLayout)findViewById(R.id.previewFace)).removeAllViews();
                                ((RelativeLayout)findViewById(R.id.previewFace)).addView(preview);
                                findViewById(R.id.previewLayout).setVisibility(View.VISIBLE);
                            }
                        });
                    }
                }).start();
                refreshCamera();
            }
        };

        findViewById(R.id.confirmNoBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.previewLayout).setVisibility(View.GONE);
            }
        });

        findViewById(R.id.confirmYesBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.previewLayout).setVisibility(View.GONE);
                cube.faces[step - 1] = f;
                step++;
                if (step == 2){
                    ((TextView)findViewById(R.id.hint)).setText(R.string.hint_red);
                } else if (step == 3){
                    ((TextView)findViewById(R.id.hint)).setText(R.string.hint_green);
                } else if (step == 4){
                    ((TextView)findViewById(R.id.hint)).setText(R.string.hint_orange);
                } else if (step == 5){
                    ((TextView)findViewById(R.id.hint)).setText(R.string.hint_blue);
                } else if (step == 6){
                    ((TextView)findViewById(R.id.hint)).setText(R.string.hint_yellow);
                } else if (step == 7){
                    Intent intent = new Intent(CameraActivity.this, SolveActivity.class);
                    for (int i = 0; i < cube.faces.length; i++){
                        Face curr_f = cube.faces[i];
                        for (int j = 0; j < curr_f.tiles.length; j++) {
                            Tile curr_t = curr_f.tiles[j];
                            intent.putExtra("face" + i + "-tile" + j + "-color", curr_t.color);
                            intent.putExtra("face" + i + "-tile" + j + "-colorValue", curr_t.color_value);
                            intent.putExtra("face" + i + "-tile" + j + "-colorIndex", curr_t.color_index);
                        }
                    }
                    startActivity(intent);
                }
            }
        });

        final ImageView cameraRectangleArea = (ImageView)findViewById(R.id.cameraRectangleArea);
        cameraRectangleArea.setBackgroundResource(R.drawable.camera_rectangle_area);

        findViewById(R.id.mainlayout).getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    findViewById(R.id.mainlayout).getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    findViewById(R.id.mainlayout).getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                x_offset = cameraRectangleArea.getLeft();
                y_offset = cameraRectangleArea.getTop();
            }
        });
    }

    private void refreshCamera(){
        if (surfaceHolder.getSurface() == null){
            return;
        }
        try{
            camera.stopPreview();
        } catch(Exception e){

        }
        try{
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch(Exception e){

        }
    }

    private void cameraImage(){
        camera.takePicture(null, null, jpegCallback);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try{
            camera = Camera.open(0);
        } catch(RuntimeException re){

        }
        Camera.Parameters parameters = camera.getParameters();
        parameters.setPreviewFrameRate(20);
        parameters.setPreviewSize(300, 300);
        camera.setParameters(parameters);
        camera.setDisplayOrientation(90);
        try{
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch(Exception e){

        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        refreshCamera();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview();
        camera.release();
        camera = null;
    }
}
