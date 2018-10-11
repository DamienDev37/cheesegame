package com.example.home.cheesegame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

public class Plateau extends View implements View.OnTouchListener {
    private boolean mBooleanIsPressed = false;

    int sizew=0;
    int sizeh=0;
    int cpt = 0;
    int x = 0;
    int y = 0;
    int dx = 0;
    int dy = 0;
    int tabx=40;
    int taby=40;
    int predx;
    int predy;
    int deck[] = new int[40];
    int plateau[][] = new int[80][80];
    int card=1;
    int cardvalue = 1;
    public Plateau(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnTouchListener(this);
        for (int i = 0;i<80;i++){
            for (int j = 0;j<80;j++){
                plateau[i][j]=0;
            }
        }
        for (int i =0;i<40;i++){
            if(i<20){deck[i]= 1;}
            if(i>=20 && i < 27){deck[i]= 2;}
            if(i>=27 && i < 31){deck[i]= 3;}
            if(i>=31){deck[i]= 4;}
        }

    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(sizew==0){sizew=this.getWidth();}
        if(sizeh==0){sizeh=this.getHeight();}
        x=sizew/2;
        y=sizeh/2;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        Resources res = getResources();
        plateau[tabx][taby] = card;
        if(cpt==0){
            Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.cat);
            canvas.drawBitmap(bitmap, sizew, sizeh, paint);
            Log.d("Init","first card");
        }else {
            for (int i = 0; i < 80; i++) {
                for (int j = 0; j < 80; j++) {
                    x = sizew - (2000 - (i * 50));
                    y = sizeh - (2000 - (j * 50));
                    if (plateau[i][j] == 1) {
                        cardvalue = R.drawable.mouse;
                    }
                    if (plateau[i][j] == 2) {
                        cardvalue = R.drawable.cat;
                    }
                    if (plateau[i][j] == 3) {
                        cardvalue = R.drawable.cheese;
                    }
                    if (plateau[i][j] == 4) {
                        cardvalue = R.drawable.mousetrap;
                    }
                    if (plateau[i][j] != 0) {
                        Bitmap bitmap = BitmapFactory.decodeResource(res, cardvalue);
                        canvas.drawBitmap(bitmap, x, y, paint);
                    }
                }
            }
        }
        canvas.translate(predx, predy);
        if(cpt==20){
            editText.setError("Your error message")
        }
    }
    private final Handler handler = new Handler();
    private final Runnable runnable = new Runnable() {
        public void run() {
            Log.d(" HANDLER" , "log LONG TOUCH");
        }
    };
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            Log.d(" TOUCH", "DOWN");
            // Execute your Runnable after 1000 milliseconds = 1 second.
            handler.postDelayed(runnable, 1000);
            mBooleanIsPressed = true;
            predx = x;
            predy = y;
            card = deck[cpt];
            cpt = cpt +1;
            Log.d(" cpt", "mount"+cpt);
        }

        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            if (mBooleanIsPressed) {
                mBooleanIsPressed = false;
                handler.removeCallbacks(runnable);


            }
            Log.d(" TOUCH", "UP");
        }
        if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
            //anything happening with event here is the X Y of the raw screen event, relative to the view.
            Log.d(" TOUCH", "MOVE");
            this.dx = predx - x;
            this.dy = predy - y;


        }
        this.invalidate();
        return true;
    }
}
