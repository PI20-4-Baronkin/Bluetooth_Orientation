package com.example.bluetoothadapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

public class MyView  extends androidx.appcompat.widget.AppCompatImageView {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    static float x1 = 0, y1 = 0;
    static int count = 0;
    int delHeight = 5, delWidth = 13;
    int unitH, unitW;
    Field[] fields = new Field[1000];
    int iterator = 0;
    boolean flag = true;
    int counter = 5;

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        unitH = canvas.getHeight()/delHeight;
        unitW = canvas.getWidth()/delWidth;
        canvas.save();
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.YELLOW);
        paint.setAntiAlias(true);

        if(counter == 0) {
            for (float i = 0; i < canvas.getWidth() + 2 * unitH; i += unitH) {
                for (float j = 0; j < canvas.getHeight() + 2 * unitH; j += unitH) {
                    RectF oval1 = new RectF(0 + i, 0 + j, unitH + i,
                            unitH + j);
                    paint.setColor(Color.YELLOW);
                    canvas.drawOval(oval1, paint);
                }
            }
        }
        if(counter == 1){
            for (float i = 0; i < canvas.getWidth() + 2 * unitH; i += unitH) {
                for (float j = 0; j < canvas.getHeight() + 2 * unitH; j += unitH) {
                    RectF oval1 = new RectF(0 + i, 0 + j, unitH + i,
                            unitH + j);
                    paint.setColor(Color.YELLOW);
                    canvas.drawOval(oval1, paint);
                }
            }
            for (float i = -unitH; i < canvas.getWidth()+2*unitH; i += unitH) {
                for (float j = -unitH; j < canvas.getHeight() +2*unitH; j += unitH) {
                    RectF oval2 = new RectF((float) 0.5 * unitH + i, (float) 0.5 * unitH + j,
                            (float) 1.5 * unitH + i, (float) 1.5 * unitH + j);
                    paint.setColor(Color.GREEN);
                    canvas.drawOval(oval2, paint);
                }
            }
        }

        for (float i = 0; i < canvas.getWidth()+ 2*unitH ; i+= unitH) {
            for (float j = 0; j < canvas.getHeight() + 2*unitH; j+= unitH) {
                paint.setColor(Color.RED);
                canvas.drawPoint(unitH/2+i, unitH/2 +j, paint);
            }
        }
        for (float i = -unitH/2; i < canvas.getWidth() + 2*unitH ; i+= unitH) {
            for (float j = -unitH/2; j < canvas.getHeight() + 2*unitH; j+= unitH) {
                paint.setColor(Color.RED);
                canvas.drawPoint(-unitH/2+i, -unitH/2 +j, paint);
            }
        }
        canvas.restore();
    }
    public void clearZone(){
        counter = 5;
        invalidate();
    }

    public void halfZone(){
        counter = 0;
        invalidate();
    }

    public void fullZone(){
        counter = 1;
        invalidate();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        invalidate();
        return super.onTouchEvent(event);
    }
}
