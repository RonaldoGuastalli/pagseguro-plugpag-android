package br.com.androidstudies.plugpagandroidpoc;


import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.AppCompatTextView;

import java.util.ArrayList;
import java.util.List;

import br.com.uol.pagseguro.plugpag.PlugPagTransactionResult;


public class ShowMessage {

    private AlertDialog mAlertDialog = null;
    private Context context;

    public ShowMessage(Context context) {
        this.context = context;
    }

    // AlertDialog
    /**
     * Shows an AlertDialog with a simple message.
     *
     * @param message Resource ID of the message to be displayed.
     */
    public void showMessage(@StringRes int message) {
        String msg = null;

        msg = context.getString(message);
        showMessage(msg);
    }

    /**
     * Shows an AlertDialog with a simple message.
     *
     * @param message Message to be displayed.
     */
    public void showMessage(@Nullable String message) {
        if (this.mAlertDialog != null && this.mAlertDialog.isShowing()) {
            this.mAlertDialog.dismiss();
        }

        if (TextUtils.isEmpty(message)) {
            this.showErrorMessage(R.string.msg_error_unexpected);
        } else {
            this.mAlertDialog = new AlertDialog.Builder(context)
                    .setMessage(message)
                    .setCancelable(true)
                    .create();
            this.mAlertDialog.show();
        }
    }


    /**
     * Shows an AlertDialog with an error message.
     *
     * @param message Resource ID of the message to be displayed.
     */
    private void showErrorMessage(@StringRes int message) {
        String msg = null;

        msg = context.getString(message);
        this.showErrorMessage(msg);
    }

    /**
     * Shows an AlertDialog with an error message.
     *
     * @param message Message to be displayed.
     */
    private void showErrorMessage(@Nullable String message) {
        if (this.mAlertDialog != null && this.mAlertDialog.isShowing()) {
            this.mAlertDialog.dismiss();
        }

        if (TextUtils.isEmpty(message)) {
            this.showErrorMessage(R.string.msg_error_unexpected);
        } else {
            this.mAlertDialog = new AlertDialog.Builder(context)
                    .setTitle(R.string.title_error)
                    .setMessage(message)
                    .setCancelable(true)
                    .create();
            this.mAlertDialog.show();
        }
    }

    public void showProgressDialog(@StringRes int message) {
        String msg = null;

        msg = context.getString(message);
        this.showProgressDialog(msg);
    }

    /**
     * Shows an AlertDialog with a ProgressBar.
     *
     * @param message Message to be displayed along-side with the ProgressBar.
     */
    public void showProgressDialog(@Nullable String message) {
        String msg = null;
//
        if (message == null) {
            msg = context.getString(R.string.msg_wait);
        } else {
            msg = message;
        }

        if (this.mAlertDialog != null && this.mAlertDialog.isShowing()) {
            ((AppCompatTextView)this.mAlertDialog.findViewById(R.id.txt_message)).setText(msg);
        } else {
            this.mAlertDialog = new AlertDialog.Builder(context)
                    .setView(LayoutInflater.from(context).inflate(R.layout.progressbar, null))
                    .setCancelable(false)
                    .create();
            this.mAlertDialog.show();
            this.showProgressDialog(msg);
        }
    }

    /**
     * Shows a transaction's result.
     *
     * @param result Result to be displayed.
     */
    public void showResult(@NonNull PlugPagTransactionResult result) {
        String resultDisplay = null;
        List<String> lines = null;

        if (result == null) {
            throw new RuntimeException("Transaction result cannot be null");
        }

        lines = new ArrayList<>();
        lines.add(context.getString(R.string.msg_result_result, result.getResult()));

        if (!TextUtils.isEmpty(result.getErrorCode())) {
            lines.add(context.getString(R.string.msg_result_error_code, result.getErrorCode()));
        }

        if (!TextUtils.isEmpty(result.getAmount())) {
            String value = null;

            value = String.format("%.2f",
                    Double.parseDouble(result.getAmount()) / 100d);
            lines.add(context.getString(R.string.msg_result_amount, value));
        }
    }

}
