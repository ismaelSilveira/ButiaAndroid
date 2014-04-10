package com.example.butiaandroid.main.vistas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by Rodrigo on 11/03/14.
 */

public class LayoutControl extends RelativeLayout {
    private int margen;
    private ShapeDrawable mDrawable;
    private float radio;
    private float centroX;
    private float centroY;
    private int locationX;
    private int locationY;


    String colorBorde="#CCD8D8D8";
    String colorSombra="#80D8D8D8";
    String colorCirculo ="#FFFFFFFF";


    public void setTransparente (boolean b){
        if (b){
            colorBorde="#CCD8D8D8";
            colorSombra="#80D8D8D8";
            colorCirculo ="#80FFFFFF";
        }else {
            colorBorde="#CCD8D8D8";
            colorSombra="#80D8D8D8";
            colorCirculo ="#FFFFFFFF";
        }

    }



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
        if (this.getWidth() > this.getHeight()){
            margen = this.getHeight() / 6;
            radio = (this.getHeight() / 2) - margen;
        }
        else {
            margen = this.getWidth() / 6;
            radio = (this.getWidth() / 2) - margen;
        }

        int mOffset[] = new int[2];
        getLocationOnScreen(mOffset);
        locationX =  mOffset[0];
        locationY =  mOffset[1];

        centroX = locationX + (this.getWidth() / 2);
        centroY = locationY + (this.getHeight() / 2);
    }

    public int getLocationX() {
       return locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public LayoutControl(Context context) {
        super(context);
        setWillNotDraw(false);
        //init();
    }

    public LayoutControl(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
       // init();
    }

    public LayoutControl(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setWillNotDraw(false);
       // init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        init();
        //sombra
        Paint paintBorde2 = new Paint();
        paintBorde2.setColor(Color.parseColor(colorSombra));
        canvas.drawCircle(getWidth()/2, getHeight()/2, radio+50, paintBorde2);
        //borde
        Paint paintBorde = new Paint();
        paintBorde.setColor(Color.parseColor(colorBorde));
        canvas.drawCircle(getWidth()/2, getHeight()/2, radio+2, paintBorde);
        //circulo
        Paint paint = new Paint();
        paint.setColor(Color.parseColor(colorCirculo));
        canvas.drawCircle(getWidth()/2, getHeight()/2, radio, paint);

    //    canvas.drawCircle(20,30,3,paint);
    }
}