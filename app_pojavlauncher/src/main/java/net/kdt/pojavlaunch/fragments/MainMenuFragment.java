package net.kdt.pojavlaunch.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import git.artdeell.mojo.R;
import net.kdt.pojavlaunch.Tools;
import net.kdt.pojavlaunch.extra.ExtraConstants;
import net.kdt.pojavlaunch.extra.ExtraCore;

public class MainMenuFragment extends Fragment {
    public static final String TAG = "MainMenuFragment"; // visible dashboard build

    public MainMenuFragment() {
        super(R.layout.fragment_launcher);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button addAccount = view.findViewById(R.id.add_account_button);
        if (addAccount != null) {
            addAccount.setOnClickListener(v -> ExtraCore.setValue(ExtraConstants.SELECT_AUTH_METHOD, true));
        }

        Button checkUpdates = view.findViewById(R.id.check_updates_button);
        if (checkUpdates != null) {
            checkUpdates.setOnClickListener(v ->
                    Toast.makeText(requireContext(), "Checking for updates…", Toast.LENGTH_SHORT).show());
        }

        Switch ultra = view.findViewById(R.id.urinth_ultra_switch);
        if (ultra != null) {
            ultra.setOnCheckedChangeListener((buttonView, isChecked) ->
                    Toast.makeText(requireContext(), isChecked ? "UrinthUltra Mode: ON" : "UrinthUltra Mode: OFF", Toast.LENGTH_SHORT).show());
        }

        TextView discord = view.findViewById(R.id.discord_button);
        if (discord != null) {
            discord.setOnClickListener(v -> Tools.openURL(requireActivity(), getString(R.string.social_media_invite)));
        }

        TextView viewAll = view.findViewById(R.id.mods_view_all);
        if (viewAll != null) {
            viewAll.setOnClickListener(v ->
                    Toast.makeText(requireContext(), "All Mods", Toast.LENGTH_SHORT).show());
        }

        View.OnClickListener play = v -> ExtraCore.setValue(ExtraConstants.LAUNCH_GAME, true);
        View firstPlay = view.findViewById(R.id.instance_play_button);
        if (firstPlay != null) firstPlay.setOnClickListener(play);
    }

    @Override
    public void onResume() {
        super.onResume();
        ExtraCore.setValue(ExtraConstants.REFRESH_ACCOUNT_SPINNER, true);
    }
}
