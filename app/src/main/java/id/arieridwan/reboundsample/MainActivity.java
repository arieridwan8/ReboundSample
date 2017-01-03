package id.arieridwan.reboundsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;

public class MainActivity extends AppCompatActivity {

    private static double TENSION = 800;
    private static double DAMPER = 20; //friction
    private ImageView img;
    private SpringSystem mSpringSystem;
    private Spring mSpring;

    // for translate
    private boolean mMovedUp = false;
    private float mOrigY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // View init
        setContentView(R.layout.activity_main);
        img = (ImageView) findViewById(R.id.images);
        // Spring init
        mSpringSystem = SpringSystem.create();
        mSpring = mSpringSystem.createSpring();
        SpringConfig config = new SpringConfig(TENSION, DAMPER);
        mSpring.setSpringConfig(config);
        reboundScale();
    }

    public void reboundScale(){
        img.setOnClickListener(null);
        img.setOnTouchListener(null);
        mSpring.removeAllListeners();
        img.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mSpring.setEndValue(1f);
                        return true;
                    case MotionEvent.ACTION_UP:
                        mSpring.setEndValue(0f);
                        return true;
                }
                return false;
            }
        });

        mSpring.addListener(new SpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                float value = (float) spring.getCurrentValue();
                float scale = 1f - (value * 0.5f);
                img.setScaleX(scale);
                img.setScaleY(scale);
            }

            @Override
            public void onSpringAtRest(Spring spring) {

            }

            @Override
            public void onSpringActivate(Spring spring) {

            }

            @Override
            public void onSpringEndStateChange(Spring spring) {

            }
        });
    }

    public void reboundTranslate(){
        img.setOnClickListener(null);
        img.setOnTouchListener(null);
        mSpring.removeAllListeners();
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMovedUp) {
                    mSpring.setEndValue(mOrigY);
                } else {
                    mOrigY = img.getY();
                    mSpring.setEndValue(mOrigY - 300f);
                }
                mMovedUp = !mMovedUp;
            }
        });

        mSpring.addListener(new SpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                float value = (float) spring.getCurrentValue();
                img.setY(value);
            }

            @Override
            public void onSpringAtRest(Spring spring) {

            }

            @Override
            public void onSpringActivate(Spring spring) {

            }

            @Override
            public void onSpringEndStateChange(Spring spring) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.scale:
                reboundScale();
                break;
            case R.id.translate:
                reboundTranslate();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
