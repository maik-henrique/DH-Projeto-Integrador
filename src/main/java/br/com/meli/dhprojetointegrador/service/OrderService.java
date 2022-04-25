package br.com.meli.dhprojetointegrador.service;

import br.com.meli.dhprojetointegrador.entity.PurchaseOrder;
import br.com.meli.dhprojetointegrador.enums.StatusEnum;
import br.com.meli.dhprojetointegrador.exception.PurchaseOrderNotFoundException;
import br.com.meli.dhprojetointegrador.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;


    public PurchaseOrder atualizar(Long idorder) {

       PurchaseOrder updateStatus;

       try{
            updateStatus = orderRepository.getById(idorder);

       }catch (Exception e){
         throw new PurchaseOrderNotFoundException("Esta ordem nao foi encontrada na base de dados!");
       }

        if(updateStatus.getStatus().equals(StatusEnum.ABERTO)) {
            updateStatus.setStatus(StatusEnum.FINALIZADO);
        }else {
            updateStatus.setStatus(StatusEnum.ABERTO);
        }

        return orderRepository.save(updateStatus);

    }

}
