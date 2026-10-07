package net.kdt.pojavlaunch.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import git.artdeell.mojo.R;

public class InstanceCardAdapter extends RecyclerView.Adapter<InstanceCardAdapter.Holder> {
    public interface Listener { void onPlay(int position); void onMenu(int position); }
    private final Listener listener;
    private final String[] names = {"URinthH", "URinthH", "URinthH"};
    private final String[] versions = {"Vannila 26.3", "Vannila 26.3", "Vannila 26.3"};

    public InstanceCardAdapter(Listener listener) { this.listener = listener; }

    @NonNull @Override public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_instance_card, parent, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(parent.getMeasuredWidth() / 3, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.setMargins(0, 0, 6, 0);
        v.setLayoutParams(lp);
        return new Holder(v);
    }

    @Override public void onBindViewHolder(@NonNull Holder h, int position) {
        h.name.setText(names[position]);
        h.version.setText(versions[position]);
        h.play.setOnClickListener(v -> listener.onPlay(position));
        h.menu.setOnClickListener(v -> listener.onMenu(position));
    }

    @Override public int getItemCount() { return names.length; }

    static class Holder extends RecyclerView.ViewHolder {
        final TextView name, version; final MaterialButton play; final ImageButton menu;
        Holder(View v) {
            super(v);
            name = v.findViewById(R.id.instance_name);
            version = v.findViewById(R.id.instance_version);
            play = v.findViewById(R.id.instance_play);
            menu = v.findViewById(R.id.instance_menu);
        }
    }
}