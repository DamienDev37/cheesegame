package com.example.home.cheesegame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class Plateau extends View implements View.OnTouchListener {
    private boolean mBooleanIsPressed = false;

    int dx = 0;
    int dy = 0;
    int predx;
    int predy;

    int plateau[][] = new int[80][80];
    int card=1;

    ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();
    public Plateau(Context context, AttributeSet attrs) {
        super(context, attrs);
        for (int i = 0;i<40;i++){
            for (int j = 0;j<40;j++){
                plateau[i][j]=0;
            }
        }

        this.setOnTouchListener(this);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        Resources res = getResources();
        int cardvalue=0;
        if(card == 1){cardvalue=R.drawable.cat;}
        if(card == 2){cardvalue=R.drawable.mouse;}
        if(card == 3){cardvalue=R.drawable.cheese;}
        if(card == 4){cardvalue=R.drawable.mousetrap;}

        Bitmap bitmap = BitmapFactory.decodeResource(res, cardvalue);
        plateau[40][40] = card;

        canvas.drawBitmap(bitmap, 1000, 1000, paint);

    }
    private final Handler handler = new Handler();
    private final Runnable runnable = new Runnable() {
        public void run() {
            Log.d(" HANDLER" , "log LONG TOUCH");
        }
    };

    public void fromGraphicToBoard(int x, int y) {

    }
    public void fromBoardToBoard(int X, int Y){

    }
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

            for (int i = 0;i<40;i++){
                for (int j = 0;j<40;j++){
                    if(plateau[i][j]!=0){
                        Paint paint = new Paint();
                        paint.setAntiAlias(true);
                        paint.setFilterBitmap(true);
                        paint.setDither(true);
                        Canvas canvas = new Canvas();
                        Resources res = getResources();
                        Bitmap bitmap = BitmapFactory.decodeResource(res, plateau[i][i]);
                        canvas.drawBitmap(bitmap, i*50, j*50, paint);
                    }
                }
            }

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
