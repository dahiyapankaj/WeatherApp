package com.weatherapp.data.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.weatherapp.R;
import com.weatherapp.databinding.ItemRowBinding;

import java.util.List;


/**
 * PagedList Adapter for recyclerview
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<com.weatherapp.data.models.weekly.List> dataModelList;
    private Context context;
    private ListItemClickListener listener;

    public ListAdapter(List<com.weatherapp.data.models.weekly.List> dataModelList, ListItemClickListener listItemClickListener) {
        this.dataModelList = dataModelList;
        listener = listItemClickListener;
    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        ItemRowBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_row, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        com.weatherapp.data.models.weekly.List dataModel = dataModelList.get(position);
        holder.itemRowBinding.setDataModel(dataModel);
        holder.itemView.setOnClickListener(view -> listener.onItemClick(dataModel));
    }


    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ItemRowBinding itemRowBinding;

        public ViewHolder(ItemRowBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

    }

    public interface ListItemClickListener {
        void onItemClick(com.weatherapp.data.models.weekly.List result);
    }
}