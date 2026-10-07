package net.kdt.pojavlaunch.fragments;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

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
        loadTargetArtwork(view);

        View addAccount = view.findViewById(R.id.add_account_button);
        if (addAccount != null) {
            addAccount.setOnClickListener(v ->
                    ExtraCore.setValue(ExtraConstants.SELECT_AUTH_METHOD, true));
        }

        View checkUpdates = view.findViewById(R.id.check_updates_button);
        if (checkUpdates != null) {
            checkUpdates.setOnClickListener(v ->
                    Toast.makeText(requireContext(), "Checking for updates…", Toast.LENGTH_SHORT).show());
        }

        View ultra = view.findViewById(R.id.urinth_ultra_switch);
        if (ultra != null) {
            ultra.setOnClickListener(v ->
                    Toast.makeText(requireContext(), "UrinthUltra Mode", Toast.LENGTH_SHORT).show());
        }

        View discord = view.findViewById(R.id.discord_button);
        if (discord != null) {
            discord.setOnClickListener(v ->
                    Tools.openURL(requireActivity(), getString(R.string.social_media_invite)));
        }

        View play = view.findViewById(R.id.instance_play_button);
        if (play != null) {
            play.setOnClickListener(v ->
                    ExtraCore.setValue(ExtraConstants.LAUNCH_GAME, true));
        }
    }

    private void loadTargetArtwork(View root) {
        try {
            ByteArrayOutputStream text = new ByteArrayOutputStream();
            for (int i = 0; i < 32; i++) {
                int id = getResources().getIdentifier(
                        String.format(java.util.Locale.US, "target_chunk_%02d", i),
                        "raw",
                        requireContext().getPackageName());
                if (id == 0) throw new IllegalStateException("Missing target artwork chunk " + i);
                try (InputStream in = getResources().openRawResource(id)) {
                    byte[] buffer = new byte[4096];
                    int n;
                    while ((n = in.read(buffer)) != -1) text.write(buffer, 0, n);
                }
            }

            String encoded = text.toString(StandardCharsets.US_ASCII.name())
                    .replaceAll("\\s+", "")
                    .replaceAll("[^A-Za-z0-9+/=]", "");

            // Normalize padding so chunk boundaries or stray padding markers
            // cannot make Android's Base64 decoder reject the launcher artwork.
            int paddingStart = encoded.indexOf('=');
            if (paddingStart >= 0) {
                encoded = encoded.substring(0, paddingStart);
            }
            int remainder = encoded.length() % 4;
            if (remainder == 1) {
                throw new IllegalStateException("Invalid launcher artwork Base64 length");
            }
            if (remainder != 0) {
                encoded += "====".substring(0, 4 - remainder);
            }

            byte[] imageBytes = Base64.decode(encoded, Base64.NO_WRAP);
            android.graphics.Bitmap bitmap = BitmapFactory.decodeByteArray(
                    imageBytes, 0, imageBytes.length);
            if (bitmap == null) throw new IllegalStateException("Target artwork decode returned null");

            android.widget.ImageView image = root.findViewById(R.id.target_launcher_art);
            image.setImageBitmap(bitmap);
        } catch (Throwable t) {
            android.util.Log.e(TAG, "Failed to load exact launcher artwork", t);
            Toast.makeText(requireContext(), "Launcher artwork failed to load", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ExtraCore.setValue(ExtraConstants.REFRESH_ACCOUNT_SPINNER, true);
    }
}
