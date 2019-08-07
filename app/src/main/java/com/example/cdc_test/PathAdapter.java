package com.example.cdc_test;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cdc_test.data.Path.Path;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PathAdapter extends RecyclerView.Adapter<PathAdapter.PathViewHolder> {

    private List<Path> paths;
    private OnUserClickListener onUserClickListener;

    public PathAdapter(OnUserClickListener onUserClickListener) {
        this.onUserClickListener = onUserClickListener;
        this.paths = new ArrayList<Path>();
    }

    @NonNull
    @Override
    public PathAdapter.PathViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new PathViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_path, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PathAdapter.PathViewHolder pathViewHolder, int i) {
        Path path = paths.get(i);
        pathViewHolder.bind(path);
    }

    class PathViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.lbl_name_path) TextView namePath;
        @BindView(R.id.lbl_date_path) TextView datePath;

        public PathViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(v -> {
                Path path = paths.get(getLayoutPosition());
                onUserClickListener.onUserClick(path);
            });
        }

        public void bind(Path path) {
            namePath.setText(path.getName());
            datePath.setText(path.getDate());
        }
    }

    @Override
    public int getItemCount() {
        return paths.size();
    }

    public void setPaths(List<Path> paths) {
        this.paths = paths;
        notifyDataSetChanged();
    }

    public interface OnUserClickListener {
        void onUserClick(Path path);
    }
}
