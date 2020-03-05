package br.com.androidstudies.plugpagandroidpoc.mock;

import br.com.androidstudies.plugpagandroidpoc.model.PaymentDataModel;

public class PaymentDataModelMock {

    public static PaymentDataModel paymentDataModelMock() {
        PaymentDataModel paymentDataModel = new PaymentDataModel();
        paymentDataModel.setmAmount(100);
        paymentDataModel.setmType(2);
        paymentDataModel.setmInstallmentType(1);
        paymentDataModel.setmUserReference("CODVENDA");
        return paymentDataModel;
    }

}
