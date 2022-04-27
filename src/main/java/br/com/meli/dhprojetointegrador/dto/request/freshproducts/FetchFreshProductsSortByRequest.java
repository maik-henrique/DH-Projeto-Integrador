package br.com.meli.dhprojetointegrador.dto.request.freshproducts;

public enum FetchFreshProductsSortByRequest {
    L("batchNumber"),
    C("currentQuantity"),
    F("dueDate");

    private final String fieldName;

    FetchFreshProductsSortByRequest(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

}
