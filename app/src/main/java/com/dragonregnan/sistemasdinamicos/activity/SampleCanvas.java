package com.dragonregnan.sistemasdinamicos.activity;

/**
 * Created by laura on 07/12/2015.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;


public class SampleCanvas extends View {
    Paint paint = new Paint();

    public SampleCanvas(Context context) {
        super(context);
    }

    @Override
    public void onDraw(Canvas canvas) {
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(3);

        canvas.drawRect(230, 230, 440, 400, paint);
        paint.setStrokeWidth(0);
        paint.setColor(Color.CYAN);
        canvas.drawRect(233, 240, 437, 397, paint );
        paint.setColor(Color.YELLOW);
        canvas.drawRect(233, 233, 437, 340, paint );

    }
}
