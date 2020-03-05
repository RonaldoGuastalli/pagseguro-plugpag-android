package br.com.androidstudies.plugpagandroidpoc.websocket.mapper;

import com.google.gson.Gson;

import br.com.androidstudies.plugpagandroidpoc.websocket.model.WebsocketIn;

public class WebsocketInMapper {

    public static WebsocketIn mapperToWebsocketIn(String message) {
        return new Gson().fromJson(message, WebsocketIn.class);
    }
}
