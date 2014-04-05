package br.com.drfacil.android.helpers;

import android.graphics.Color;

import static com.google.common.base.Preconditions.checkArgument;

public final class ColorHelper {

    // Have a better name? Let me know!
    public static int interpolate(int colorA, int colorB, double coef) {
        checkArgument(0 <= coef && coef <= 1, "coef must be between 0 and 1 (inclusive).");
        int alpha = interpolate(colorA, colorB, coef, 0xFF000000, 6 * 4);
        int red = interpolate(colorA, colorB, coef, 0x00FF0000, 4 * 4);
        int green = interpolate(colorA, colorB, coef, 0x0000FF00, 2 * 4);
        int blue = interpolate(colorA, colorB, coef, 0x000000FF, 0);
        return Color.argb(alpha, red, green, blue);
    }

    private static int interpolate(int a, int b, double coef, int mask, int n) {
        return (int) (((a & mask) >>> n) * (1 - coef) + ((b & mask) >>> n) * coef);
    }

    // Prevents instantiation
    private ColorHelper() {
        throw new AssertionError("Cannot instantiate object from " + this.getClass());
    }
}
