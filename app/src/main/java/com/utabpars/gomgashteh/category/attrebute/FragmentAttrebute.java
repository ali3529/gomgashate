package com.utabpars.gomgashteh.category.attrebute;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.databinding.FragmentAttrebuteBinding;
import com.utabpars.gomgashteh.databinding.ItemHelpAttrBinding;
import com.utabpars.gomgashteh.utils.PlateNumber;

import java.util.ArrayList;
import java.util.List;


public class FragmentAttrebute extends Fragment implements BottonShettCallback{
    FragmentAttrebuteBinding binding;
    RecyclerView recyclerView;
    SpinnerAdaptor spinnerAdaptor;
    AttrebuteViewModel viewModel;
    SharedPreferences sharedPreferences;
    ArrayList<AtttrModel> spinnerlist=new ArrayList<>();
    int adaptorItemCount;
    SpinnerBottomSheet spinnerBottomSheet;
    int position;
    String id_for_emty_attr;
    String check_string="0";
    String card_attr_id;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_attrebute,container,false);
        viewModel=new ViewModelProvider(this).get(AttrebuteViewModel.class);
        sharedPreferences=getActivity().getSharedPreferences("add_announce", Context.MODE_PRIVATE);
        initVews();
        return binding.getRoot();
    }

    private void initVews() {
        recyclerView=binding.recyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String id=getArguments().getString("id");
        String type=getArguments().getString("type");
//          if (sharedPreferences.getString("card_id","0").equals("38")){
//                Log.d("fbdfbdfbbb", "onViewCreated: ");
//            }else {
//              viewModel.getAttrebute(id, type);
//              Log.d("fbdfbdfbbb", "onViewCreated: else "+sharedPreferences.getString("card_id,","0"));
//          }
        viewModel.getAttrebute(id, type);
        viewModel.spinnerModelMutableLiveData.observe(getViewLifecycleOwner(),t ->{
            if (carddd(sharedPreferences.getString("card_id","0"))) {
                card_attr_id=t.getAttrebuteData().get(0).getId();
                Log.d("kjhklblkjb", "onViewCreated: " + t.getAttrebuteData().get(0).getId());
                Log.d("kjhklblkjb", "onViewCreated: " + t.getAttrebuteData().size());
                PlateNumber.setType(sharedPreferences.getString("card_id","0"));
                binding.plate.setVisibility(View.VISIBLE);
                binding.recyclerview.setVisibility(View.GONE);
            }else {
                binding.plate.setVisibility(View.GONE);
                binding.recyclerview.setVisibility(View.VISIBLE);
                spinnerAdaptor = new SpinnerAdaptor(t, this);
                recyclerView.setAdapter(spinnerAdaptor);
                adaptorItemCount = recyclerView.getAdapter().getItemCount();
                id_for_emty_attr = t.getAttrebuteData().get(0).getId();
                setIndec();
                Log.d("kjhklblkjb", "onViewCreated: " + t.getAttrebuteData().get(0).getId());
            }

        });


        binding.save.setOnClickListener( o->{
            SharedPreferences.Editor editor=sharedPreferences.edit();
            if (binding.nassesury.isChecked() && getCardAttrValue(sharedPreferences.getString("card_id","0")).length()==0) {
                Toast.makeText(getContext(), "فیلد های خالی را پر کنید", Toast.LENGTH_SHORT).show();
            }else if (!binding.nassesury.isChecked() && getCardAttrValue(sharedPreferences.getString("card_id","0")).length()==0){
                editor.putString("collaction_id", "[, ]");
                editor.putString("type", "");
                editor.putString("title", "ویژگی");
                editor.putString("emty_status", id_for_emty_attr);
                editor.apply();
                Navigation.findNavController(o).navigate(R.id.action_fragmentAttrebute_to_add);
            }
            else {
                if (carddd(sharedPreferences.getString("card_id", "0"))) {

                    editor.putString("collaction_id", setCardIndec(card_attr_id,
                            getCardAttrValue(sharedPreferences.getString("card_id", "0")),
                            binding.nassesury.isChecked()));


                } else {
                    editor.putString("collaction_id", getData().toString());
                }

                editor.putString("type", "");
                editor.putString("title", "ویژگی");
                editor.putString("emty_status", id_for_emty_attr);
                editor.apply();
                Navigation.findNavController(o).navigate(R.id.action_fragmentAttrebute_to_add);
                Log.d("sgfsegesgeg", "onViewCreated: " + getData().toString());


                Log.d("fgnfdndfhf", "onViewCreated: " + setCardIndec(card_attr_id,
                        getCardAttrValue(sharedPreferences.getString("card_id", "0")),
                        binding.nassesury.isChecked()));
            }
        });


        binding.help.setOnClickListener(o->{
            showHelpDialoghhy();
        });

    }

    public void setIndec(){
        for (int i = 0; i < adaptorItemCount; i++) {
            spinnerlist.add(new AtttrModel("","",""));

        }
    }

    public String setCardIndec(String attr_id,String value,boolean is_ckeck){
        List<String> list=new ArrayList<>();
        list.add(attr_id);
        list.add(value);
        if (is_ckeck){
            list.add("1");
        }else {
            list.add("0");
        }

            return list.toString();
    }
    public List<String> getData(){
        List<String> list=new ArrayList<>();
        for (int i = 0; i < spinnerlist.size(); i++) {
            String[] strings=spinnerlist.get(i).getValue().split("@");
            list.add(spinnerlist.get(i).getId());
            list.add(strings[0]);
            list.add(spinnerlist.get(i).getIscheck());
        }
        return list;
    }

    @Override
    public void onClickSpinner(String id, List<String> values, int position) {
        this.position=position;
        spinnerBottomSheet=new SpinnerBottomSheet(values,id,attrCallback);
        spinnerBottomSheet.show(getActivity().getSupportFragmentManager(),"");
    }
    SpinnerBottomSheet.AttrCallback attrCallback=new SpinnerBottomSheet.AttrCallback() {
        @Override
        public void getAttr(String id, String value, String value_id,boolean isCheck) {
            spinnerBottomSheet.dismiss();

            if (isCheck){
                check_string="1";
            }else check_string="0";

            spinnerAdaptor.setText(value,position);
            AtttrModel atttrModel=new AtttrModel(id,value_id,check_string);
            spinnerlist.set(position,atttrModel);



        }
    };



    public void showHelpDialoghhy(){
        LayoutInflater inflater = this.getLayoutInflater();
        View help_layout = inflater.inflate(R.layout.item_help_attr, null);
      //  help_layout.findViewById(R.id.card).setBackgroundColor(Color.TRANSPARENT);

        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setCancelable(true);

        builder.setView(help_layout);

        AlertDialog alertDialog=builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();

        help_layout.findViewById(R.id.btn).setOnClickListener(o->{
            alertDialog.dismiss();
        });
    }


    public String getCardAttrValue(String type){
        String card="0";

        if (type.equals("39") || type.equals("127")){
            card=binding.cardInfo.getCarPlateNumber();
        }else if (type.equals("116")){
           card=binding.cardInfo.getMotoNumber();
        }else if (type.equals("38") || type.equals("117") || type.equals("162") ||
                type.equals("163") || type.equals("118")
                || type.equals("121") || type.equals("156") || type.equals("157")
                || type.equals("158")
                || type.equals("154")  || type.equals("155")){
          card=binding.cardInfo.getCardNumber();
        }
        return card;
    }
public boolean carddd(String type){
        boolean is_card=false;
    is_card= type.equals("39") || type.equals("127")
            || type.equals("116")
            || type.equals("38") || type.equals("117") || type.equals("162") ||
            type.equals("163") || type.equals("118") || type.equals("121") || type.equals("156") || type.equals("157")
            || type.equals("158")  || type.equals("154")  || type.equals("155") || type.equals("165") || type.equals("166");
    Log.d("dfbfdbdfb", "carddd: "+is_card);
    return is_card;
}

}