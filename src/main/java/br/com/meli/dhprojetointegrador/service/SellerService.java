package br.com.meli.dhprojetointegrador.service;

import br.com.meli.dhprojetointegrador.dto.request.NewProductRequest;
import br.com.meli.dhprojetointegrador.dto.response.FullProductResponse;
import br.com.meli.dhprojetointegrador.entity.Category;
import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.entity.Seller;
import br.com.meli.dhprojetointegrador.exception.BadRequestException;
import br.com.meli.dhprojetointegrador.exception.NotfoundException;
import br.com.meli.dhprojetointegrador.exception.ProductNotFoundException;
import br.com.meli.dhprojetointegrador.exception.ResourceNotFound;
import br.com.meli.dhprojetointegrador.repository.CategoryRepository;
import br.com.meli.dhprojetointegrador.repository.ProductRepository;
import br.com.meli.dhprojetointegrador.repository.SellerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SellerService {

    private ProductRepository productRepository;
    private SellerRepository sellerRepository;
    private CategoryRepository categoryRepository;

    private Seller checkSeller(Long sellerId) {
        try {
            Seller seller = sellerRepository.getById(sellerId);
            System.out.println(seller);
            return seller;
        } catch (EntityNotFoundException e) {
            throw new NotfoundException("Seller not found");
        }
    }

    private Category checkCategory(Long categoryId) {
        try {
            Category category = categoryRepository.getById(categoryId);
            System.out.println(category);
            return category;
        } catch (EntityNotFoundException e) {
            throw new NotfoundException("Category not found");
        }
    }

    private FullProductResponse buildAndCreateProduct(NewProductRequest input, Seller seller, Category category) {
        Product product = Product.builder()
                .name(input.getName())
                .price(input.getPrice())
                .volume(input.getVolume())
                .category(category)
                .seller(seller)
                .build();
        try {
            return fullProductResponseBuilder(productRepository.save(product));
        } catch (Exception e) {
            throw new BadRequestException("There was a problem creating the product on the database");
        }
    }

    public FullProductResponse fullProductResponseBuilder(Product product) {
        return FullProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .volume(product.getVolume())
                .category_id(product.getCategory().getId())
                .seller_id(product.getSeller().getId())
                .build();
    }

    /**
     * Author: Bruno Mendes
     * Method: createProduct
     * Description: valida o seller e a categoria e cria um produto no banco de dados
     **/

    public FullProductResponse createProduct(NewProductRequest input) {
        Seller seller = checkSeller(input.getSellerId());
        Category category = checkCategory(input.getCategoryId());
        return buildAndCreateProduct(input, seller, category);
    }

    /**
     * Author: Bruno Mendes
     * Method: getAllProducts
     * Description: valida se um vendedor existe e retorna uma lista com seus produtos
     **/
    public List<FullProductResponse> getAllProducts(Long sellerId) {
        Seller seller = checkSeller(sellerId);
        return productRepository.findBySeller(seller).stream().map(p -> fullProductResponseBuilder(p)).collect(Collectors.toList());
    }

    /**
     * Author: Bruno Mendes
     * Method: getAllProductsByName
     * Description: valida se um vendedor existe e retorna uma lista de produtos com determinando nome
     **/
    public List<FullProductResponse> getAllProductsByName(Long sellerId, String name) {
        checkSeller(sellerId);
        try{
            return productRepository.findBySellerIdAndAndName(sellerId, name).stream().map(p -> fullProductResponseBuilder(p)).collect(Collectors.toList());
        } catch ( Exception e) {
            throw new ProductNotFoundException("Produto nao encontrado");
        }
    }

    /**
     * Author: Bruno Mendes
     * Method: updateProduct
     * Description: Verifica se determinado produto existe e atualiza seu preco e nome se forem passados como parametro
     **/
    public FullProductResponse updateProduct(Long productId, String name, BigDecimal price) {
        try {
            Product product = productRepository.findById(productId).get();
            if (price != null) {
                product.setPrice(price);
            }
            if (name != null) {
                product.setName(name);
            }
            return fullProductResponseBuilder(productRepository.save(product));
        } catch (NoSuchElementException e) {
            throw new ProductNotFoundException("Produto não encontrado na base de dados");
        }
    }
}
