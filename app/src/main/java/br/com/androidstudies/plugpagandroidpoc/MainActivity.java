package br.com.androidstudies.plugpagandroidpoc;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import br.com.androidstudies.plugpagandroidpoc.websocket.ToUpperWebsocket;
import br.com.uol.pagseguro.plugpag.PlugPagAuthenticationListener;

public class MainActivity
        extends AppCompatActivity
        implements View.OnClickListener, TaskHandler, PlugPagAuthenticationListener {

    private static final int PERMISSIONS_REQUEST_CODE = 0x1234;


    // Lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Click events
    @Override
    public void onClick(View v) {
        PlugPagManager.create(this.getApplicationContext());
        this.requestPermissions();
        new ToUpperWebsocket( 6060 , this).start();
    }

    /**
     * Requests permissions on runtime, if any needed permission is not granted.
     */
    private void requestPermissions() {
        String[] missingPermissions = null;
        missingPermissions = this.filterMissingPermissions(this.getManifestPermissions());
        if (missingPermissions != null && missingPermissions.length > 0) {
            ActivityCompat.requestPermissions(this, missingPermissions, MainActivity.PERMISSIONS_REQUEST_CODE);
        }
    }


    /**
     *
     * @param permissions List of permissions to be checked.
     * @return Permissions not granted.
     */
    private String[] filterMissingPermissions(String[] permissions) {
        String[] missingPermissions = null;
        List<String> list = null;
        list = new ArrayList<>();
        if (permissions != null && permissions.length > 0) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(this.getApplicationContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                    list.add(permission);
                }
            }
        }
        missingPermissions = list.toArray(new String[0]);
        return missingPermissions;
    }

    /**
     * Returns a list of permissions requested on the AndroidManifest.xml file.
     *
     * @return Permissions requested on the AndroidManifest.xml file.
     */
    private String[] getManifestPermissions() {
        String[] permissions = null;
        PackageInfo info = null;
        try {
            info = this.getPackageManager()
                    .getPackageInfo(this.getApplicationContext().getPackageName(), PackageManager.GET_PERMISSIONS);
            permissions = info.requestedPermissions;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("PlugPag", "Package name not found", e);
        }
        if (permissions == null) {
            permissions = new String[0];
        }

        return permissions;
    }

    // Result display
    @Override
    public void onSuccess() { }

    @Override
    public void onError() { }

    // Task handling
    @Override
    public void onTaskStart() { }

    @Override
    public void onProgressPublished(String progress, Object transactionInfo) { }

    @Override
    public void onTaskFinished(Object result) { }
}
