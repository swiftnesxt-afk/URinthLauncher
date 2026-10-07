package net.kdt.pojavlaunch.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import git.artdeell.mojo.R;

public class ModCardAdapter extends RecyclerView.Adapter<ModCardAdapter.Holder> {
    public interface Listener { void onAdd(String name); }
    private final Listener listener;
    private final String[][] data = {
        {"Sodium", "NeoForge 1.21.1"},
        {"Lithium", "Fabric 1.21.1"},
        {"Iris", "Fabric 1.21.1"},
        {"Distant Horizons", "Forge 1.21.1"},
        {"Xaero's Minimap", "Forge 1.21.1"}
    };

    public ModCardAdapter(Listener listener) { this.listener = listener; }

    @NonNull @Override public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mod_card, parent, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(parent.getMeasuredWidth() / 5, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.setMargins(0, 0, 5, 0);
        v.setLayoutParams(lp);
        return new Holder(v);
    }

    @Override public void onBindViewHolder(@NonNull Holder h, int position) {
        h.name.setText(data[position][0]);
        h.version.setText(data[position][1]);
        h.add.setOnClickListener(v -> listener.onAdd(data[position][0]));
    }

    @Override public int getItemCount() { return data.length; }

    static class Holder extends RecyclerView.ViewHolder {
        final TextView name, version; final MaterialButton add;
        Holder(View v) {
            super(v);
            name = v.findViewById(R.id.mod_name);
            version = v.findViewById(R.id.mod_version);
            add = v.findViewById(R.id.mod_add);
        }
    }
}