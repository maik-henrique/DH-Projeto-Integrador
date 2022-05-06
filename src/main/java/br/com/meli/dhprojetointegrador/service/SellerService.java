package br.com.meli.dhprojetointegrador.service;

import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.entity.Seller;
import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;
import br.com.meli.dhprojetointegrador.repository.SellerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SellerService {

    private SellerRepository sellerRepository;

    /**
     * Author: Matheus Guerra
     * Method: findSellerById
     * @param id id do Seller desejado
     * @return Caso o id seja valido, retorna o Seller presente no banco de dados correspondente ao id
     */
    public Seller findSellerById(Long id) {

        return sellerRepository.findById(id)
                .orElseThrow(() -> new BusinessValidatorException(String.format("Seller with id %d not found", id)));
    }

    /**
     * Author: Matheus Guerra
     * Method: persiste instancia da Class Seller no banco de dados
     * @param seller instancia da Class Seller que deseja-se cadastrar no banco de dados
     * @return o seller apos o processo de persistente no banco de dados
     */
    public Seller saveSeller(Seller seller){

        return sellerRepository.save(seller);
    }

    /**
     * Author: Matheus Guerra
     * Method: atualiza atributo name de uma instancia da Class Seller persistida no banco de dados
     * @param id id do Seller presente no banco de dados que deseja-se alterar
     * @param newName novo valor do atributo name para o Seller desejado
     * @return seller com o valor do atributo name alterado
     */
    public Seller putSellerName(Long id, String newName){

        Seller seller = sellerRepository.findById(id).get();
        seller.setName(newName);

        return sellerRepository.save(seller);
    }

    /**
     * Author: Matheus Guerra
     * Method: atualiza atributo statusActiveAccount de uma instancia da Class Seller persistida no banco de dados
     * @param id id do Seller presente no banco de dados que deseja-se alterar
     * @param newStatus novo valor do atributo statusActiveAccount para o Seller desejado
     * @return seller com o valor do atributo statusActiveAccount alterado
     */
    public Seller putSellerAccountStatus(Long id, Boolean newStatus){

        Seller seller = sellerRepository.findById(id).get();
        seller.setStatusActiveAccount(newStatus);

        return sellerRepository.save(seller);
    }

}
