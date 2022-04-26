package br.com.meli.dhprojetointegrador.dto.request.freshproducts;

public enum FetchFreshProductsSortByRequest {
    L("batchStock.batchNumber"),
    C("batchStock.currentQuantity"),
    F("batchStock.dueDate");

    private final String fieldName;

    FetchFreshProductsSortByRequest(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

}
