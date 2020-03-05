package br.com.androidstudies.plugpagandroidpoc;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import br.com.androidstudies.plugpagandroidpoc.mock.PaymentDataModelMock;
import br.com.androidstudies.plugpagandroidpoc.model.PaymentDataModel;
import br.com.androidstudies.plugpagandroidpoc.task.TerminalPaymentTask;
import br.com.androidstudies.plugpagandroidpoc.websocket.ToUpperWebsocket;
import br.com.uol.pagseguro.plugpag.PlugPag;
import br.com.uol.pagseguro.plugpag.PlugPagAuthenticationListener;
import br.com.uol.pagseguro.plugpag.PlugPagPaymentData;
import br.com.uol.pagseguro.plugpag.PlugPagTransactionResult;
import br.com.uol.pagseguro.plugpag.PlugPagVoidData;

public class MainActivity
        extends AppCompatActivity
        implements View.OnClickListener, TaskHandler, PlugPagAuthenticationListener {

    private static final int PERMISSIONS_REQUEST_CODE = 0x1234;

    private ShowMessage showMessage = new ShowMessage(this);


    // Lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new ToUpperWebsocket( 6060 ).start();
    }

    // Click events
    @Override
    public void onClick(View v) {
        PlugPagManager.create(this.getApplicationContext());
        this.requestPermissions();
        this.getTask(PaymentDataModelMock.paymentDataModelMock());
    }

    /**
     * Start a specific payment task.
     */
    public void getTask(PaymentDataModel paymentDataModel) {
        switch (OperationTypeEnum.toEnum(paymentDataModel.getmType())) {
            case TYPE_CREDITO:
                break;
            case TYPE_DEBITO:
                this.startTerminalDebitPayment(paymentDataModel);
                break;
            case TYPE_VOUCHER:
                break;
            case INSTALLMENT_TYPE_A_VISTA:
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

    // Request missing permissions
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

    // Result display
    @Override
    public void onSuccess() {
        showMessage.showMessage(R.string.msg_authentication_ok);
    }

    @Override
    public void onError() {
        showMessage.showMessage(R.string.msg_authentication_failed);
    }

    // Task handling
    @Override
    public void onTaskStart() {
        showMessage.showProgressDialog(R.string.msg_wait);
    }

    @Override
    public void onProgressPublished(String progress, Object transactionInfo) {
        String message = null;
        String type = null;

        if (TextUtils.isEmpty(progress)) {
            message = this.getString(R.string.msg_wait);
        } else {
            message = progress;
        }

        if (transactionInfo instanceof PlugPagPaymentData) {
            switch (((PlugPagPaymentData) transactionInfo).getType()) {
                case PlugPag.TYPE_CREDITO:
                    type = this.getString(R.string.type_credit);
                    break;

                case PlugPag.TYPE_DEBITO:
                    type = this.getString(R.string.type_debit);
                    break;

                case PlugPag.TYPE_VOUCHER:
                    type = this.getString(R.string.type_voucher);
                    break;
            }

            message = this.getString(
                    R.string.msg_payment_info,
                    type,
                    (double) ((PlugPagPaymentData) transactionInfo).getAmount() / 100d,
                    ((PlugPagPaymentData) transactionInfo).getInstallments(),
                    message);
        } else if (transactionInfo instanceof PlugPagVoidData) {
            message = this.getString(R.string.msg_void_payment_info, message);
        }

        showMessage.showProgressDialog(message);

    }

    @Override
    public void onTaskFinished(Object result) {
        if (result instanceof PlugPagTransactionResult) {
            showMessage.showResult((PlugPagTransactionResult) result);
        } else if (result instanceof String) {
            showMessage.showMessage((String) result);        }

    }
}
