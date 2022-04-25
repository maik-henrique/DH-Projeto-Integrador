package br.com.meli.dhprojetointegrador.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.meli.dhprojetointegrador.dto.request.InboundPostRequestBody;
import br.com.meli.dhprojetointegrador.entity.Agent;
import br.com.meli.dhprojetointegrador.entity.BatchStock;
import br.com.meli.dhprojetointegrador.entity.InboundOrder;
import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.entity.Section;
import br.com.meli.dhprojetointegrador.exception.BadRequestException;
import br.com.meli.dhprojetointegrador.mapper.BatchStockMapper;
import br.com.meli.dhprojetointegrador.repository.AgentRepository;
import br.com.meli.dhprojetointegrador.repository.BatchStockRepository;
import br.com.meli.dhprojetointegrador.repository.InboundOrderRepository;
import br.com.meli.dhprojetointegrador.repository.ProductRepository;
import br.com.meli.dhprojetointegrador.repository.SectionRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InboundOrderService {

  private final InboundOrderRepository inboundOrderRepository;
  private final SectionRepository sectionRepository;
  private final AgentRepository agentRepository;
  private final BatchStockRepository batchStockRepository;
  private final ProductRepository productRepository;

  public InboundOrder create(InboundPostRequestBody inboundPostRequestBody) {
    InboundOrder inboundOrder = new InboundOrder();

    Section section = sectionRepository.findById(inboundPostRequestBody.getSectionId())
        .orElseThrow(() -> new BadRequestException("Section id not found"));

    Agent agent = agentRepository.findById(inboundPostRequestBody.getAgentId())
        .orElseThrow(() -> new BadRequestException("Agent id not found"));

    LocalDate convertedDate = inboundPostRequestBody.getOrderDate().toInstant().atZone(ZoneId.systemDefault())
        .toLocalDate();

    inboundOrder.setSection(section);
    inboundOrder.setOrderDate(convertedDate);
    inboundOrder.setAgent(agent);

    InboundOrder savedInboundOrder = inboundOrderRepository.save(inboundOrder);

    inboundPostRequestBody.getBatchStock().forEach(item -> {
      item.setInboundOrder(savedInboundOrder);
      Product product = productRepository.findById(item.getProduct_id())
          .orElseThrow(() -> new BadRequestException("Agent id not found"));
      item.setProducts(product);
    });

    List<BatchStock> batchStocks = BatchStockMapper.INSTANCE.toBatchStock(inboundPostRequestBody.getBatchStock());

    batchStockRepository.saveAll(batchStocks);

    return savedInboundOrder;
  }
}
