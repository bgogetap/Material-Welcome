package com.brandongogetap.materialwelcome;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.brandongogetap.library.base.WelcomeActivity;
import com.brandongogetap.library.base.WelcomeScreen;

import java.util.ArrayList;
import java.util.List;

public class MyWelcomeActivity extends WelcomeActivity {

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override protected List<WelcomeScreen> welcomeScreens() {
        List<WelcomeScreen> screens = new ArrayList<>();
        screens.add(new WelcomeScreen.Builder(R.layout.welcome_screen_1)
                .backgroundColorResource(R.color.colorAccent)
                .onViewClicked(R.id.btn_first, new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        Toast.makeText(MyWelcomeActivity.this, "First Clicked!", Toast.LENGTH_SHORT)
                                .show();
                    }
                })
                .build());
        screens.add(new WelcomeScreen.Builder(R.layout.welcome_screen_2)
                .backgroundColor(Color.BLUE)
                .screenAction(new MyScreenAction())
                .build());
        screens.add(new WelcomeScreen.Builder(R.layout.welcome_screen_3)
                .backgroundColorResource(R.color.colorAccent)
                .onViewClicked(R.id.btn_third, new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        Toast.makeText(MyWelcomeActivity.this, "Third Clicked!", Toast.LENGTH_SHORT)
                                .show();
                    }
                })
                .build());

        return screens;
    }

    @Override protected Intent welcomeFinishedIntent() {
        return new Intent(this, MainActivity.class);
    }
}
