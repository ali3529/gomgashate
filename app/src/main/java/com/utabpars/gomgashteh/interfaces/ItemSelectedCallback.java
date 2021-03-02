package com.utabpars.gomgashteh.interfaces;

import android.view.View;

import com.utabpars.gomgashteh.database.categoryDatabase.Category;
import com.utabpars.gomgashteh.model.CategoryModel;

public interface ItemSelectedCallback {
    public void getSelectedItem(View view, Category categoryModel, int position, boolean is_checked);
}
