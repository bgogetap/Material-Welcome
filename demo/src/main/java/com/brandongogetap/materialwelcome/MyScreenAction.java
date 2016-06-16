package com.brandongogetap.materialwelcome;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.brandongogetap.library.base.action.ScreenAction;
import com.brandongogetap.library.base.action.ScreenActionListener.ScrollDirection;

import java.math.BigDecimal;

class MyScreenAction implements ScreenAction {

    private TextView statusTextView;

    @Override public void screenSettled(@NonNull View view) {
        statusTextView = null;
    }

    @Override public void screenCompletelyHidden(View view) {
        statusTextView = null;
    }

    @Override
    public void screenScrollingOut(@NonNull View view, float scrollPosition, @ScrollDirection int direction) {
        checkTextView(view);
        scrollPosition = scrollPosition * 100;
        BigDecimal bigDecimal = new BigDecimal(Float.toString(scrollPosition));
        bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
        statusTextView.setText("Scrolling Out: " + bigDecimal + "% showing");
    }

    @Override
    public void screenScrollingIn(@NonNull View view, float scrollPosition, @ScrollDirection int direction) {
        checkTextView(view);
        scrollPosition = scrollPosition * 100;
        BigDecimal bigDecimal = new BigDecimal(Float.toString(scrollPosition));
        bigDecimal = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP);
        statusTextView.setText("Scrolling In: " + bigDecimal + "% showing");

    }

    private void checkTextView(View parent) {
        if (statusTextView == null) {
            statusTextView = (TextView) parent.findViewById(R.id.tv_page2_status);
        }
    }
}
