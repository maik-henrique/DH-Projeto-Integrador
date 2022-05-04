package br.com.meli.dhprojetointegrador.unit.service;


import br.com.meli.dhprojetointegrador.entity.Buyer;
import br.com.meli.dhprojetointegrador.entity.PurchaseOrder;
import br.com.meli.dhprojetointegrador.enums.BuyerStatusEnum;
import br.com.meli.dhprojetointegrador.enums.StatusEnum;
import br.com.meli.dhprojetointegrador.repository.BuyerRepository;
import br.com.meli.dhprojetointegrador.repository.PurchaseOrderRepository;
import br.com.meli.dhprojetointegrador.service.BuyerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class BuyerServiceTest {

    @InjectMocks
    private BuyerService buyerService;

    @Mock
    private BuyerRepository buyerRepository;

    @Mock
    private PurchaseOrderRepository purchaseOrderRepository;

    /**
     * @Author: David
     * @Methodo:
     * @Description: Nao listar uma Purchase com status Aberto ou de Buyer Inativo
     */
    @Test
    void naoDeveListarPurchasesAbertoOuDeBuyerInativo() {

        Buyer buyerAtivo = Buyer.builder().id(4L).name("Brunozera").status(BuyerStatusEnum.ATIVO).build();
        PurchaseOrder purchaseOrder = PurchaseOrder.builder().id(4L).buyer(buyerAtivo).status(StatusEnum.FINALIZADO).build();

        Mockito.when(purchaseOrderRepository.findByBuyerIdAndStatus(4L, StatusEnum.FINALIZADO)).thenReturn(List.of(purchaseOrder));
        List <PurchaseOrder> result = this.buyerService.listAllPurchases(4L);
        assert result.get(0).getId().equals(purchaseOrder.getId());
        assert result.get(0).getBuyer().equals(purchaseOrder.getBuyer());
    }

    /**
     * @Author: David
     * @Methodo:
     * @Description: Nao Listar Purchase com status diferente de FINALIZADO
     */
    @Test
    void naoDeveListarPurchasesDeStatusDiferenteDeFinalizado() {

        PurchaseOrder purchaseOrderFinalizado = PurchaseOrder.builder().id(4L).status(StatusEnum.FINALIZADO).build();
        PurchaseOrder purchaseOrderAberto = PurchaseOrder.builder().id(3L).status(StatusEnum.ABERTO).build();

        Mockito.when(purchaseOrderRepository.findByBuyerIdAndStatus(4L, StatusEnum.FINALIZADO)).thenReturn(List.of(purchaseOrderFinalizado));
        Mockito.when(purchaseOrderRepository.findByBuyerIdAndStatus(3L, StatusEnum.ABERTO)).thenReturn(List.of(purchaseOrderAberto));

        Mockito.when(purchaseOrderRepository.findByBuyerIdAndStatus(4L, StatusEnum.ABERTO)).thenReturn(List.of());
        Mockito.when(purchaseOrderRepository.findByBuyerIdAndStatus(3L, StatusEnum.FINALIZADO)).thenReturn(List.of());

        List <PurchaseOrder> result = this.buyerService.listAllPurchasesWithStatus(4L, StatusEnum.ABERTO);
        List <PurchaseOrder> result2 = this.buyerService.listAllPurchasesWithStatus(4L, StatusEnum.FINALIZADO);
        List <PurchaseOrder> result3 = this.buyerService.listAllPurchasesWithStatus(3L, StatusEnum.ABERTO);
        List <PurchaseOrder> result4 = this.buyerService.listAllPurchasesWithStatus(3L, StatusEnum.FINALIZADO);


        assert result.isEmpty();
        assert result2.size()==1;

        assert result4.isEmpty();
        assert result3.size()==1;

    }


    /**
     * @Author: David
     * @Methodo:
     * @Description: Nao deve alterar dados de um buyer inativo ou inexistente
     */
    @Test
    void deveAlterarDadosDeBuyerAtivo() {

        Buyer buyerAtivo = Buyer.builder().id(4L).name("Alexandre").email("meli@meli.com").status(BuyerStatusEnum.ATIVO).build();

        Buyer buyerAtivoUpdate = Buyer.builder().id(4L).name("Alexandre Fernandes").email("meli@meli.com.br").status(BuyerStatusEnum.ATIVO).build();

        Mockito.when(buyerRepository.findById(4L)).thenReturn(Optional.of(buyerAtivo));
        Mockito.when(buyerRepository.save(Mockito.any(Buyer.class))).thenReturn(buyerAtivoUpdate);

        Buyer result = this.buyerService.updateDataBuyer(4L, "Alexandre Fernandes", "meli@meli.com.br");

        assert result.getName().equals(buyerAtivoUpdate.getName());
        assert result.getEmail().equals(buyerAtivoUpdate.getEmail());
    }



    /**
     * @Author: David
     * @Methodo:
     * @Description: Nao deve alterar o status de um buyer ja inativado
     */
    @Test
    void deveAlterarStatusDeBuyerParaInativo() {

        Buyer buyerAtivo = Buyer.builder().id(4L).status(BuyerStatusEnum.ATIVO).build();

        Buyer buyerInativo = Buyer.builder().id(4L).status(BuyerStatusEnum.INATIVO).build();

        Mockito.when(buyerRepository.findById(4L)).thenReturn(Optional.of(buyerAtivo));
        Mockito.when(buyerRepository.save(Mockito.any(Buyer.class))).thenReturn(buyerInativo);

        Buyer result = this.buyerService.deactivateBuyer(4L);

        assert result.getStatus().equals(BuyerStatusEnum.INATIVO);

    }
}
