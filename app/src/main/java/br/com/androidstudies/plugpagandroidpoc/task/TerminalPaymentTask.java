package br.com.androidstudies.plugpagandroidpoc.task;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import org.java_websocket.WebSocket;

import br.com.androidstudies.plugpagandroidpoc.PlugPagManager;
import br.com.androidstudies.plugpagandroidpoc.TaskHandler;
import br.com.androidstudies.plugpagandroidpoc.helper.Bluetooth;
import br.com.uol.pagseguro.plugpag.PlugPag;
import br.com.uol.pagseguro.plugpag.PlugPagDevice;
import br.com.uol.pagseguro.plugpag.PlugPagPaymentData;
import br.com.uol.pagseguro.plugpag.PlugPagTransactionResult;

public class TerminalPaymentTask extends AsyncTask<PlugPagPaymentData, String, PlugPagTransactionResult> {

    private PlugPagPaymentData mPaymentData = null;
    private WebSocket conn;

    /**
     * Creates a new terminal payment task.
     *
     * @param handler Handler used to report updates.
     */
    public TerminalPaymentTask(@NonNull TaskHandler handler, WebSocket conn) {
        if (handler == null) {
            throw new RuntimeException("TaskHandler reference cannot be null");
        }
        this.conn = conn;
    }

    @Override
    protected void onPreExecute() {
//        this.mHandler.onTaskStart();
    }

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

    @Override
    protected void onProgressUpdate(String... values) {
        conn.send(new Gson().toJson(values));
    }

    @Override
    protected void onPostExecute(PlugPagTransactionResult plugPagTransactionResult) {
        conn.send(new Gson().toJson(plugPagTransactionResult));
    }
}
