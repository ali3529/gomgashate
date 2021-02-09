package com.utabpars.gomgashteh.chat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.databinding.ImageMassageReceiverBinding;
import com.utabpars.gomgashteh.databinding.ImageMassageSenderBinding;
import com.utabpars.gomgashteh.databinding.ItemMassageReceveBinding;
import com.utabpars.gomgashteh.databinding.MassageSenderBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MassageAdaptor extends RecyclerView.Adapter<MassageAdaptor.MassageViewHolder> {
    List<TicketResponseModel.Massage> ticketResponseModel =new ArrayList<>();
    TicketResponseModel.Massage massage;
    DeleteCallback deleteCallback;

    public void getMassageList(List<TicketResponseModel.Massage> TicketResponseModel) {
        this.ticketResponseModel = TicketResponseModel;
    }

    public void addSendMassage(TicketResponseModel.Massage massage) {
        this.massage = massage;
        ticketResponseModel.add(massage);
    }

    public MassageAdaptor(DeleteCallback deleteCallback) {
        this.deleteCallback=deleteCallback;
    }

    @NonNull
    @Override
    public MassageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        ItemMassageReceveBinding receiverBinding;
        MassageSenderBinding senderBinding;
        ImageMassageSenderBinding imageSenderBinding;
        ImageMassageReceiverBinding imageReceiverBinding1;
        if (viewType==1){
            receiverBinding= DataBindingUtil.inflate(inflater, R.layout.item_massage_receve,parent,false);
            return new MassageViewHolder(receiverBinding);
        }else if (viewType==2){
            senderBinding= DataBindingUtil.inflate(inflater, R.layout.massage_sender,parent,false);
            return new MassageViewHolder(senderBinding);
        }
        else if (viewType==3){

            imageReceiverBinding1= DataBindingUtil.inflate(inflater, R.layout.image_massage_receiver,parent,false);
            return new MassageViewHolder(imageReceiverBinding1);
        }
        else{
            imageSenderBinding= DataBindingUtil.inflate(inflater, R.layout.image_massage_sender,parent,false);
            return new MassageViewHolder(imageSenderBinding);
        }



    }

    @Override
    public void onBindViewHolder(@NonNull MassageViewHolder holder, int position) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        long min=currentTime/1000;
        switch (holder.getItemViewType()){
            case 1:
                holder.receveBinding.massage.setText(ticketResponseModel.get(position).getMessage());
                holder.setMassageStatusreveive(ticketResponseModel.get(position).getStatus());
                holder.receveBinding.setCreateat(ticketResponseModel.get(position).getCreated_at());

                break;
            case 2:

                holder.senderBinding.massage2.setText(ticketResponseModel.get(position).getMessage());
                holder.setMassageStatusreveive(ticketResponseModel.get(position).getStatus());
                holder.senderBinding.setCreateat(ticketResponseModel.get(position).getCreated_at());
                if (min>ticketResponseModel.get(position).getTime()){
                    Log.d("sddsvfnhgkyy", "onViewCreated: "+"false");
                    Log.d("sddsvfnhgkyy", "onViewCreated: min  "+min);
                    Log.d("sddsvfnhgkyy", "onViewCreated: text  "+ticketResponseModel.get(position).getMessage());
                    Log.d("sddsvfnhgkyy", "onViewCreated: time "+ticketResponseModel.get(position).getTime());
                    holder.senderBinding.close.setVisibility(View.GONE);


                }else {
                    Log.d("sddsvfnhgkyy", "onViewCreated: "+"true");
                    Log.d("sddsvfnhgkyy", "onViewCreated: time "+ticketResponseModel.get(position).getTime());
                    Log.d("sddsvfnhgkyy", "onViewCreated: min "+min);
                    holder.senderBinding.close.setVisibility(View.VISIBLE);

                }
                holder.senderBinding.close.setOnClickListener(o->{
                    deleteCallback.ItemDeleted(ticketResponseModel.get(position).getAnswer_id());
                });

                break;
            case 3:
                holder.imageReceiverBinding1.setImage(ticketResponseModel.get(position).getPictures());
                holder.setImageStatusreveive(ticketResponseModel.get(position).getStatus());
                holder.imageReceiverBinding1.setCreateat(ticketResponseModel.get(position).getCreated_at());
                holder.imageReceiverBinding1.massage2.setOnClickListener(o->{
                    deleteCallback.ItemImageZoom(ticketResponseModel.get(position).getPictures());
                });
                break;
            case 4:
            holder.imageSenderBinding.setImage(ticketResponseModel.get(position).getPictures());
            holder.setImageStatusreveive(ticketResponseModel.get(position).getStatus());
                holder.imageSenderBinding.setCreateat(ticketResponseModel.get(position).getCreated_at());
                if (min>ticketResponseModel.get(position).getTime()){
                    Log.d("sddsvfnhgkyy", "onViewCreated: "+"false");
                    Log.d("sddsvfnhgkyy", "onViewCreated: min  "+min);
                    Log.d("sddsvfnhgkyy", "onViewCreated: text  "+ticketResponseModel.get(position).getMessage());
                    Log.d("sddsvfnhgkyy", "onViewCreated: time "+ticketResponseModel.get(position).getTime());
                    holder.imageSenderBinding.close.setVisibility(View.GONE);


                }else {
                    Log.d("sddsvfnhgkyy", "onViewCreated: "+"true");
                    Log.d("sddsvfnhgkyy", "onViewCreated: time "+ticketResponseModel.get(position).getTime());
                    Log.d("sddsvfnhgkyy", "onViewCreated: min "+min);
                    holder.imageSenderBinding.close.setVisibility(View.VISIBLE);

                }
                holder.imageSenderBinding.close.setOnClickListener(o->{
                    deleteCallback.ItemDeleted(ticketResponseModel.get(position).getAnswer_id());
                });

                holder.imageSenderBinding.massage2.setOnClickListener(o->{
                    deleteCallback.ItemImageZoom(ticketResponseModel.get(position).getPictures());
                });
            break;

        }
    }

    @Override
    public int getItemCount() {
        return ticketResponseModel.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (ticketResponseModel.get(position).getType().equals("1")){
            return 1;
        }else if (ticketResponseModel.get(position).getType().equals("2")) {
            return 2;
        }else if (ticketResponseModel.get(position).getType().equals("3")){
            return 3;
        }else return 4;
    }

    public class MassageViewHolder extends RecyclerView.ViewHolder {
        ItemMassageReceveBinding receveBinding;
        MassageSenderBinding senderBinding;
        //image viewholder
        ImageMassageSenderBinding imageSenderBinding;
        ImageMassageReceiverBinding imageReceiverBinding1;
        public MassageViewHolder(@NonNull ItemMassageReceveBinding receveBinding) {
            super(receveBinding.getRoot());
            this.receveBinding=receveBinding;
        }

        public MassageViewHolder(@NonNull MassageSenderBinding senderBinding) {
            super(senderBinding.getRoot());
            this.senderBinding = senderBinding;
        }

        public MassageViewHolder(@NonNull ImageMassageSenderBinding imageSenderBinding) {
            super(imageSenderBinding.getRoot());
            this.imageSenderBinding = imageSenderBinding;
        }

        public MassageViewHolder(@NonNull ImageMassageReceiverBinding imageReceiverBinding1) {
            super(imageReceiverBinding1.getRoot());
            this.imageReceiverBinding1 = imageReceiverBinding1;
        }

        public void setMassageStatusreveive(String status) {
            // i use try cash becaus alway on of the view in null
            // use try cash  as to function
            try {
                if (status.equals("0")){
                    receveBinding.setCheck(true);
                    receveBinding.setDoublcheck(false);
                }
                else {
                    receveBinding.setCheck(false);
                    receveBinding.setDoublcheck(true);
                }
            }catch (Exception e){
                if (status.equals("0")){
                    senderBinding.setCheck(true);
                    senderBinding.setDoublcheck(false);
                }
                else {
                    senderBinding.setCheck(false);
                    senderBinding.setDoublcheck(true);
                }
            }

        }

        public void setImageStatusreveive(String status) {
            // i use try cash becaus alway on of the view in null
            // use try cash  as to function
            try {
                if (status.equals("0")){
                    imageReceiverBinding1.setCheck(true);
                    imageReceiverBinding1.setDoublcheck(false);
                }
                else {
                    imageReceiverBinding1.setCheck(false);
                    imageReceiverBinding1.setDoublcheck(true);
                }
            }catch (Exception e){
                if (status.equals("0")){
                    imageSenderBinding.setCheck(true);
                    imageSenderBinding.setDoublcheck(false);
                }
                else {
                    imageSenderBinding.setCheck(false);
                    imageSenderBinding.setDoublcheck(true);
                }
            }

        }


    }

    public interface DeleteCallback{
        void ItemDeleted(int answerId);
        void ItemImageZoom(String url);
    }
}
