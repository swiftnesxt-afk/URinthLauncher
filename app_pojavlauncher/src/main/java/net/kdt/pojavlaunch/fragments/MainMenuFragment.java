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

public class MainMenuFragment extends Fragment {
    public static final String TAG = "MainMenuFragment";

    private final int[] navIds = {
        R.id.nav_home,
        R.id.nav_instances,
        R.id.nav_modpacks,
        R.id.nav_mods,
        R.id.nav_shaders,
        R.id.nav_worlds,
        R.id.nav_resource_packs,
        R.id.nav_servers,
        R.id.nav_settings
    };

    public MainMenuFragment() {
        super(R.layout.fragment_launcher);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        android.widget.ImageView hero = view.findViewById(R.id.hero_art);
        if (hero != null) {
            hero.setImageBitmap(
                net.kdt.pojavlaunch.ui.AssetBitmapLoader.loadBase64Jpeg(
                    requireContext(), R.raw.modrinth_hero_jpg_b64));
        }

        setupInstanceRecycler(view);
        setupNavigation(view);
        setupHeader(view);
    }

    private void setupInstanceRecycler(View root) {
        RecyclerView recycler = root.findViewById(R.id.instance_recycler);
        recycler.setLayoutManager(
            new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));

        recycler.setAdapter(new InstanceCardAdapter(new InstanceCardAdapter.Listener() {
            @Override
            public void onPlay(int position) {
                ExtraCore.setValue(ExtraConstants.LAUNCH_GAME, true);
            }

            @Override
            public void onMenu(int position) {
                Toast.makeText(
                    requireContext(),
                    "Instance actions",
                    Toast.LENGTH_SHORT).show();
            }
        }));
    }

    private void setupNavigation(View root) {
        for (int id : navIds) {
            View item = root.findViewById(id);
            if (item == null) continue;

            item.setOnClickListener(v -> {
                selectNavigation(root, v.getId());

                if (v.getId() == R.id.nav_settings) {
                    Tools.swapFragment(
                        requireActivity(),
                        LauncherPreferenceFragment.class,
                        net.kdt.pojavlaunch.LauncherActivity.SETTING_FRAGMENT_TAG,
                        null);
                    return;
                }

                if (v.getId() != R.id.nav_home) {
                    showCategorySelected(v);
                }
            });
        }

        View profile = root.findViewById(R.id.nav_profile);
        if (profile != null) {
            profile.setOnClickListener(v ->
                Toast.makeText(
                    requireContext(),
                    "Modrinth profile",
                    Toast.LENGTH_SHORT).show());
        }
    }

    private void selectNavigation(View root, int selectedId) {
        for (int id : navIds) {
            View item = root.findViewById(id);
            if (item == null) continue;

            if (id == selectedId) {
                item.setBackgroundResource(R.drawable.sidebar_active_pill);
            } else {
                item.setBackgroundResource(android.R.drawable.list_selector_background);
            }
        }
    }

    private void showCategorySelected(View view) {
        android.widget.TextView label = null;
        if (view instanceof android.view.ViewGroup) {
            View child = ((android.view.ViewGroup) view).getChildAt(1);
            if (child instanceof android.widget.TextView) {
                label = (android.widget.TextView) child;
            }
        }

        String name = label == null ? "Category" : label.getText().toString();
        Toast.makeText(
            requireContext(),
            name + " selected",
            Toast.LENGTH_SHORT).show();
    }

    private void setupHeader(View root) {
        View headerMenu = root.findViewById(R.id.header_menu);
        if (headerMenu != null) {
            headerMenu.setOnClickListener(v ->
                Toast.makeText(
                    requireContext(),
                    "Navigation menu",
                    Toast.LENGTH_SHORT).show());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ExtraCore.setValue(ExtraConstants.REFRESH_ACCOUNT_SPINNER, true);
    }
}
