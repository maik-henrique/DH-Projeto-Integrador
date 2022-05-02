package br.com.meli.dhprojetointegrador.unit.util;

import br.com.meli.dhprojetointegrador.entity.PurchaseOrder;
import br.com.meli.dhprojetointegrador.enums.StatusEnum;

import java.time.LocalDate;

public class PurchaseOrderCreator {
    public static PurchaseOrder createValidPurchaseOrder() {
        LocalDate date = LocalDate.of(2021, 04, 25);

        return PurchaseOrder.builder()
                .buyer(BuyerCreator.createValidBuyer())
                .date(date)
                .status(StatusEnum.FINALIZADO)
                .build();
    }
}
