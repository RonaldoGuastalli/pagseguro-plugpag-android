package br.com.androidstudies.plugpagandroidpoc.model;

public class PaymentDataModel {

    private int mType;
    private int mAmount;
    private int mInstallmentType;
    private String mUserReference;

    public PaymentDataModel() {
    }

    public int getmType() {
        return mType;
    }

    public void setmType(int mType) {
        this.mType = mType;
    }

    public int getmAmount() {
        return mAmount;
    }

    public void setmAmount(int mAmount) {
        this.mAmount = mAmount;
    }

    public int getmInstallmentType() {
        return mInstallmentType;
    }

    public void setmInstallmentType(int mInstallmentType) {
        this.mInstallmentType = mInstallmentType;
    }

    public String getmUserReference() {
        return mUserReference;
    }

    public void setmUserReference(String mUserReference) {
        this.mUserReference = mUserReference;
    }
}
