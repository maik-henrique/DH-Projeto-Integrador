package br.com.meli.dhprojetointegrador.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.meli.dhprojetointegrador.dto.response.ProductByWarehouseResponse;
import br.com.meli.dhprojetointegrador.dto.response.WarehouseQuantity;
import br.com.meli.dhprojetointegrador.entity.BatchStock;
import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.enums.CategoryEnum;
import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;
import br.com.meli.dhprojetointegrador.repository.ProductRepository;
import br.com.meli.dhprojetointegrador.service.validator.ValidadeProduct;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    private ValidadeProduct validateProduct;

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
    public List<Product> returnAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Author: Matheus Guerra
     * Method: Buscar todos os produtos de uma certa categoria
     * Description: Serviço responsavel por retornar uma lista com todos os produtos
     * presentes
     * na aplicação filtrados por categoria;
     *
     * @param category Um dos 3 valores possiveis para o atributo "name" da Class
     *                 Category:
     *                 FS,
     *                 RF,
     *                 FF
     *
     * @return Se existir, retorna lista de produtos filtrados por categoria
     */
    public List<Product> returnProductsByCategory(String category) {
        return productRepository.findByCategory_Name(CategoryEnum.valueOf(category));
    }

    /**
     * Author: Bruno Mendes
     * Method: getProductByWarehouse
     * Description: Busca os produtos e associação com cada warehouse e soma o total
     * de produtos em cada warehouse
     * 
     * @return ProductByWarehouseResponse
     */
    public ProductByWarehouseResponse getProductByWarehouse(Long id) {
        Product product = validateProduct.validateQuantity(1, id);
        List<WarehouseQuantity> warehouseQuantities = new ArrayList<>();
        Set<BatchStock> batchStockSet = product.getBatchStockList();
        batchStockSet.forEach(b -> {
            WarehouseQuantity warehouseQuantity = WarehouseQuantity.builder()
                    .totalQuantity(b.getCurrentQuantity())
                    .warehouseCode(b.getInboundOrder().getSection().getWarehouse().getId())
                    .build();
            warehouseQuantities.add(warehouseQuantity);
        });
        List<WarehouseQuantity> warehouseQuantitiesFinal = new ArrayList<>();
        warehouseQuantities.stream().forEach(w -> {
            if (warehouseQuantitiesFinal.stream().anyMatch(ww -> ww.getWarehouseCode().equals(w.getWarehouseCode()))) {
                WarehouseQuantity existing = warehouseQuantitiesFinal.stream()
                        .filter(ww -> ww.getWarehouseCode().equals(w.getWarehouseCode())).findFirst().get();
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
     * Author: Pedro Dalpa
     * Method: orderProductByPrice
     * Description: Retorna todos os produtos ordenados pelo preço crescente ou
     * decrescente e entre o intervalo de preço determinado
     *
     * @param price
     * @param minValue
     * @param maxValue
     * @return Se existir, retorna lista de produtos filtrados por categoria
     */
    public List<Product> orderProductsByPrice(Direction price, BigDecimal minValue, BigDecimal maxValue) {

        Sort sort = Sort.by(price, "price");
        return productRepository.orderProductByPrice(minValue, maxValue, sort);

    }

    /**
     * Author: Pedro Dalpa
     * Method: listByName
     * Description: Retorna todos os produtos filtrados pelo nome
     *
     * @param name
     * 
     * @return Se existir, retorna lista de produtos que contenham a string passada
     *         em alguma parte do nome
     */
    public List<Product> findByName(String name) {

        return productRepository.findByNameContainingIgnoreCase(name);

    }

    /**
     * Author: Pedro Dalpa
     * Method: listByBrand
     * Description: Retorna todos os produtos filtrados pela marca
     *
     * @param brand
     * 
     * @return Se existir, retorna lista de produtos que contenham a string passada
     *         em alguma parte da marca
     */
    public List<Product> findByBrand(String brand) {

        return productRepository.findByBrandContainingIgnoreCase(brand);

    }

}
