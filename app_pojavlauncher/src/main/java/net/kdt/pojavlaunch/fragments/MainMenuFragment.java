package net.kdt.pojavlaunch.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import git.artdeell.mojo.R;
import net.kdt.pojavlaunch.Tools;
import net.kdt.pojavlaunch.extra.ExtraConstants;
import net.kdt.pojavlaunch.extra.ExtraCore;

public class MainMenuFragment extends Fragment {
    public static final String TAG = "MainMenuFragment";

    public MainMenuFragment() { super(R.layout.fragment_launcher); }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        View.OnClickListener launch = v -> ExtraCore.setValue(ExtraConstants.LAUNCH_GAME, true);
        View.OnClickListener addAccount = v -> ExtraCore.setValue(ExtraConstants.SELECT_AUTH_METHOD, true);
        bindClick(view, R.id.hero_play, launch);
        bindClick(view, R.id.instance_play_1, launch);
        bindClick(view, R.id.instance_play_2, launch);
        bindClick(view, R.id.instance_play_3, launch);
        bindClick(view, R.id.add_account_card, addAccount);

        View discord = view.findViewById(R.id.discord_card);
        if (discord != null) discord.setOnClickListener(v -> Tools.openURL(requireActivity(), getString(R.string.social_media_invite)));

        View settings = view.findViewById(R.id.modrinth_settings);
        if (settings != null) settings.setOnClickListener(v -> openSettings());

        TextView ultra = view.findViewById(R.id.ultra_card);
        if (ultra != null) ultra.setOnClickListener(v -> {
            boolean off = ultra.getText().toString().endsWith("OFF");
            ultra.setText(off ? "UrInthUltra Mode\nON" : "UrInthUltra Mode\nOFF");
        });
    }

    private void bindClick(View root, int id, View.OnClickListener listener) {
        View target = root.findViewById(id);
        if (target != null) target.setOnClickListener(listener);
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