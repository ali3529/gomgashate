package com.utabpars.gomgashteh.interfaces;

import com.utabpars.gomgashteh.chat.ChatStatusModel;

public interface ChatCallBack {
    public void ChatListener(ChatStatusModel chatStatusModel);
    public void ChatErrorListener();
}
