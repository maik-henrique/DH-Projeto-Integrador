package br.com.meli.dhprojetointegrador.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.meli.dhprojetointegrador.dto.request.InboundOrderPostRequest;
import br.com.meli.dhprojetointegrador.dto.request.InboundOrderUpdateRequest;
import br.com.meli.dhprojetointegrador.dto.response.InboundOrderResponse;
import br.com.meli.dhprojetointegrador.entity.InboundOrder;
import br.com.meli.dhprojetointegrador.service.InboundOrderService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/fresh-products/inboundorder")
@AllArgsConstructor
public class InboundOrderController {

  private final InboundOrderService inboundOrderService;
  private final ModelMapper modelMapper;

  @PutMapping
  public ResponseEntity<?> update(@RequestBody InboundOrderUpdateRequest inboundOrderUpdateRequest) {
    InboundOrder inboundOrder = modelMapper.map(inboundOrderUpdateRequest, InboundOrder.class);
    InboundOrder updatedInboundOrder = inboundOrderService.update(inboundOrder);
    InboundOrderResponse inboundOrderResponse = modelMapper.map(updatedInboundOrder, InboundOrderResponse.class);

    return ResponseEntity.ok().body(inboundOrderResponse);
  }

  @PostMapping
  public ResponseEntity<InboundOrderResponse> create(@RequestBody InboundOrderPostRequest inboundOrderPostRequest) {
    InboundOrder inboundOrder = modelMapper.map(inboundOrderPostRequest, InboundOrder.class);

    InboundOrder createInboundOrder = inboundOrderService.create(inboundOrder);
    InboundOrderResponse inboundOrderResponse = modelMapper.map(createInboundOrder, InboundOrderResponse.class);
    return ResponseEntity.ok().body(inboundOrderResponse);
  }

}
