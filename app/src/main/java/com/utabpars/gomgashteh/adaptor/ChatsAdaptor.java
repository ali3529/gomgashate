package com.utabpars.gomgashteh.adaptor;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.databinding.ItemTicketBinding;
import com.utabpars.gomgashteh.interfaces.ChatOnclick;
import com.utabpars.gomgashteh.model.ChatsModel;

import java.util.ArrayList;
import java.util.List;

public class ChatsAdaptor extends RecyclerView.Adapter<ChatsAdaptor.ChatViewHolder> {
    List<ChatsModel.Tickets> chats=new ArrayList<>();
    ChatOnclick chatOnclick;

    public ChatsAdaptor(List<ChatsModel.Tickets> chats,ChatOnclick chatOnclick) {
        this.chats = chats;
        this.chatOnclick=chatOnclick;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        ItemTicketBinding binding= DataBindingUtil.inflate(inflater, R.layout.item_ticket,parent,false);
        return new ChatViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        holder.binding.setModel(chats.get(position));
        holder.itemView.setOnClickListener(o ->{
            chatOnclick.onChatItemClicked(o,
                    chats.get(position).getTicket_id(),
                    chats.get(position).getAnnouncement_title(),
                    chats.get(position).getUser_id(),
                    chats.get(position).getAnnouncement_id(),
                    chats.get(position).isBlock());
        });

        Picasso.get().load(chats.get(position).getPicture()).into(holder.binding.image);
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {
        ItemTicketBinding binding;
        public ChatViewHolder(@NonNull ItemTicketBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
