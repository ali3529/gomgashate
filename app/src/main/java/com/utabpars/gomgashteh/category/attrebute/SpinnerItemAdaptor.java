package com.utabpars.gomgashteh.category.attrebute;

import android.util.Log;
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

public class SpinnerItemAdaptor extends RecyclerView.Adapter<SpinnerItemAdaptor.SpinerViewHolser> {
    List<String> list=new ArrayList<>();
    ItemCallbacl itemCallbacl;
    public SpinnerItemAdaptor(List<String> list,ItemCallbacl itemCallbacl) {
        this.list = list;
        this.itemCallbacl=itemCallbacl;
    }

    @NonNull
    @Override
    public SpinerViewHolser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        ItemReportTextBinding binding= DataBindingUtil.inflate(inflater, R.layout.item_report_text,parent,false);
        return new SpinerViewHolser(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SpinerViewHolser holder, int position) {
        List<String> list_id=new ArrayList<>();
        List<String> list_name=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String[] strings=list.get(i).split("@");
            list_name.add(strings[1]);
            list_id.add(strings[0]);
            Log.d("dvsdvdsv", "onBindViewHolder: "+strings[0].toString());
            Log.d("dvsdvdsv", "onBindViewHolder: "+strings[1].toString());

        }

        holder.binding.success.setText(list_name.get(position));

        holder.itemView.setOnClickListener(o ->{
            itemCallbacl.ItemClick(list_name.get(position),list_id.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class SpinerViewHolser extends RecyclerView.ViewHolder {
        ItemReportTextBinding binding;
        public SpinerViewHolser(@NonNull ItemReportTextBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
   public interface ItemCallbacl{
        void ItemClick(String value,String value_id);
    }
}
