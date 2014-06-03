package com.example.butiaandroid.main.vistas;

/**
 * Created by Rodrigo on 02/06/14.
 */

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.example.butiaandroid.main.R;

/**
 * Created by Rodrigo on 02/06/14.
 */
public class SimpleViewAnimator extends FrameLayout
{
    private Animation inAnimation;
    private Animation outAnimation;

    public SimpleViewAnimator(Context context)
    {
        super(context);
        initAnimations(context.getApplicationContext());
    }

    public void setInAnimation(Animation inAnimation)
    {

        this.inAnimation = inAnimation;
    }

    private void initAnimations(Context context)
    {/*
        inAnimation = (AnimationSet) AnimationUtils.loadAnimation(context, R.anim.in_animation);
        outAnimation = (Animation) AnimationUtils.loadAnimation(context, R.anim.out_animation);*/
    }



    public void setOutAnimation(Animation outAnimation)
    {
        this.outAnimation = outAnimation;
    }

    @Override
    public void setVisibility(int visibility)
    {
     /*   if (getVisibility() != visibility)
        {
            if (visibility == VISIBLE)
            {
                if (inAnimation != null) startAnimation(inAnimation);
            }
            else if ((visibility == INVISIBLE) || (visibility == GONE))
            {
                if (outAnimation != null) startAnimation(outAnimation);
            }
        }
*/
        super.setVisibility(visibility);
    }
}