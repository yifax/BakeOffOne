package edu.ucla.hci.bakeoffone;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.lang.Math;

public class MainActivity extends AppCompatActivity {
    int layoutWidth, layoutHeight;
    int centerX;
    int centerY;
    int leftC, topC, bottomC, rightC;
    int MODE = -1;
    int SEL = 0;
    TextView Zero;
    TextView NE;
    TextView Three;
    TextView SE;
    TextView Six;
    TextView SW;;
    TextView Nine;
    TextView NW;
    TextView textView;
    Button redDot;
    Button blueDot;

    //Calculate the current clockwise-view angle
    double angleCalculator(int cX, int cY, float X, float Y){
        double radius = Math.atan2(X-cX, cY-Y);
        return radius / (Math.PI / 180);
    }

    // Selection made, generate actual input
    int selectionDetect(float currentX, float currentY){
        double ang = angleCalculator(centerX, centerY, currentX, currentY);
        Log.i("TAG", "angle:" + ang);
        if ((-22.5 <= ang) && (ang < 22.5)) {    // Zero
            return 1;
        }
        if ((22.5 <= ang) && (ang < 67.5)) {    // NE
            return 2;
        }
        if ((67.5 <= ang) && (ang < 112.5)) {    // Three
            return 3;
        }
        if ((112.5 <= ang) && (ang < 157.5)) {    // SE
            return 4;
        }
        if ((157.5 <= ang) || ( ang < -157.5)) {    // Six
            return 5;
        }
        if ((-157.5 <= ang) && (ang < -112.5)) {    // SW
            return 6;
        }
        if ((-112.5 <= ang) && (ang < -67.5)) {    // Nine
            return 7;
        }
        if ((-67.5 <= ang) && (ang < -22.5)) {    // NW
            return  8;
        }
        return 0;
    }

