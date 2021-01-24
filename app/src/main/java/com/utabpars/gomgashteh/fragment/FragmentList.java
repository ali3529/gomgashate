package com.utabpars.gomgashteh.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.adaptor.CategoryAdaptor;
import com.utabpars.gomgashteh.databinding.FragmentListBinding;
import com.utabpars.gomgashteh.interfaces.CategoryCallBack;
import com.utabpars.gomgashteh.model.CategoryModel;
import com.utabpars.gomgashteh.viewmodel.CategoryViewModel;

public class FragmentList extends Fragment {
    FragmentListBinding binding;
    RecyclerView recyclerView;
    CategoryAdaptor categoryAdaptor;
    CategoryViewModel categoryViewModel;
    static MutableLiveData<CategoryModel> saveInstanceList;
    Toolbar toolbar;
    String type ="0";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_list,container,false);
        initViews();
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        categoryViewModel=new ViewModelProvider(getActivity()).get(CategoryViewModel.class);

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("fghfdgdfhh", "onViewCreated:  list");
        binding.setProgress(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        try {
            type =getArguments().getString("type");
        }catch (Exception e){
            type="category";
        }





            categoryViewModel.categoriesMutableLiveData().observe(getViewLifecycleOwner(), new Observer<CategoryModel>() {
                @Override
                public void onChanged(CategoryModel categoryModel) {
                    binding.setProgress(false);
                    categoryAdaptor=new CategoryAdaptor(categoryModel.getListData(), new CategoryCallBack() {
                        @Override
                        public void getCategoryId(View view, String id,int position,String title) {


                            saveCategoryNames(title);
                                Bundle bundle = new Bundle();
                                bundle.putString("title", categoryModel.getListData().get(position).getCategoryName());
                                bundle.putString("id", String.valueOf(id));
                                bundle.putString("type", type);
                                Navigation.findNavController(view).navigate(R.id.action_list_to_fragmentCallection, bundle);
                            Log.d("fdtjnfngdbfv", "getCategoryId: dnknvx [test]"+id);

                        }
                    });
                    Log.d("sdvsdvds", "onchange: "+categoryModel.getResponse());
                    Log.d("sdvsdvds", "onchange: "+categoryModel.getListData().get(0).getCategoryName());
                    recyclerView.setAdapter(categoryAdaptor);
                    saveInstanceList =categoryViewModel.categoriesMutableLiveData();
                }
            });

//        else {
//            saveInstanceList.observe(getViewLifecycleOwner(), new Observer<CategoryModel>() {
//                @Override
//                public void onChanged(CategoryModel categoryModel) {
//                    categoryAdaptor=new CategoryAdaptor(categoryModel.getListData(), new CategoryCallBack() {
//                        @Override
//                        public void getCategoryId(View view, int id,int position) {
//                            Bundle bundle=new Bundle();
//                            bundle.putString("title",categoryModel.getListData().get(position).getCategoryName());
//                            bundle.putString("id",String.valueOf(id));
//                            Navigation.findNavController(view).navigate(R.id.action_list_to_fragmentCallection,bundle);
//                            Toast.makeText(getContext(), ""+id, Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                    Log.d("sdvsdvds", "instance: "+categoryModel.getResponse());
//                    Log.d("sdvsdvds", "instance: "+categoryModel.getListData().get(0).getCategoryName());
//                    recyclerView.setAdapter(categoryAdaptor);
//                }
//            });
//        }


        lastAnnouncmentAboveBtNavigation();

    }
    private void lastAnnouncmentAboveBtNavigation() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                binding.gggg.setVisibility(View.GONE);
                LinearLayoutManager layoutManager= (LinearLayoutManager) recyclerView.getLayoutManager();
                int itemcount=layoutManager.getItemCount();
                int lastvisi=layoutManager.findLastVisibleItemPosition();
                Log.d("dsgfdgfdg", "onScrolled: "+itemcount);
                Log.d("dsgfdgfdg", "onScrolled: "+lastvisi);
                if (lastvisi==itemcount-1){
                    Log.d("dsgfdgfdg", "last: ");
                    binding.gggg.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        OnBackPressedCallback callback=new OnBackPressedCallback(true) {

            @Override
            public void handleOnBackPressed() {

              //  Navigation.findNavController(getView()).navigate(R.id.action_list_to_announcement);
               Navigation.findNavController(getView()).popBackStack();



            };

        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this,callback);
    }

    private void initViews() {
        recyclerView=binding.categoryrecyclerview;
        toolbar=binding.toolbar;
    }

//
//    private void getCategory(){
//        ApiInterface apiInterface= ApiClient.getApiClient();
//        CompositeDisposable compositeDisposable=new CompositeDisposable();
//        compositeDisposable.add(apiInterface.getcategories("")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableSingleObserver<CategoryModel>() {
//                    @Override
//                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull CategoryModel categoryModel) {
//                        if (categoryModel.getResponse().equals("1")){
//                            Log.d("dsfgdsfds", "onSuccess: "+categoryModel.getResponse());
//                            Log.d("dsfgdsfds", "onSuccess: "+categoryModel.getListData().get(0).getCategoryName());
//                        }
//                    }
//
//                    @Override
//                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
//                        Log.d("dsfsd", "onError: "+e.toString());
//                    }
//                }));
//    }
private void saveCategoryNames(String title) {
    SharedPreferences SaveCategoryName;
    SharedPreferences.Editor SaveCategoryNameEditor;
    SaveCategoryName = getActivity().getSharedPreferences("save_category_name", Context.MODE_PRIVATE);
    SaveCategoryNameEditor = SaveCategoryName.edit();
    SaveCategoryNameEditor.putString("category", title);
    SaveCategoryNameEditor.apply();
}


}