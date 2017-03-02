package com.example.sample.sampleprimer;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;

/**
 * Created by rakshith on 2/18/17.
 */
@Layout(R.layout.swipable_card_view)
public class SwipableCard {

    @com.mindorks.placeholderview.annotations.View(R.id.swipable_card_view_iv_profile_image)
    private ImageView profileImageView;

    @com.mindorks.placeholderview.annotations.View(R.id.swipable_card_view_tv_description)
    private TextView nameAgeTxt;

    @com.mindorks.placeholderview.annotations.View(R.id.activity_main_iv_ok)
    private ImageView ivOk;

    private Profile mProfile;
    private Context mContext;
    private SwipePlaceHolderView mSwipeView;
    int itemCount = -1;

    public SwipableCard(Context context, Profile profile, SwipePlaceHolderView swipeView) {
        mContext = context;
        mProfile = profile;
        mSwipeView = swipeView;
    }

    @Resolve
    private void onResolved() {
        Glide.with(mContext).load(mProfile.getImageUrl()).error(R.drawable.ic_cricket_splash).into(profileImageView);
        nameAgeTxt.setText(mProfile.getName() + ", " + mProfile.getAge());
    }

    @Click(R.id.swipable_card_view_iv_profile_image)
    private void onClick() {
        Log.d("EVENT", "profileImageView click");
        mSwipeView.addView(this);
    }

    @SwipeOut
    private void onSwipedOut() {
        Log.d("EVENT", "onSwipedOut");
//        mSwipeView.addView(this);
    }

    @SwipeCancelState
    private void onSwipeCancelState() {
        Log.d("EVENT", "onSwipeCancelState");
    }

    @SwipeIn
    private void onSwipeIn() {
        Log.d("EVENT", "onSwipedIn");
    }

    @SwipeInState
    private void onSwipeInState() {
        Log.d("EVENT", "onSwipeInState");
    }

    @SwipeOutState
    private void onSwipeOutState() {
        Log.d("EVENT", "onSwipeOutState");
    }
}

