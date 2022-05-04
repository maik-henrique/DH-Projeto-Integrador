package br.com.meli.dhprojetointegrador.controller;


import br.com.meli.dhprojetointegrador.dto.response.BuyerDTO;
import br.com.meli.dhprojetointegrador.entity.Buyer;
import br.com.meli.dhprojetointegrador.entity.PurchaseOrder;
import br.com.meli.dhprojetointegrador.enums.StatusEnum;
import br.com.meli.dhprojetointegrador.service.BuyerService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/fresh-products/buyer")
@AllArgsConstructor
public class BuyerController {


    @Autowired
    private BuyerService buyerService;

    private final ModelMapper modelMapper;


    /**
     * @Author: David
     * @Description: Listar todas as purchaseOrder referentes ao Buyer
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<List<PurchaseOrder>> listAllPurchases(@PathVariable Long id) {
        List<PurchaseOrder> purchaseOrders = buyerService.listAllPurchases(id);
        return ResponseEntity.ok(purchaseOrders);
    }



    /**
     * @Author: David
     * @Description: Listar todas as purchaseOrder referentes ao Buyer com um mesmo Status
     * @return
     */
    @GetMapping()
    public ResponseEntity<List<PurchaseOrder>> listAllPurchasesWithStatus(@RequestParam String id,
                                                                          @RequestParam String status) {
        List<PurchaseOrder> purchaseOrders = buyerService.listAllPurchasesWithStatus(Long.valueOf(id), StatusEnum.valueOf(status));
        return ResponseEntity.ok((purchaseOrders));
    }


    /**
     * @Author: David
     * @Methodo:
     * @Description: Alterar os dados do buyer, pelo email/nome
     * @return
     */
    @PutMapping()
        public ResponseEntity<?> updateDataBuyer(@Valid @RequestParam String id,
                                                 @RequestParam String name,
                                                 @RequestParam String email){

        Buyer updateBuyer = buyerService.updateDataBuyer(Long.valueOf(id), name, email);

        return ResponseEntity.ok().body(updateBuyer);

    }


    /**
     * @Author: David
     * @Description: Desativar a conta a partir de email e password, setando Ativo e Inativo
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateBuyer(@PathVariable Long id) {
        buyerService.deactivateBuyer(id);
        return ResponseEntity.accepted().build();
    }



}
