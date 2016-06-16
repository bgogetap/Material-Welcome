package com.brandongogetap.library.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.brandongogetap.library.R;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public abstract class WelcomeActivity extends AppCompatActivity implements WelcomeScreenContainer {

    private List<View> indicatorList;
    private View doneButton;
    private View nextButton;
    private View skipButton;
    private View selectedIndicator;
    private View parentView;
    private LinearLayout indicatorContainer;
    private ViewPager viewPager;
    private int screenCount;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_welcome_screen);
        viewPager = (ViewPager) findViewById(R.id.vp_welcome_pager);
        indicatorContainer = (LinearLayout) findViewById(R.id.ll_indicator_container);
        selectedIndicator = findViewById(R.id.iv_selected_circle);
        parentView = findViewById(R.id.welcome_parent_view);
        doneButton = findViewById(R.id.btn_done);
        nextButton = findViewById(R.id.btn_next);
        skipButton = findViewById(R.id.btn_skip);

        List<WelcomeScreen> screens = welcomeScreens();
        screenCount = screens.size();
        initializeAndPositionIndicators();

        final int lastPostion = savedInstanceState != null ?
                savedInstanceState.getInt("pager_position") : 0;
        WelcomeScreenCoordinator coordinator =
                new WelcomeScreenCoordinator(screens, this, getApplicationContext(), lastPostion);

        WelcomeAdapter adapter = new WelcomeAdapter(screens);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(coordinator);
        coordinator.updateWelcomeState(viewPager.getCurrentItem());
        indicatorContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override public void onGlobalLayout() {
                indicatorContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                selectedIndicator.setX(
                        indicatorContainer.getX() + indicatorList.get(lastPostion).getX());
                selectedIndicator.setY(
                        indicatorContainer.getY() + indicatorList.get(lastPostion).getY());
                selectedIndicator.setVisibility(View.VISIBLE);
            }
        });

        setUpClickListeners();
    }

    private void setUpClickListeners() {
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
            }
        });

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                viewPager.setCurrentItem(screenCount - 1, true);
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startActivity(welcomeFinishedIntent());
                finish();
            }
        });
    }

    private void initializeAndPositionIndicators() {
        indicatorList = new ArrayList<>(screenCount);
        LayoutParams params = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        int margin = (int) getResources().getDimension(R.dimen.indicator_margin);
        params.setMargins(margin, margin, margin, margin);
        params.gravity = Gravity.CENTER_VERTICAL;
        for (int i = 0; i < screenCount; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.pager_circle));
            imageView.setLayoutParams(params);
            indicatorList.add(imageView);
            indicatorContainer.addView(imageView);
        }
    }

    @Override
    public void moveSelectedIndicator(int position) {
        float currentIndicatorX = indicatorList.get(position).getX();
        float newPositionX = currentIndicatorX + indicatorContainer.getX();
        if (newPositionX == 0) {
            // This can happen on config changes. In this case, we have already positioned the
            // selected indicator based on saved state.
            return;
        }
        selectedIndicator.animate().x(newPositionX).setDuration(300).start();
    }

    @Override public void updateBackgroundColor(int color) {
        parentView.setBackgroundColor(color);
    }

    @Override public void updateWelcomeState(WelcomeState state) {
        doneButton.setVisibility(state.doneVisibility);
        nextButton.setVisibility(state.nextVisibility);
        skipButton.setVisibility(state.skipVisibility);
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("pager_position", viewPager.getCurrentItem());
    }

    /**
     * Provide a list of screens in the order they should be presented
     *
     * @return List of ordered screens
     */
    protected abstract List<WelcomeScreen> welcomeScreens();

    /**
     * Provide the Intent that will be called with {@link Activity#startActivity(Intent)} when the
     * 'Done' button is clicked.
     *
     * @return Intent to be invoked
     */
    protected abstract Intent welcomeFinishedIntent();
}
