package edu.ucla.hci.bakeoffone;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.Math;
import java.util.concurrent.CopyOnWriteArrayList;

public class MainActivity extends AppCompatActivity {
    int layoutWidth, layoutHeight, centerX, centerY;   // Center Coordinate of touchArea
    int leftC, topC, bottomC, rightC;
    int biasX, biasY;
    int limitR = 120;
    int MODE = -1;
    int SEL = 0;
    int lastSEL = 0;
    int CAPSL = 1;
    String withdrawBuffer = "";
    TextView Zero, NE, Three, SE, Six, SW, Nine, NW;
    TextView textView;
    ImageView redDot, backSpace, withDraw, clearButton, capsLock;
    ImageView touchArea;

    //Calculate the current clockwise-view angle
    double angleCalculator(int cX, int cY, float X, float Y){
        double radius = Math.atan2(X-cX, cY-Y);
        return radius;
    }

    // Selection made, generate actual input
    int selectionDetect(double ang){
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
        String[] cc = new String[]{"X","X","X","X","X","X","X","↩"};
        if (CAPSL == 1) {
            switch (selection) {
                case 1:
                    cc = new String[]{"A", "B", "C", "D", "E", "F", "⌴", "↩"};
                    break;
                case 2:
                    cc = new String[]{"/", "|", "$", ">", "<", "_", "&", "↩"};
                    break;
                case 3:
                    cc = new String[]{"G", "H", "I", "J", "K", "L", "M", "↩"};
                    break;
                case 4:
                    cc = new String[]{"1", "2", "3", "4", "5", "6", "#", "↩"};
                    break;
                case 5:
                    cc = new String[]{"N", "O", "P", "Q", "R", "S", "⌴", "↩"};
                    break;
                case 6:
                    cc = new String[]{"7", "8", "9", "0", ",", ".", "*", "↩"};
                    break;
                case 7:
                    cc = new String[]{"T", "U", "V", "W", "X", "Y", "Z", "↩"};
                    break;
                case 8:
                    cc = new String[]{"?", "!", "-", "~", "&", "(", ")", "↩"};
                    break;
                case 0:
                    Log.i("TAG", "iniLetters: ERROR Parameter!");
            }
        }
        else {
            switch (selection) {
                case 1:
                    cc = new String[]{"a", "b", "c", "d", "e", "f", "⌴", "↩"};
                    break;
                case 2:
                    cc = new String[]{"/", "|", "$", ">", "<", "_", "&", "↩"};
                    break;
                case 3:
                    cc = new String[]{"g", "h", "i", "j", "k", "l", "m", "↩"};
                    break;
                case 4:
                    cc = new String[]{"1", "2", "3", "4", "5", "6", "#", "↩"};
                    break;
                case 5:
                    cc = new String[]{"n", "o", "p", "q", "r", "s", "⌴", "↩"};
                    break;
                case 6:
                    cc = new String[]{"7", "8", "9", "0", ",", ".", "*", "↩"};
                    break;
                case 7:
                    cc = new String[]{"t", "u", "v", "w", "x", "y", "z", "↩"};
                    break;
                case 8:
                    cc = new String[]{"?", "!", "-", "~", "&", "(", ")", "↩"};
                break;
                case 0:
                    Log.i("TAG", "iniLetters: ERROR Parameter!");
            }
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
        String[] cc;
        if (CAPSL == 1){
            cc = new String[]{"A-F","$|","G-M","#","N-S","*","T-Z","!?"};
        }
        else {
            cc = new String[]{"a-f","$|","g-m","#","n-s","*","t-z","!?"};
        }

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

    void centering(){
        SEL = 0;
        redDot.layout(leftC, topC, rightC, bottomC);
        refreshDisplay(SEL);
    }

    void backUp(){
        MODE = 0;
        centering();
        restoreLetters();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        touchArea = (ImageView) findViewById(R.id.touchArea);
        layoutWidth = touchArea.getWidth();
        layoutHeight = touchArea.getHeight();
        centerX = layoutWidth / 2;
        centerY = layoutHeight / 2;
        biasX = redDot.getWidth() / 2;
        biasY = redDot.getHeight() / 2;
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
        redDot = (ImageView) findViewById(R.id.redDot);
        backSpace = (ImageView) findViewById(R.id.backspace);
        withDraw = (ImageView) findViewById(R.id.withdraw);
        clearButton = (ImageView) findViewById(R.id.clear);
        capsLock = (ImageView) findViewById(R.id.capslock);

        // Backspace
        backSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buffer = textView.getText().toString();
                withdrawBuffer = buffer;
                textView.setText(buffer.substring(0, buffer.length() - 1));
                backUp();
            }
        });

