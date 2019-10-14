package edu.ucla.hci.bakeoffone;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int layoutWidth, layoutHeight;
    int centerX;
    int centerY;
    TextView Zero;
    TextView Three;
    TextView Six;
    TextView Nine;
    TextView textView;
    Button redDot;
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        ImageView touchArea = (ImageView) findViewById(R.id.touchArea);
        layoutWidth = touchArea.getWidth();
        layoutHeight = touchArea.getHeight();
        centerX = layoutWidth / 2;
        centerY = layoutHeight / 2;
    }

    // Selection made, generate actual input
    void lightUp(int currentX, int currentY) {
        int quarterX = centerX / 2;
        int quarterY = centerY / 2;
        Log.i("TAG", "3/2x:" + quarterX + " 3/2y:" + quarterY + " currentX" + currentX + " currentY" + currentY);
        if ((currentY < centerY) && (quarterX < currentX) && (currentX < 3*quarterX/2)){    // Activate Up
            Zero.setImageDrawable(getResources().getDrawable((R.drawable.on)));
            Three.setImageDrawable(getResources().getDrawable((R.drawable.off)));
            Six.setImageDrawable(getResources().getDrawable((R.drawable.off)));
            Nine.setImageDrawable(getResources().getDrawable((R.drawable.off)));
        }
        if ((currentX > centerX) && (quarterY < currentY) && (currentY < 3*quarterY/2)){    // Activate Left
            Three.setImageDrawable(getResources().getDrawable((R.drawable.on)));
            Six.setImageDrawable(getResources().getDrawable((R.drawable.off)));
            Nine.setImageDrawable(getResources().getDrawable((R.drawable.off)));
            Zero.setImageDrawable(getResources().getDrawable((R.drawable.off)));
        }
        if ((currentY > centerY) && (quarterX < currentX) && (currentX < 3*quarterX/2)){    // Activate Down
            Six.setImageDrawable(getResources().getDrawable((R.drawable.on)));
            Nine.setImageDrawable(getResources().getDrawable((R.drawable.off)));
            Zero.setImageDrawable(getResources().getDrawable((R.drawable.off)));
            Three.setImageDrawable(getResources().getDrawable((R.drawable.off)));
        }
        if ((currentX < centerX) && (quarterY < currentY) && (currentY < 3*quarterY/2)){    // Activate Right
            Nine.setImageDrawable(getResources().getDrawable((R.drawable.on)));
            Zero.setImageDrawable(getResources().getDrawable((R.drawable.off)));
            Three.setImageDrawable(getResources().getDrawable((R.drawable.off)));
            Six.setImageDrawable(getResources().getDrawable((R.drawable.off)));
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Zero = (TextView) findViewById(R.id.Zero);
        Three = (TextView) findViewById(R.id.Three);
        Six = (TextView) findViewById(R.id.Six);
        Nine = (TextView) findViewById(R.id.Nine);
        textView = (TextView) findViewById(R.id.textView);
        redDot = (Button) findViewById(R.id.redDot);
        redDot.setOnTouchListener(new View.OnTouchListener(){
            int lastX, lastY; //Save last location
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int act=event.getAction();
                switch(act){
                    case MotionEvent.ACTION_DOWN:
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE: // Move
                        // Dynamic Location Calculator
                        int dx = (int) event.getRawX() - lastX;
                        int dy = (int) event.getRawY() - lastY;
                        int left = v.getLeft() + dx;
                        int top = v.getTop() + dy;
                        int right = v.getRight() + dx;
                        int bottom = v.getBottom() + dy;
                        if (left < 0) {
                            left = 0;
                            right = left + v.getWidth();
                        }
                        if (right > layoutWidth) {
                            right = layoutWidth;
                            left = right - v.getWidth();
                        }
                        if (top < 0) {
                            top = 0;
                            bottom = top + v.getHeight();
                        }
                        if (bottom > layoutHeight) {
                            bottom = layoutHeight;
                            top = bottom - v.getHeight();
                        }
                        v.layout(left, top, right, bottom); // Draw new button
                        lightUp(left,top);   // Highlight Border of selected Input-Letter-Box.
                        // Toast.makeText(getActivity(), "position：" + left + ", " +
                        // top + ", " + right + ", " + bottom, 0)
                        // .show();
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        textView.setText("DEBUG #: LEFT:" + left + " TOP:" + top +" x:" + lastX + " y:" + lastY);
                        break;
                    case MotionEvent.ACTION_UP: // Up
                        // Attach to the border
//					int dx1 = (int) event.getRawX() - lastX;
//					int dy1 = (int) event.getRawY() - lastY;
//					int left1 = v.getLeft() + dx1;
//					int top1 = v.getTop() + dy1;
//					int right1 = v.getRight() + dx1;
//					int bottom1 = v.getBottom() + dy1;
//					if (left1 < (screenWidth / 2)) {
//						if (top1 < 100) {
//							v.layout(left1, 0, right1, btnHeight);
//						} else if (bottom1 > (screenHeight - 200)) {
//							v.layout(left1, (screenHeight - btnHeight), right1, screenHeight);
//						} else {
//							v.layout(0, top1, btnHeight, bottom1);
//						}
//					} else {
//						if (top1 < 100) {
//							v.layout(left1, 0, right1, btnHeight);
//						} else if (bottom1 > (screenHeight - 200)) {
//							v.layout(left1, (screenHeight - btnHeight), right1, screenHeight);
//						} else {
//							v.layout((screenWidth - btnHeight), top1, screenWidth, bottom1);
//						}
//					}
//					break;
                }
                return false;
            }
        });
    }
}
