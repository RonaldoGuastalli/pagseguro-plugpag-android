package br.com.androidstudies.plugpagandroidpoc.task;

import android.os.AsyncTask;

import br.com.androidstudies.plugpagandroidpoc.PlugPagManager;
import br.com.androidstudies.plugpagandroidpoc.TaskHandler;
import br.com.uol.pagseguro.plugpag.PlugPag;
import br.com.uol.pagseguro.plugpag.PlugPagDevice;
import br.com.uol.pagseguro.plugpag.PlugPagPaymentData;
import br.com.uol.pagseguro.plugpag.PlugPagTransactionResult;

public class TerminalPaymentTask extends AsyncTask<PlugPagPaymentData, String, PlugPagTransactionResult> {

    private TaskHandler mHandler = null;
    private PlugPagPaymentData mPaymentData = null;

    @Override
    protected PlugPagTransactionResult doInBackground(PlugPagPaymentData... plugPagPaymentData) {
        PlugPagTransactionResult result = null;
        PlugPag plugpag = null;

        if (plugPagPaymentData != null && plugPagPaymentData.length > 0 && plugPagPaymentData[0] != null) {
            // Update throbber
            this.mPaymentData = plugPagPaymentData[0];
            this.publishProgress("");

            plugpag = PlugPagManager.getInstance().getPlugPag();

            try {
                plugpag.initBTConnection(new PlugPagDevice(Bluetooth.getTerminal()));
                result = plugpag.doPayment(plugPagPaymentData[0]);
            } catch (Exception e) {
                this.publishProgress(e.getMessage());
            }

            this.mPaymentData = null;
        }

        return result;
    }
}