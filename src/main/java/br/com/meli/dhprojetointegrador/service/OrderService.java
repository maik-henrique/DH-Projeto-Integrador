package br.com.meli.dhprojetointegrador.service;

import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.entity.PurchaseOrder;
import br.com.meli.dhprojetointegrador.enums.StatusEnum;
import br.com.meli.dhprojetointegrador.exception.PurchaseOrderNotFoundException;
import br.com.meli.dhprojetointegrador.repository.OrderRepository;
import br.com.meli.dhprojetointegrador.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;


    /**
     * @Author: David
     * @Methodo: Mudar Cart para Aberto ou Finalizado na Order
     * @Description: Modifique o pedido existente. torná-lo do tipo de carrinho para modificar - ABERTO/FINALIZADO
     * @param idorder
     * @return
     */
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


    /**
     * @Author: David
     * @Methodo: Listar produtos em seus determinados lotes
     * @Description: Veja uma lista de produtos com todos os lotes onde aparece. Se a lista não existir, ela deve retornar um “404 Not Found”.
     * @return
     */
    @Override
    public List<Product> listaProdutosLotes() {

        List<Product> products = productRepository.findAll();
        if(products == null) {
            return new ArrayList<Product>();
        }
        return products;
    }


}
