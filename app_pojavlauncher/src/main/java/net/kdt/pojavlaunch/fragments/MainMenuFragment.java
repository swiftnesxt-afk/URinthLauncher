package net.kdt.pojavlaunch.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import net.kdt.pojavlaunch.Tools;
import net.kdt.pojavlaunch.extra.ExtraConstants;
import net.kdt.pojavlaunch.extra.ExtraCore;
import git.artdeell.mojo.R;

public class MainMenuFragment extends Fragment {
    public static final String TAG = "MainMenuFragment";

    public MainMenuFragment() {
        super(R.layout.fragment_launcher);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        View nav = view.findViewById(R.id.nav_button);
        View settings = view.findViewById(R.id.nav_settings);
        View account = view.findViewById(R.id.add_account_button);
        View discord = view.findViewById(R.id.discord_button);

        // Match the target dashboard's distinct latest-mod cards.
        setupModCard(view.findViewById(R.id.mod_sodium), "Sodium", "NeoForge 1.21.1", R.drawable.ic_px_zap, "#A7E35A");
        setupModCard(view.findViewById(R.id.mod_lithium), "Lithium", "Fabric 1.21.1", R.drawable.ic_px_speed, "#A56BFF");
        setupModCard(view.findViewById(R.id.mod_iris), "Iris", "Fabric 1.21.1", R.drawable.ic_px_image_renderer, "#63D7FF");
        setupModCard(view.findViewById(R.id.mod_distant), "Distant Horizons", "Forge 1.21.1", R.drawable.ic_px_viewport_expand, "#55C7D9");
        setupModCard(view.findViewById(R.id.mod_xaero), "Xaero's Minimap", "Forge 1.21.1", R.drawable.ic_px_control_size, "#D7E2EA");

        View play1 = view.findViewById(R.id.instance_one).findViewById(R.id.instance_play);
        View play2 = view.findViewById(R.id.instance_two).findViewById(R.id.instance_play);
        View play3 = view.findViewById(R.id.instance_three).findViewById(R.id.instance_play);

        View.OnClickListener openSettings = v ->
                Tools.swapFragment(requireActivity(),
                        net.kdt.pojavlaunch.prefs.screens.LauncherPreferenceFragment.class,
                        net.kdt.pojavlaunch.LauncherActivity.SETTING_FRAGMENT_TAG, null);

        if (nav != null) nav.setOnClickListener(openSettings);
        if (settings != null) settings.setOnClickListener(openSettings);

        View.OnClickListener addAccount = v ->
                ExtraCore.setValue(ExtraConstants.SELECT_AUTH_METHOD, true);
        if (account != null) account.setOnClickListener(addAccount);

        View.OnClickListener play = v ->
                ExtraCore.setValue(ExtraConstants.LAUNCH_GAME, true);
        if (play1 != null) play1.setOnClickListener(play);
        if (play2 != null) play2.setOnClickListener(play);
        if (play3 != null) play3.setOnClickListener(play);

        if (discord != null) {
            discord.setOnClickListener(v ->
                    Tools.openURL(requireActivity(), getString(R.string.social_media_invite)));
        }

        View ultra = view.findViewById(R.id.ultra_button);
        if (ultra instanceof TextView) {
            ultra.setOnClickListener(v -> {
                TextView t = (TextView) v;
                boolean on = t.getText().toString().endsWith("ON");
                t.setText(on
                        ? "🚀  UrinthUltra Mode\n     Enable ultra performance mode             OFF   ON"
                        : "🚀  UrinthUltra Mode\n     Enable ultra performance mode             OFF   ON");
                t.setSelected(!on);
            });
        }

        View updates = view.findViewById(R.id.check_updates_button);
        if (updates != null) {
            updates.setOnClickListener(v ->
                    android.widget.Toast.makeText(requireContext(),
                            "Checking for updates…", android.widget.Toast.LENGTH_SHORT).show());
        }
    }

    
    private void setupModCard(View card, String name, String version, int iconRes, String tint) {
        if (card == null) return;
        TextView nameView = card.findViewById(R.id.mod_name);
        TextView versionView = card.findViewById(R.id.mod_version);
        ImageView iconView = card.findViewById(R.id.mod_icon);
        if (nameView != null) nameView.setText(name);
        if (versionView != null) versionView.setText(version);
        if (iconView != null) {
            iconView.setImageResource(iconRes);
            iconView.setColorFilter(Color.parseColor(tint));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ExtraCore.setValue(ExtraConstants.REFRESH_ACCOUNT_SPINNER, true);
    }
}
