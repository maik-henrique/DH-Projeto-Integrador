package br.com.meli.dhprojetointegrador.unit.util;

import br.com.meli.dhprojetointegrador.entity.Buyer;

public class BuyerCreator {
    public static Buyer createValidBuyer() {
        return Buyer.builder()
                .id(1L)
                .name("Bruno")
                .email("bruno@email.com")
                .build();
    }
}
