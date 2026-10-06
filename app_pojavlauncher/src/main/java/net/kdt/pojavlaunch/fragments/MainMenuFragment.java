package net.kdt.pojavlaunch.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import git.artdeell.mojo.R;
import net.kdt.pojavlaunch.Tools;
import net.kdt.pojavlaunch.extra.ExtraConstants;
import net.kdt.pojavlaunch.extra.ExtraCore;
import net.kdt.pojavlaunch.ui.TargetUiAsset;

public class MainMenuFragment extends Fragment {
    public static final String TAG = "MainMenuFragment";

    public MainMenuFragment() {
        super(R.layout.fragment_launcher);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ImageView artwork = view.findViewById(R.id.target_ui_artwork);

        byte[] bytes = Base64.decode(TargetUiAsset.BASE64_WEBP, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        artwork.setImageBitmap(bitmap);

        View touchLayer = view.findViewById(R.id.target_touch_layer);
        touchLayer.setOnTouchListener((v, event) -> {
            if (event.getAction() != MotionEvent.ACTION_UP) return true;

            float x = event.getX() / v.getWidth();
            float y = event.getY() / v.getHeight();

            if (x >= 0.79f && x <= 0.99f && y >= 0.09f && y <= 0.26f) {
                ExtraCore.setValue(ExtraConstants.SELECT_AUTH_METHOD, true);
                return true;
            }

            if (y >= 0.39f && y <= 0.66f && x >= 0.15f && x <= 0.78f) {
                ExtraCore.setValue(ExtraConstants.LAUNCH_GAME, true);
                return true;
            }

            if (x >= 0.79f && x <= 0.99f && y >= 0.48f && y <= 0.60f) {
                Tools.openURL(requireActivity(), getString(R.string.social_media_invite));
                return true;
            }

            if (x <= 0.07f && y <= 0.10f) {
                openSettings();
                return true;
            }

            if (x <= 0.14f && y >= 0.66f && y <= 0.76f) {
                openSettings();
                return true;
            }

            return true;
        });
    }

    private void openSettings() {
        View settingButton = requireActivity().findViewById(R.id.setting_button);
        if (settingButton != null) settingButton.performClick();
    }

    @Override
    public void onResume() {
        super.onResume();
        ExtraCore.setValue(ExtraConstants.REFRESH_ACCOUNT_SPINNER, true);
    }
}