package com.utabpars.gomgashteh.systemtickets;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.databinding.ItemSystemTicketBinding;

public class SystemTicketAdaptor extends RecyclerView.Adapter<SystemTicketAdaptor.SystemTicketViewHolder> {
    SystemTicketModel systemTicketModel;

    public SystemTicketAdaptor(SystemTicketModel systemTicketModel) {
        this.systemTicketModel = systemTicketModel;
    }

    @NonNull
    @Override
    public SystemTicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        ItemSystemTicketBinding binding= DataBindingUtil.inflate(inflater, R.layout.item_system_ticket,parent,false);
        return new SystemTicketViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SystemTicketViewHolder holder, int position) {
        holder.binding.setMessage(systemTicketModel.getMassageList().get(position));
    }

    @Override
    public int getItemCount() {
        return systemTicketModel.getMassageList().size();
    }

    class SystemTicketViewHolder extends RecyclerView.ViewHolder {
        ItemSystemTicketBinding binding;
        public SystemTicketViewHolder(@NonNull ItemSystemTicketBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
