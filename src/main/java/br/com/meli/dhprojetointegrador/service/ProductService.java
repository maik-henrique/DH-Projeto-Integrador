package br.com.meli.dhprojetointegrador.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import br.com.meli.dhprojetointegrador.dto.response.ProductByWarehouseResponse;
import br.com.meli.dhprojetointegrador.dto.response.WarehouseQuantityResponse;
import br.com.meli.dhprojetointegrador.entity.BatchStock;
import br.com.meli.dhprojetointegrador.entity.Seller;

import br.com.meli.dhprojetointegrador.service.validator.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.enums.CategoryEnum;
import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;
import br.com.meli.dhprojetointegrador.repository.ProductRepository;
import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    private ProductValidator validateProduct;

    //@Cacheable(value = "findProductById", key = "#id")
    public Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new BusinessValidatorException(String.format("Product with id %d not found", id)));
    }

    /**
     * Author: Mariana Galdino
     * Method: Buscar todos os produtos na lista
     * Description: Serviço responsavel por retornar uma lista com todos os produtos
     * presentes na aplicação;
     * 
     * @return lista de produtos
     */
    //@Cacheable(value = "returnAllProducts")
    public List<Product> returnAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Author: Matheus Guerra
     * Method: Buscar todos os produtos de uma certa categoria
     * Description: Serviço responsavel por retornar uma lista com todos os produtos presentes
     * na aplicação filtrados por categoria;
     *
     * @param category Um dos 3 valores possiveis para o atributo "name" da Class Category:
     *                 FS,
     *                 RF,
     *                 FF
     *
     * @return Se existir, retorna lista de produtos filtrados por categoria
     */
    //@Cacheable(value = "returnProductsByCategory", key = "#category")
    public List<Product> returnProductsByCategory(String category ){
        return productRepository.findByCategory_Name(CategoryEnum.valueOf(category));
    }

    /**
     * Author: Bruno Mendes
     * Method: getProductByWarehouse
     * Description: Busca os produtos e associação com cada warehouse e soma o total de produtos em cada warehouse
     * @return ProductByWarehouseResponse
     */
    //@Cacheable(value = "getProductByWarehouse", key = "#id")
    public ProductByWarehouseResponse getProductByWarehouse(Long id) {
        Product product = validateProduct.validateQuantity(1, id);
        List<WarehouseQuantityResponse> warehouseQuantities = new ArrayList<>();
        Set<BatchStock> batchStockSet = product.getBatchStockList();
        batchStockSet.forEach(b-> {
            WarehouseQuantityResponse warehouseQuantityResponse = WarehouseQuantityResponse.builder()
                    .totalQuantity(b.getCurrentQuantity())
                    .warehouseCode(b.getInboundOrder().getSection().getWarehouse().getId())
                    .build();
            warehouseQuantities.add(warehouseQuantityResponse);
        });
        List<WarehouseQuantityResponse> warehouseQuantitiesFinal = new ArrayList<>();
        warehouseQuantities.stream().forEach( w -> {
            if (warehouseQuantitiesFinal.stream().anyMatch(ww-> ww.getWarehouseCode().equals(w.getWarehouseCode()))) {
                WarehouseQuantityResponse existing = warehouseQuantitiesFinal.stream().filter(ww-> ww.getWarehouseCode().equals(w.getWarehouseCode())).findFirst().get();
                existing.setTotalQuantity(existing.getTotalQuantity() + w.getTotalQuantity());
            } else {
                warehouseQuantitiesFinal.add(w);
            }
        });
        return ProductByWarehouseResponse.builder()
                .productId(id)
                .warehouses(warehouseQuantitiesFinal)
                .build();
    }

    /**
     * Author: Matheus Guerra
     * Method: associa um Seller e persiste a instancia da Class Product no banco de dados
     * @param product novo produto a ser persistido no banco de dados
     * @param seller seller existente no banco de dados que sera associado a um novo produto
     * @return instancia da Class product apos a persistencia no banco de dados
     */
    public Product saveProductWithSeller(Product product, Seller seller){
        product.setSeller(seller);

        return productRepository.save(product);
    }

    /**
     * Author: Matheus Guerra
     * Method: atualiza atributo statusProduct de uma instancia da Class Product persistida no banco de dados
     * @param productId id do product existe a ser alterado
     * @param sellerId id do seller existente associado ao produto a ser alterado
     * @param newStatus novo valor de status para o produto
     * @return product com o valor do atributo statusProduct alterado
     */
    public Product putProductStatus(Long productId, Long sellerId, Boolean newStatus){
        Product product = productRepository.findByIdAndSeller_Id(productId, sellerId);
        product.setStatusProduct(newStatus);

        return productRepository.save(product);
    }
}
