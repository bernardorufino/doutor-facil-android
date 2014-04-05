package br.com.drfacil.android.helpers;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.LinearInterpolator;

public final class AnimationHelper {

    public static ViewPropertyAnimator fadeIn(final View view, long duration) {
        return fade(view, 0, 1, View.INVISIBLE, View.VISIBLE, duration);
    }

    public static ViewPropertyAnimator fadeOut(final View view, long duration) {
        return fade(view, 1, 0, View.VISIBLE, View.INVISIBLE, duration);
    }

    private static ViewPropertyAnimator fade(final View view, float initialAlpha, float finalAlpha,
                                             int initialVisibility, final int finalVisibility, long duration) {
        view.setAlpha(initialAlpha);
        view.setVisibility(initialVisibility);
        return view.animate().alpha(finalAlpha).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(finalVisibility);
            }
        }).setDuration(duration);
    }

    public static ObjectAnimator forDrawable(Drawable drawable) {
        ObjectAnimator animator = ObjectAnimator.ofInt(drawable, "level", drawable.getLevel(), 10000);
        animator.setInterpolator(new LinearInterpolator());
        return animator;
    }

    public static ObjectAnimator loopDrawable(Drawable drawable) {
        ObjectAnimator animator = forDrawable(drawable);
        animator.setRepeatCount(ObjectAnimator.INFINITE);
        return animator;
    }

    // Prevents instantiation
    private AnimationHelper() {
        throw new AssertionError("Cannot instantiate object from " + this.getClass());
    }
}
