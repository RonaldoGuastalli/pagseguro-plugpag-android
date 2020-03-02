package br.com.androidstudies.plugpagandroidpoc;

import android.content.Context;

import androidx.annotation.NonNull;

import br.com.uol.pagseguro.plugpag.PlugPag;
import br.com.uol.pagseguro.plugpag.PlugPagAppIdentification;

public class PlugPagManager {

    private static PlugPagManager sInstance = null;
    private PlugPag mPlugPag = null;

    private PlugPagManager(@NonNull Context appContext) {
        PlugPagAppIdentification appIdentification = null;

        if (appContext == null) {
            throw new RuntimeException("Context reference cannot be null");
        }

        appIdentification = new PlugPagAppIdentification(
                appContext.getString(R.string.plugpag_app_identification),
                appContext.getString(R.string.plugpag_app_version));
        this.mPlugPag = new PlugPag(appContext, appIdentification);
    }

    public static final PlugPagManager create(@NonNull Context appContext) {
        if (appContext == null) {
            throw new RuntimeException("Context reference cannot be null");
        }

        if (PlugPagManager.sInstance == null) {
            PlugPagManager.sInstance = new PlugPagManager(appContext);
        }

        return PlugPagManager.sInstance;
    }

    public static final PlugPagManager getInstance() {
        if (PlugPagManager.sInstance == null) {
            throw new RuntimeException("PlugPagManager not instantiated");
        }

        return PlugPagManager.sInstance;
    }

    public final PlugPag getPlugPag() {
        return this.mPlugPag;
    }
}
