package br.com.androidstudies.plugpagandroidpoc.websocket.mapper;

import br.com.androidstudies.plugpagandroidpoc.websocket.model.WebsocketIn;
import br.com.uol.pagseguro.plugpag.PlugPagPaymentData;

public class PlugPagPaymentDataMapper {

    public static PlugPagPaymentData mapperToPlugPagPaymentData(WebsocketIn websocketIn) {
        return new PlugPagPaymentData.Builder()
                .setType(websocketIn.getmType())
                .setAmount(websocketIn.getmAmount())
                .setUserReference(websocketIn.getmUserReference())
                .setInstallments(websocketIn.getmInstallment())
                .build();
    }
}
