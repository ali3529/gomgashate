package com.utabpars.gomgashteh.category;

public interface SubSetCallBack {
    void onSubsetCallback(SubSetModel subSetModel);
    void onAttributeCallback(SubSetModel SubSetModel);
    void emptyCallback(boolean empty,SubSetModel subSetModel);
}
