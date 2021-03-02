package com.utabpars.gomgashteh.category;

import com.utabpars.gomgashteh.database.categoryDatabase.Attrebiute;
import com.utabpars.gomgashteh.database.categoryDatabase.Subset;
import com.utabpars.gomgashteh.database.categoryDatabase.Subset2;

import java.util.List;

public interface SubSetCallBack {
    void onSubsetCallback(List<Subset> subSetModel);
    void onSubset2Callback(List<Subset2> subSetModel);
    void onAttributeCallback(List<Attrebiute> SubSetModel);
    void emptyCallback(boolean empty);
}
