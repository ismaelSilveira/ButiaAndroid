package com.example.butiaandroid.main.vistas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by Rodrigo on 11/03/14.
 */

public class LayoutControl extends View {
    private ShapeDrawable mDrawable;
    private float radio;
    private float centroX;
    private float centroY;

    public float getRadio(){
        return radio;
    }

    public float getCentroX(){
        return centroX;
    }


    public float getCentroY(){
        return centroY;
    }



    private void init () {
        if (this.getWidth() > this.getHeight() ){
            radio = (this.getHeight() /2 ) -20;
        } else {
            radio = (this.getWidth() /2 ) -20;
        }

        int mOffset[] = new int[2];
        getLocationOnScreen( mOffset );

        centroX =  mOffset[0] +  (this.getWidth() /2 );
        centroY =  mOffset[1]+ (this.getHeight() /2);
    }



    public LayoutControl(Context context) {
        super(context);
        //init();

    }




    public LayoutControl(Context context, AttributeSet attrs) {
        super(context, attrs);
       // init();

    }

    public LayoutControl(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
       // init();

    }


    @Override
    protected  void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        init();
/*
        Paint paintBorde = new Paint();
        paintBorde.setColor(R.color.borde);
        canvas.drawCircle(getWidth()/2, getHeight()/2, radio+2, paintBorde);
*/
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        canvas.drawCircle(getWidth()/2, getHeight()/2, radio, paint);

    //    canvas.drawCircle(20,30,3,paint);

    }

}
