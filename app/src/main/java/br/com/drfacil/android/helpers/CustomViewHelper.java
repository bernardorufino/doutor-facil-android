package br.com.drfacil.android.helpers;


import android.view.View;
import android.view.ViewManager;
import android.view.ViewParent;

import static com.google.common.base.Preconditions.checkArgument;

public class CustomViewHelper {

    public static boolean tryMakeOrphan(View view) {
        ViewParent parent = view.getParent();
        if (parent instanceof ViewManager) {
            ((ViewManager) parent).removeView(view);
            return true;
        }
        return false;
    }

    public static <T extends View> T makeOrphan(T view) {
        checkArgument(tryMakeOrphan(view), "Can't make view orphan.");
        return view;
    }

    // Prevents instantiation
    private CustomViewHelper() {
        throw new AssertionError("Cannot instantiate object from " + this.getClass());
    }
}
