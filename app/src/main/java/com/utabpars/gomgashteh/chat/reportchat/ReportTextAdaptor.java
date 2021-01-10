package com.utabpars.gomgashteh.chat.reportchat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.databinding.ItemReportTextBinding;

import java.util.ArrayList;
import java.util.List;

public class ReportTextAdaptor extends RecyclerView.Adapter<ReportTextAdaptor.ReportViewHolder> {
    List<String> list=new ArrayList<>();
    ReportClick reportClick;

    public ReportTextAdaptor(List<String> list,ReportClick reportClick) {
        this.list = list;
        this.reportClick=reportClick;
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        ItemReportTextBinding binding= DataBindingUtil.inflate(inflater, R.layout.item_report_text,parent,false);
        return new ReportViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
        holder.binding.success.setText(list.get(position));
        holder.itemView.setOnClickListener(o ->{
            reportClick.reportTextClick(list.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ReportViewHolder extends RecyclerView.ViewHolder {
        ItemReportTextBinding binding;
        public ReportViewHolder(@NonNull ItemReportTextBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}

interface ReportClick{
    void reportTextClick(String rText);
}
