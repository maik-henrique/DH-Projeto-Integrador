package br.com.meli.dhprojetointegrador.service;

import br.com.meli.dhprojetointegrador.entity.PurchaseOrder;
import br.com.meli.dhprojetointegrador.enums.StatusEnum;
import br.com.meli.dhprojetointegrador.exception.PurchaseOrderNotFoundException;
import br.com.meli.dhprojetointegrador.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.Serializable;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @CachePut(value = "UpdateStatusOrder", key="#idorder")
    public PurchaseOrder atualizar(Long idorder) {

       try{
            PurchaseOrder updateStatus = orderRepository.getById(idorder);

           if(updateStatus.getStatus().equals(StatusEnum.ABERTO)) {
               updateStatus.setStatus(StatusEnum.FINALIZADO);
           }else {
               updateStatus.setStatus(StatusEnum.ABERTO);
           }

           return orderRepository.save(updateStatus);

       }catch (EntityNotFoundException e){
         throw new PurchaseOrderNotFoundException("Esta ordem nao foi encontrada na base de dados!");
       }


    }

}
