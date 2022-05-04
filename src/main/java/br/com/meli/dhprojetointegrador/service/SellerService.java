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

    public Seller findSellerById(Long id) {
        return sellerRepository.findById(id)
                .orElseThrow(() -> new BusinessValidatorException(String.format("Seller with id %d not found", id)));
    }

    public Seller saveSeller(Seller seller){

        return sellerRepository.save(seller);

    }

    public Seller putSellerName(Long id, String newName){

        Seller seller = sellerRepository.getById(id);
        seller.setName(newName);

        return sellerRepository.save(seller);
    }

    public Seller putSellerAccountStatus(Long id, Boolean newStatus){

        Seller seller = sellerRepository.getById(id);
        seller.setStatusActiveAccount(newStatus);

        return sellerRepository.save(seller);

    }





}
