package com.utabpars.gomgashteh.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.adaptor.SearchAdaptor;
import com.utabpars.gomgashteh.api.ApiClient;
import com.utabpars.gomgashteh.api.ApiInterface;
import com.utabpars.gomgashteh.databinding.FragmentSearchBinding;
import com.utabpars.gomgashteh.interfaces.DetileCallBack;
import com.utabpars.gomgashteh.model.AnoncmentModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static androidx.core.content.ContextCompat.getSystemService;

public class FragmentSearch extends Fragment  {
    FragmentSearchBinding binding;
    EditText searchBar;
    RecyclerView searchRecyClerView;
    SearchAdaptor searchAdaptor;
    ImageView backArrow,clear_text;
    static AnoncmentModel Save_anoncmentModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this
       binding= DataBindingUtil.inflate(inflater,R.layout.fragment_search,container,false);
       getActivity().findViewById(R.id.bottomnav).setVisibility(View.GONE);
        initViews();

        return binding.getRoot();

    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InputMethodManager inputMethodManager =
                (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (searchBar.requestFocus()){
            inputMethodManager.toggleSoftInputFromWindow(
                    getView().getApplicationWindowToken(),
                    InputMethodManager.SHOW_FORCED, 0);
            Log.d("dghfdgfdg", "onViewCreated: rewuest");
        }


        searchRecyClerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),
                        InputMethodManager.RESULT_UNCHANGED_SHOWN);
                return false;
            }
        });





        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searcgKey(charSequence.toString());
                binding.setProgres(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),
                        InputMethodManager.RESULT_UNCHANGED_SHOWN);
            }
        });

        clear_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchBar.getText().clear();
            }
        });




    }

    private void initViews() {
        searchBar=binding.searchtwo;
        searchRecyClerView=binding.searchrecyclerview;
        backArrow=binding.backArrow;
        clear_text=binding.cleanText;
        searchRecyClerView.setLayoutManager(new LinearLayoutManager(getContext()));
        searchRecyClerView.setHasFixedSize(true);
    }

    private void searcgKey(String query) {
        ApiInterface apiInterface= ApiClient.getApiClient();
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(apiInterface.Search(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<AnoncmentModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull AnoncmentModel anoncmentModel) {
                        if (anoncmentModel.getResponse().equals("1")){
                            Toast.makeText(getContext(), ""+anoncmentModel.getData().size(), Toast.LENGTH_SHORT).show();
                            binding.setSearchvisibility(true);
                            binding.setProgres(false);
                            binding.setLayouterror(false);
                            Log.d("vfrtert", "onSuccess: seccos");
                            Save_anoncmentModel=anoncmentModel;
                            searchAdaptor=new SearchAdaptor(anoncmentModel.getData(), new DetileCallBack() {
                                @Override
                                public void onItemClicked(View view, int id) {
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("id", id);

                                    Navigation.findNavController(view).navigate(R.id.action_fragmentSearch_to_fragmentAnnouncmentDetail, bundle);
                                }
                            });
                            searchRecyClerView.setAdapter(searchAdaptor);

                        }else {

                            binding.setProgres(false);
                            binding.setLayouterror(true);
                            binding.setResult(anoncmentModel.getMassage());
                            Log.d("vfrtert", "onSuccess: wlse");
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        binding.setProgres(false);
                        Log.d("vfrtert", "onSuccess: "+ e.toString());
                    }
                }));
    }


}