        // Withdraw
        withDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!withdrawBuffer.isEmpty()){
                    textView.setText(withdrawBuffer);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Withdraw Fail：Empty Buffer.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Clear
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                withdrawBuffer = textView.getText().toString();
                textView.setText("");
            }
        });

        // CapsLock
        capsLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CAPSL = 1 - CAPSL;

                if (CAPSL == 0) {
                    Toast.makeText(getApplicationContext(), "Caps Lock On",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Caps Lock Off",Toast.LENGTH_SHORT).show();
                }

                if (MODE == 0) {
                    restoreLetters();
                }
                else if (MODE == 1) {
                    iniLetters(lastSEL);
                }
                Toast.makeText(getApplicationContext(), "Caps Lock On",Toast.LENGTH_SHORT).show();
            }
        });

        // Joystick
        redDot.setOnTouchListener(new View.OnTouchListener(){
            int lastX, lastY; // Save last location
            float currX, currY;   // Center Coordinate of current joystick view
            int newX, newY; // Orbit-Fix Value
            int dx, dy; // Movement Offset Value

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int act = event.getAction();
                switch(act){
                    case MotionEvent.ACTION_DOWN:
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE: //
                        // Dynamic Location Calculator
                        dx = (int) event.getRawX() - lastX;
                        dy = (int) event.getRawY() - lastY;
                        currX = v.getX() + biasX + dx;
                        currY = v.getY() + biasY + dy;
                        double ang = angleCalculator(centerX, centerY, currX, currY);
                        SEL = selectionDetect(Math.toDegrees(ang));
                        newX = (int) (centerX + limitR * Math.sin(ang));
                        newY = (int) (centerY - limitR * Math.cos(ang));

                        int left = newX - biasX;
                        int right = newX + biasX;
                        int top = newY - biasY;
                        int bottom = newY + biasY;
                        v.layout(left, top, right, bottom); // Draw new button at new location
                        refreshDisplay(SEL);
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP: // Up
                        if ((MODE == 0)&&(SEL != 0)){   // Enter Stage 2
                                MODE = 1;
                                lastSEL = SEL;
                                iniLetters(SEL);
                                centering();
                        }
                        else if (MODE == 1) {  // Type Char
                            switch (SEL) {
                                case 1: textView.setText(textView.getText().toString() + Zero.getText()); break;
                                case 2: textView.setText(textView.getText().toString() + NE.getText()); break;
                                case 3: textView.setText(textView.getText().toString() + Three.getText()); break;
                                case 4: textView.setText(textView.getText().toString() + SE.getText()); break;
                                case 5: textView.setText(textView.getText().toString() + Six.getText()); break;
                                case 6: textView.setText(textView.getText().toString() + SW.getText()); break;
                                case 7:{
                                    if (Nine.getText().equals("⌴")) {
                                        textView.setText(textView.getText().toString() + " "); break;
                                    }
                                    else {
                                        textView.setText(textView.getText().toString() + Nine.getText()); break;
                                    }
                                }
                                case 8: backUp(); break;
                            }
                            centering();
                        }
                }
                return true;
            }
        });
    }
}
