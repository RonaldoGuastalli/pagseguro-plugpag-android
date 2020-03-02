package br.com.androidstudies.plugpagandroidpoc;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import br.com.androidstudies.plugpagandroidpoc.helper.Generator;
import br.com.androidstudies.plugpagandroidpoc.model.PaymentDataModel;
import br.com.uol.pagseguro.plugpag.PlugPag;
import br.com.uol.pagseguro.plugpag.PlugPagAuthenticationListener;
import br.com.uol.pagseguro.plugpag.PlugPagPaymentData;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, PlugPagAuthenticationListener {

    private static final int PERMISSIONS_REQUEST_CODE = 0x1234;

    private ShowMessage showMessage = new ShowMessage();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        this.requestPermissions();

//        this.setupEventListeners();
    }

    /**
     * Setups event listeners for all Buttons.
     */
    private void setupEventListeners() {
        ViewGroup root = null;
        View currentView = null;

        root = this.getWindow().getDecorView().findViewById(android.R.id.content); // Default Android content container
        root = (ViewGroup) root.getChildAt(0); // ScrollView
        root = (ViewGroup) root.getChildAt(0); // LinearLayout

        for (int i = 0; i < root.getChildCount(); i++) {
            currentView = root.getChildAt(i);

            if (currentView instanceof Button) {
                currentView.setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View v) {
        PlugPagManager.create(this.getApplicationContext());
        this.requestPermissions();

    }

    /**
     * Start a specific payment task.
     */
    public void getTask(PaymentDataModel paymentDataModel) {
        switch (OperationTypeEnum.toEnum(paymentDataModel.getmType())) {
            case OperationTypeEnum.TYPE_DEBITO:
                this.startTerminalDebitPayment();
                break;

            default:
                break;
        }

    }

    /**
     * Starts a new debit payment on a terminal.
     */
    private void startTerminalDebitPayment(PaymentDataModel paymentDataModel) {
        PlugPagPaymentData paymentData = null;

        paymentData = new PlugPagPaymentData.Builder()
                .setType(PlugPag.TYPE_DEBITO)
                .setAmount(paymentDataModel.getmAmount())
                .setUserReference(paymentDataModel.getmUserReference())
                .build();
        new TerminalPaymentTask(this).execute(paymentData);
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
     * Filters only the permissions still not granted.
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

    @Override
    public void onSuccess() {
    }

    @Override
    public void onError() {

    }
}
