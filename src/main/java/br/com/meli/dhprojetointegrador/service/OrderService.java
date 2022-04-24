package br.com.meli.dhprojetointegrador.service;

import br.com.meli.dhprojetointegrador.entity.CartProduct;
import br.com.meli.dhprojetointegrador.entity.PurchaseOrder;
import br.com.meli.dhprojetointegrador.enums.StatusEnum;
import br.com.meli.dhprojetointegrador.repository.OrderRepository;

public class OrderService {

    @Override
    public PurchaseOrder atualizar(PurchaseOrder purchaseorder) {

        PurchaseOrder updateStatus = OrderRepository.findbyStatus(purchaseorder.setStatus(StatusEnum.ABERTO));

        if(updateStatus == "aberto") {
            throw new RuntimeException("Status ainda aberto!");
        }

        updateStatus.setStatus(purchaseorder.setStatus(StatusEnum.FINALIZADO));

        return OrderRepository.save(updateStatus);

    }

}
