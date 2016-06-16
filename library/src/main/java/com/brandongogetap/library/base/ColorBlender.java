package com.brandongogetap.library.base;

import android.graphics.Color;

final class ColorBlender {

    static int blendColors(int from, int to, float ratio) {
        float inverseRatio = 1f - ratio;

        final float red = Color.red(to) * ratio + Color.red(from) * inverseRatio;
        final float green = Color.green(to) * ratio + Color.green(from) * inverseRatio;
        final float blue = Color.blue(to) * ratio + Color.blue(from) * inverseRatio;

        return Color.rgb((int) red, (int) green, (int) blue);
    }
}
