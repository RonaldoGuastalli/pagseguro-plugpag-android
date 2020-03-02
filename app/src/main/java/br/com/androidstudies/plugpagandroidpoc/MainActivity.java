package br.com.androidstudies.plugpagandroidpoc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;

import br.com.uol.pagseguro.plugpag.PlugPagAuthenticationListener;

public class MainActivity extends AppCompatActivity implements PlugPagAuthenticationListener {

    private static final int PERMISSIONS_REQUEST_CODE = 0x1234;
    private AlertDialog mAlertDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PlugPagManager.create(this.getApplicationContext());
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError() {

    }
}
