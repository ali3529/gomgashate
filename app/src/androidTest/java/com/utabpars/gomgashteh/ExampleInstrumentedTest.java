package com.utabpars.gomgashteh;

import android.content.Context;
import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.utabpars.gomgashteh.database.categoryDatabase.Category;
import com.utabpars.gomgashteh.database.categoryDatabase.CategoryDataBase;
import com.utabpars.gomgashteh.database.categoryDatabase.CategoryFromServerViewModel;
import com.utabpars.gomgashteh.database.categoryDatabase.Collection;
import com.utabpars.gomgashteh.database.categoryDatabase.DatabaseEntityModel;
import com.utabpars.gomgashteh.database.categoryDatabase.Subset;
import com.utabpars.gomgashteh.database.categoryDatabase.Subset2;
import com.utabpars.gomgashteh.database.citydatabase.City;
import com.utabpars.gomgashteh.database.citydatabase.CityDatabase;
import com.utabpars.gomgashteh.database.citydatabase.CityFromServerViewModel;
import com.utabpars.gomgashteh.database.citydatabase.Province;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
   // //private CategoryDataBase db;
    private CityDatabase db;

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.utabpars.gomgashteh", appContext.getPackageName());
    }
    @Rule
   public InstantTaskExecutorRule instantTaskExecutorRule=new InstantTaskExecutorRule();
    @Test
    public void getCateg(){

        CategoryFromServerViewModel viewModel;
        viewModel=new ViewModelProvider(ViewModelStore::new).get(CategoryFromServerViewModel.class);

        viewModel.databaseEntityModelMutableLiveData.observeForever(t->{
            assertEquals(t.getCategory().get(0).getId(),"22");
//            assertEquals(t.getCollection().get(0).getId(),"38");
//            assertEquals(t.getSubset().get(0).getId(),"77");
//            assertEquals(t.getSubset2().get(0).getId(),"83");
        });

    }

//    @Test
//    public void insertData(){
//        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
//        db = CategoryDataBase.getInstance(appContext);
//
//        CategoryFromServerViewModel viewModel;
//        viewModel=new ViewModelProvider(ViewModelStore::new).get(CategoryFromServerViewModel.class);
//
//        viewModel.databaseEntityModelMutableLiveData.observeForever(t->{
//            for (Category category:t.getCategory()) {
//                db.categoryDao().setCategoryToDB(category);
//            }
////            for (Collection collection:t.getCollection()) {
////                db.categoryDao().setCollectionToDB(collection);
////            }
////            for (Subset subset:t.getSubset()) {
////                db.categoryDao().setSubsetToDB(subset);
////            }
////            for (Subset2 subset2:t.getSubset2()) {
////                db.categoryDao().setSubset2ToDB(subset2);
////            }
//
//        });

     //   db.categoryDao().getCategorys().observeForever(t->{
          // assertEquals( db.categoryDao().getCategorys().get(0).getId(),"22");
     //   });
//        db.categoryDao().getCategorys("22").observeForever(t->{
//            assertEquals(t.getName(),"کارت ها");
//        });




   // }


//    @Test
//    public void getCity(){
//        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
//        db=CityDatabase.getInstance(appContext);
//        CityFromServerViewModel viewModel=new ViewModelProvider(ViewModelStore::new).get(CityFromServerViewModel.class);
//        viewModel.cityDatabaseModelMutableLiveData.observeForever(result->{
////            assertEquals(result.getProvinces().get(0).getProvince_id(),"1");
////            assertEquals(result.getCities().get(0).getProvince_id(),"1");
//            for (Province province:result.getProvinces()) {
//                db.cityDao().insertProvinceToDB(province);
//
//            }
//            for (City city:result.getCities()) {
//               // Log.d("vsdvsdv", "getCity: "+db.cityDao().insertCityToDB(city));
//                assertEquals( city.getProvince_id(),"1");
//                db.cityDao().insertCityToDB(city);
//
//            }
//        });
//       // db.cityDao().getCity().get(0).getProvince_id().equals("1");
////        db.cityDao().getCity().observeForever(t->{
////           assertEquals(t.get(0).getProvince_id(),"1");
////
////
////        });
//
//    }
}