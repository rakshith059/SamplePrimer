package com.example.sample.sampleprimer;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sample.sampleprimer.utils.Utils;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.listeners.ItemRemovedListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SwipePlaceHolderView mSwipeView;
    private Context mContext;
    TextView tvProgress;

    ArrayList<Profile> profile;
    private ProgressBar pbProgressBar;
    private ImageView ivCheckMark;
    private ImageView ivHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSwipeView = (SwipePlaceHolderView) findViewById(R.id.activity_main_swipe_view);
        tvProgress = (TextView) findViewById(R.id.activity_main_progress_text);
        pbProgressBar = (ProgressBar) findViewById(R.id.activity_main_progress_bar);
        ivHome = (ImageView) findViewById(R.id.ic_home);
        ivCheckMark = (ImageView) findViewById(R.id.activity_main_iv_ok);
        mContext = getApplicationContext();
        profile = new ArrayList<>();
        getArrayList();
        int bottomMargin = Utils.dpToPx(250);
        Point windowSize = Utils.getDisplaySize(getWindowManager());

        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setHeightSwipeDistFactor(10)
                .setWidthSwipeDistFactor(5)
                .setDisplayViewCount(5)
                .setIsUndoEnabled(true)
                .setSwipeDecor(new SwipeDecor()
                        .setViewWidth(windowSize.x)
                        .setViewHeight(windowSize.y - bottomMargin)
                        .setViewGravity(Gravity.CENTER)
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f));
        ivHome.setEnabled(false);

        mSwipeView.addItemRemoveListener(new ItemRemovedListener() {
            @Override
            public void onItemRemoved(int count) {
                pbProgressBar.setMax(profile.size());
                pbProgressBar.setProgress(profile.size() - count);

//                Double percentage = ((double) count / profile.size()) * 100;
//                Double totalPercentage = ((double) 100) - percentage;
//                tvProgress.setText(String.valueOf(totalPercentage) + "%");

                if (count == 1) {
                    mSwipeView.disableTouchSwipe();
                    ivCheckMark.setVisibility(View.VISIBLE);
                }
                if (count == 0) {
                    ivHome.setEnabled(true);
                    ivHome.setClickable(true);
                }
            }
        });

        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvProgress.setText("0%");
                mSwipeView.enableTouchSwipe();
                addAllViews();
            }
        });

        ivCheckMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSwipeView.doSwipe(true);
                ivCheckMark.setVisibility(View.GONE);
            }
        });

        addAllViews();

    }

    private void addAllViews() {
        for (int i = 0; i < profile.size(); i++) {
            mSwipeView.addView(new SwipableCard(mContext, profile.get(i), mSwipeView));
        }
    }

    private void getArrayList() {
        profile.add(new Profile(getResources().getString(R.string.dummy_text), "R.mipmap.ic_launcher", 20, "location1"));
        profile.add(new Profile(getResources().getString(R.string.dummy_text), "R.mipmap.ic_launcher", 21, "location2"));
        profile.add(new Profile(getResources().getString(R.string.dummy_text), "R.mipmap.ic_launcher", 22, "location3"));
        profile.add(new Profile(getResources().getString(R.string.dummy_text), "R.mipmap.ic_launcher", 20, "location4"));
        profile.add(new Profile(getResources().getString(R.string.dummy_text), "R.mipmap.ic_launcher", 21, "location5"));
        profile.add(new Profile(getResources().getString(R.string.dummy_text), "R.mipmap.ic_launcher", 19, "location6"));
        profile.add(new Profile(getResources().getString(R.string.dummy_text), "R.mipmap.ic_launcher", 22, "location3"));
        profile.add(new Profile(getResources().getString(R.string.dummy_text), "R.mipmap.ic_launcher", 20, "location4"));
        profile.add(new Profile(getResources().getString(R.string.dummy_text), "R.mipmap.ic_launcher", 21, "location5"));
        profile.add(new Profile(getResources().getString(R.string.dummy_text), "R.mipmap.ic_launcher", 19, "location6"));
    }
}

