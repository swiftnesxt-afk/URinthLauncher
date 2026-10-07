package net.kdt.pojavlaunch.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

    @Override
    public void onResume() {
        super.onResume();
        ExtraCore.setValue(ExtraConstants.REFRESH_ACCOUNT_SPINNER, true);
    }
}