    void refreshDisplay(int selection) {
        int scale = 25;
        int original = 20;
        Zero.setTextSize(original);
        NE.setTextSize(original);
        Three.setTextSize(original);
        SE.setTextSize(original);
        Six.setTextSize(original);
        SW.setTextSize(original);
        Nine.setTextSize(original);
        NW.setTextSize(original);
        Zero.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        NE.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        Three.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        SE.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        Six.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        SW.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        Nine.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        NW.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        // Highlight Border of selected Input-Letter-Box.
        switch (selection) {
            case 1: Zero.setTextSize(scale); Zero.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD)); break;
            case 2: NE.setTextSize(scale); NE.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD)); break;
            case 3: Three.setTextSize(scale); Three.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD)); break;
            case 4: SE.setTextSize(scale); SE.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD)); break;
            case 5: Six.setTextSize(scale); Six.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD)); break;
            case 6: SW.setTextSize(scale); SW.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD)); break;
            case 7: Nine.setTextSize(scale); Nine.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD)); break;
            case 8: NW.setTextSize(scale); NW.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD)); break;
            case 0: Log.i("TAG", "No Selection Made");
        }
    }

    void iniLetters(int selection){
        String[] cc = new String[]{"X","X","X","X","X","X","X","X"};
        switch (selection) {
            case 1: cc = new String[]{"A","B","C","D","E","F"," "," "}; break;
            case 2: cc = new String[]{"←"," "," "," "," "," "," "," "}; break;
            case 3: cc = new String[]{"G","H","I","J","K","L","M","N"}; break;
            case 4: cc = new String[]{"0","1","2","3","4","5","6","7"}; break;
            case 5: cc = new String[]{"O","P","Q","R","S","T"," "," "}; break;
            case 6: cc = new String[]{"8","9","!","?",",",".","/","-"}; break;
            case 7: cc = new String[]{"U","V","W","X","Y","Z"," "," "}; break;
            case 8: cc = new String[]{"⌴"," "," "," "," "," "," "," "}; break;
            case 0: Log.i("TAG", "iniLetters: ERROR Parameter!");
        }
        Zero.setText(cc[0]);
        NE.setText(cc[1]);
        Three.setText(cc[2]);
        SE.setText(cc[3]);
        Six.setText(cc[4]);
        SW.setText(cc[5]);
        Nine.setText(cc[6]);
        NW.setText(cc[7]);
    }
    void restoreLetters(){
        String[] cc = new String[]{"A-F","←","G-N","#","O-T","*","U-Z","⌴"};
        Zero.setText(cc[0]);
        NE.setText(cc[1]);
        Three.setText(cc[2]);
        SE.setText(cc[3]);
        Six.setText(cc[4]);
        SW.setText(cc[5]);
        Nine.setText(cc[6]);
        NW.setText(cc[7]);
        refreshDisplay(0);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        ImageView touchArea = (ImageView) findViewById(R.id.touchArea);
        layoutWidth = touchArea.getWidth();
        layoutHeight = touchArea.getHeight();
        centerX = layoutWidth / 2;
        centerY = layoutHeight / 2;
        if (MODE == -1) {
            leftC = redDot.getLeft();
            topC = redDot.getTop();
            rightC = redDot.getRight();
            bottomC = redDot.getBottom();
            MODE = 0;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Zero = (TextView) findViewById(R.id.Zero);
        NE = (TextView) findViewById(R.id.NE);
        Three = (TextView) findViewById(R.id.Three);
        SE = (TextView) findViewById(R.id.SE);
        Six = (TextView) findViewById(R.id.Six);
        SW = (TextView) findViewById(R.id.SW);
        Nine = (TextView) findViewById(R.id.Nine);
        NW = (TextView) findViewById(R.id.NW);
        textView = (TextView) findViewById(R.id.textView);
        redDot = (Button) findViewById(R.id.redDot);
        blueDot = (Button) findViewById(R.id.blueDot);

        // Reset Button
        blueDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blueDot.setEnabled(false);
                MODE = 0;
                SEL = 0;
                redDot.layout(leftC, topC, rightC, bottomC);
                restoreLetters();
            }
        });

        // Select Button
        redDot.setOnTouchListener(new View.OnTouchListener(){
            int lastX, lastY; //Save last location
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float cX, cY;
                int act = event.getAction();
                switch(act){
                    case MotionEvent.ACTION_DOWN:
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE: // Move ////////NEED FIX!!!!!!
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
                        cX = v.getX() + v.getWidth()  / 2;
                        cY = v.getY() + v.getHeight() / 2;
                        SEL = selectionDetect(cX, cY);
                        refreshDisplay(SEL);
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        //textView.setText("DEBUG #: LEFT:" + left + " TOP:" + top +" x:" + lastX + " y:" + lastY);
                        break;
                    case MotionEvent.ACTION_UP: // Up
                        if (MODE == 0) {    // Enter Input Mode
                            if (SEL == 2) { // Del Operation
                                String buffer = textView.getText().toString();
                                textView.setText(buffer.substring(0, buffer.length() - 1));
                                blueDot.performClick();
                            }
                            else if (SEL == 8) {  // Add Space
                                textView.setText(textView.getText().toString() + " ");
                                blueDot.performClick();
                            }
                            else if (SEL != 0){
                                MODE = 1;
                                iniLetters(SEL);
                                blueDot.setEnabled(true);
                                //blueDot.setVisibility(View.VISIBLE);
                            }
                        }
                        else if (MODE == 1) {  // Type in
                            switch (SEL) {
                                case 1: textView.setText(textView.getText().toString() + Zero.getText()); break;
                                case 2: textView.setText(textView.getText().toString() + NE.getText()); break;
                                case 3: textView.setText(textView.getText().toString() + Three.getText()); break;
                                case 4: textView.setText(textView.getText().toString() + SE.getText()); break;
                                case 5: textView.setText(textView.getText().toString() + Six.getText()); break;
                                case 6: textView.setText(textView.getText().toString() + SW.getText()); break;
                                case 7: textView.setText(textView.getText().toString() + Nine.getText()); break;
                                case 8: textView.setText(textView.getText().toString() + NW.getText()); break;
                            }
                            blueDot.performClick();  // Force Reset
                        }
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
