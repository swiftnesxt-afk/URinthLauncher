package net.kdt.pojavlaunch.fragments;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

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
        ImageView artwork = view.findViewById(R.id.target_ui_artwork);
        loadTargetArtwork(artwork);

        View touch = view.findViewById(R.id.target_touch_layer);
        if (touch != null) {
            touch.setOnTouchListener((v, e) -> {
                if (e.getAction() != MotionEvent.ACTION_UP) return true;
                float x = e.getX() / v.getWidth();
                float y = e.getY() / v.getHeight();

                if (x >= 0.79f && x <= 0.99f && y >= 0.09f && y <= 0.27f) {
                    ExtraCore.setValue(ExtraConstants.SELECT_AUTH_METHOD, true);
                    return true;
                }

                if (x >= 0.14f && x <= 0.79f && y >= 0.37f && y <= 0.67f) {
                    ExtraCore.setValue(ExtraConstants.LAUNCH_GAME, true);
                    return true;
                }

                if (x >= 0.79f && x <= 0.99f && y >= 0.47f && y <= 0.62f) {
                    Tools.openURL(requireActivity(), getString(R.string.social_media_invite));
                    return true;
                }

                if (x <= 0.15f && y >= 0.66f) {
                    View button = requireActivity().findViewById(R.id.setting_button);
                    if (button != null) button.performClick();
                    return true;
                }
                return true;
            });
        }
    }

    private void loadTargetArtwork(ImageView artwork) {
        if (artwork == null) return;
        try (InputStream in = getResources().openRawResource(R.raw.modrinth_target_b64);
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];
            int count;
            while ((count = in.read(buffer)) != -1) {
                out.write(buffer, 0, count);
            }
            String encoded = new String(out.toByteArray(), StandardCharsets.US_ASCII);
            byte[] imageBytes = Base64.decode(encoded, Base64.NO_WRAP);
            artwork.setImageBitmap(BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length));
        } catch (Exception ignored) {
            // Keep the dark fallback background if the artwork cannot be decoded.
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ExtraCore.setValue(ExtraConstants.REFRESH_ACCOUNT_SPINNER, true);
    }
}