package br.com.androidstudies.plugpagandroidpoc;


import android.app.AlertDialog;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;


public class ShowMessage extends AppCompatActivity {

    private AlertDialog mAlertDialog = null;

    /**
     * Shows an AlertDialog with a simple message.
     *
     * @param message Resource ID of the message to be displayed.
     */
    public void showMessage(@StringRes int message) {
        String msg = null;

        msg = getString(message);
        showMessage(msg);
    }

    /**
     * Shows an AlertDialog with a simple message.
     *
     * @param message Message to be displayed.
     */
    private void showMessage(@Nullable String message) {
        if (this.mAlertDialog != null && this.mAlertDialog.isShowing()) {
            this.mAlertDialog.dismiss();
        }

        if (TextUtils.isEmpty(message)) {
            this.showErrorMessage(R.string.msg_error_unexpected);
        } else {
            this.mAlertDialog = new AlertDialog.Builder(this)
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

        msg = this.getString(message);
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
            this.mAlertDialog = new AlertDialog.Builder(this)
                    .setTitle(R.string.title_error)
                    .setMessage(message)
                    .setCancelable(true)
                    .create();
            this.mAlertDialog.show();
        }
    }

}
