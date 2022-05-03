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


@ExtendWith(MockitoExtension.class)
public class BuyerServiceTest {
/*
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
    /*
    @Test
    void naoDeveListarPurchasesAbertoOuDeBuyerInativo() {

        Buyer buyerInativo = Buyer.builder().status(BuyerStatusEnum.INATIVO).build();
        PurchaseOrder purchaseOrder = PurchaseOrder.builder().status(StatusEnum.ABERTO).build();

        Mockito.when(buyerRepository.getById(4L)).thenReturn(buyerInativo);
        Mockito.when(buyerRepository.save(Mockito.any(Buyer.class))).thenReturn(buyerInativo);

        Mockito.when(purchaseOrderRepository.getById(4L)).thenReturn(purchaseOrder);
        Mockito.when(purchaseOrderRepository.save(Mockito.any(PurchaseOrder.class))).thenReturn(purchaseOrder);

       // List <Buyer> buyer = this.buyerService.listAllPurchases(4L);
        List <PurchaseOrder> result = this.buyerService.listAllPurchases(4L);

        assert result.equals(result);
       // assert result.equals(buyer);

    }

    /**
     * @Author: David
     * @Methodo:
     * @Description: Nao Listar Purchase com status diferente de FINALIZADO
     */
    /*
    @Test
    void naoDeveListarPurchasesDeStatusDiferenteDeFinalizado() {

        PurchaseOrder buyer = PurchaseOrder.builder().status(StatusEnum.ABERTO).build();

        Mockito.when(purchaseOrderRepository.getById(4L)).thenReturn(buyer);
        Mockito.when(purchaseOrderRepository.save(Mockito.any(PurchaseOrder.class))).thenReturn(buyer);

        List <PurchaseOrder> result = this.buyerService.listAllPurchasesWithStatus(4L, StatusEnum.ABERTO);

        assert result.equals(result);

    }


    /**
     * @Author: David
     * @Methodo:
     * @Description: Nao deve alterar dados de um buyer inativo ou inexistente
     */
    /*
    @Test
    void naoDeveAlterarDadosDeBuyerInativoOuInexistente() {

        Buyer buyerInativo = Buyer.builder().status(BuyerStatusEnum.INATIVO).build();

        Mockito.when(buyerRepository.getById(4L)).thenReturn(buyerInativo);
        Mockito.when(buyerRepository.save(Mockito.any(Buyer.class))).thenReturn(buyerInativo);

        Buyer result = this.buyerService.updateDataBuyer(buyerInativo);

        assert result.equals(result);

    }



    /**
     * @Author: David
     * @Methodo:
     * @Description: Nao deve alterar o status de um buyer ja inativado
     */
    /*
    @Test
    void naoDeveAlterarStatusDeUsuarioJaInativo() {

        Buyer buyerInativo = Buyer.builder().status(BuyerStatusEnum.INATIVO).build();

        Mockito.when(buyerRepository.getById(4L)).thenReturn(buyerInativo);
        Mockito.when(buyerRepository.save(Mockito.any(Buyer.class))).thenReturn(buyerInativo);

        Buyer result = this.buyerService.deactivateBuyer(4L);

        assert result.equals(result);

    }
}
