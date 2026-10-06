package net.kdt.pojavlaunch.fragments;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import git.artdeell.mojo.R;
import net.kdt.pojavlaunch.Tools;
import net.kdt.pojavlaunch.extra.ExtraConstants;
import net.kdt.pojavlaunch.extra.ExtraCore;

public class MainMenuFragment extends Fragment {
    public static final String TAG = "MainMenuFragment";

    public MainMenuFragment() {
        super(R.layout.fragment_launcher);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        View touch = view.findViewById(R.id.target_touch_layer);
        if (touch != null) {
            touch.setOnTouchListener((v, e) -> {
                if (e.getAction() != MotionEvent.ACTION_UP) return true;
                float x = e.getX() / v.getWidth();
                float y = e.getY() / v.getHeight();

                // Add Account card on the right.
                if (x >= 0.79f && x <= 0.99f && y >= 0.09f && y <= 0.27f) {
                    ExtraCore.setValue(ExtraConstants.SELECT_AUTH_METHOD, true);
                    return true;
                }

                // Three instance Play buttons.
                if (x >= 0.14f && x <= 0.79f && y >= 0.37f && y <= 0.67f) {
                    ExtraCore.setValue(ExtraConstants.LAUNCH_GAME, true);
                    return true;
                }

                // Discord card.
                if (x >= 0.79f && x <= 0.99f && y >= 0.47f && y <= 0.62f) {
                    Tools.openURL(requireActivity(), getString(R.string.social_media_invite));
                    return true;
                }

                // Settings.
                if (x <= 0.15f && y >= 0.66f) {
                    View button = requireActivity().findViewById(R.id.setting_button);
                    if (button != null) button.performClick();
                    return true;
                }
                return true;
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ExtraCore.setValue(ExtraConstants.REFRESH_ACCOUNT_SPINNER, true);
    }
}
