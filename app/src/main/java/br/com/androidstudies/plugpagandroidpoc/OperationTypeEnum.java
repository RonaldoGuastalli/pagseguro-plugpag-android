package br.com.androidstudies.plugpagandroidpoc;

public enum OperationTypeEnum {

    TYPE_CREDITO(1),
    TYPE_DEBITO(2),
    TYPE_VOUCHER(3),
    INSTALLMENT_TYPE_A_VISTA(4);

    private final int cod;

    OperationTypeEnum(int cod) {
        this.cod = cod;
    }

    public static OperationTypeEnum toEnum(Integer cod) {
        if(cod == null) return null;
        for (OperationTypeEnum operationType : OperationTypeEnum.values()) {
            if(cod.equals(operationType.getCod()))
                return operationType;
        }
        return null;
    }

    public int getCod() {
        return cod;
    }
}
