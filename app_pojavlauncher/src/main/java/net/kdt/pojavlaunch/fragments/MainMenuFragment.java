package net.kdt.pojavlaunch.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import git.artdeell.mojo.R;
import net.kdt.pojavlaunch.Tools;
import net.kdt.pojavlaunch.extra.ExtraConstants;
import net.kdt.pojavlaunch.extra.ExtraCore;
import net.kdt.pojavlaunch.prefs.screens.LauncherPreferenceFragment;
import net.kdt.pojavlaunch.ui.InstanceCardAdapter;
import net.kdt.pojavlaunch.ui.ModCardAdapter;

public class MainMenuFragment extends Fragment {
    public static final String TAG = "MainMenuFragment";

    private final int[] navIds = {
        R.id.nav_home, R.id.nav_instances, R.id.nav_modpacks, R.id.nav_mods,
        R.id.nav_shaders, R.id.nav_worlds, R.id.nav_resource_packs, R.id.nav_servers, R.id.nav_settings
    };

    public MainMenuFragment() {
        super(R.layout.fragment_launcher);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setupInstanceRecycler(view);
        setupModRecycler(view);
        setupNavigation(view);
        setupControls(view);
    }

    private void setupInstanceRecycler(View root) {
        RecyclerView recycler = root.findViewById(R.id.instance_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
        recycler.setAdapter(new InstanceCardAdapter(new InstanceCardAdapter.Listener() {
            @Override public void onPlay(int position) {
                ExtraCore.setValue(ExtraConstants.LAUNCH_GAME, true);
            }
            @Override public void onMenu(int position) {
                Toast.makeText(requireContext(), "Instance actions", Toast.LENGTH_SHORT).show();
            }
        }));
    }

    private void setupModRecycler(View root) {
        RecyclerView recycler = root.findViewById(R.id.mod_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
        recycler.setAdapter(new ModCardAdapter(name ->
            Toast.makeText(requireContext(), name + " selected", Toast.LENGTH_SHORT).show()));
    }

    private void setupNavigation(View root) {
        for (int id : navIds) {
            View item = root.findViewById(id);
            if (item == null) continue;
            item.setOnClickListener(v -> {
                selectNavigation(root, v.getId());
                if (v.getId() == R.id.nav_settings) {
                    Tools.swapFragment(requireActivity(), LauncherPreferenceFragment.class,
                            net.kdt.pojavlaunch.LauncherActivity.SETTING_FRAGMENT_TAG, null);
                    return;
                }
                if (v.getId() != R.id.nav_home) {
                    TextViewLabel(v);
                }
            });
        }

        View profile = root.findViewById(R.id.nav_profile);
        if (profile != null) {
            profile.setOnClickListener(v ->
                Toast.makeText(requireContext(), "Modrinth profile", Toast.LENGTH_SHORT).show());
        }
    }

    private void selectNavigation(View root, int selectedId) {
        for (int id : navIds) {
            View item = root.findViewById(id);
            if (item == null) continue;
            item.setBackgroundResource(id == selectedId
                    ? R.drawable.sidebar_active_pill
                    : android.R.drawable.list_selector_background);
        }
    }

    private void TextViewLabel(View view) {
        android.widget.TextView label = null;
        if (view instanceof android.view.ViewGroup) {
            View child = ((android.view.ViewGroup) view).getChildAt(1);
            if (child instanceof android.widget.TextView) label = (android.widget.TextView) child;
        }
        String name = label == null ? "Category" : label.getText().toString();
        Toast.makeText(requireContext(), name + " selected", Toast.LENGTH_SHORT).show();
    }

    private void setupControls(View root) {
        View addAccount = root.findViewById(R.id.add_account_button);
        addAccount.setOnClickListener(v ->
            ExtraCore.setValue(ExtraConstants.SELECT_AUTH_METHOD, true));

        View checkUpdates = root.findViewById(R.id.check_updates_button);
        checkUpdates.setOnClickListener(v ->
            Toast.makeText(requireContext(), "Checking for updates…", Toast.LENGTH_SHORT).show());

        com.google.android.material.switchmaterial.SwitchMaterial ultra =
                root.findViewById(R.id.urinth_ultra_switch);
        ultra.setOnCheckedChangeListener((button, checked) ->
            Toast.makeText(requireContext(), checked ? "UrinthUltra Mode ON" : "UrinthUltra Mode OFF", Toast.LENGTH_SHORT).show());

        View discord = root.findViewById(R.id.discord_button);
        discord.setOnClickListener(v ->
            Tools.openURL(requireActivity(), getString(R.string.social_media_invite)));

        View viewAll = root.findViewById(R.id.view_all_mods);
        viewAll.setOnClickListener(v ->
            Toast.makeText(requireContext(), "Opening all mods", Toast.LENGTH_SHORT).show());

        View headerMenu = root.findViewById(R.id.header_menu);
        headerMenu.setOnClickListener(v ->
            Toast.makeText(requireContext(), "Navigation menu", Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onResume() {
        super.onResume();
        ExtraCore.setValue(ExtraConstants.REFRESH_ACCOUNT_SPINNER, true);
    }
}