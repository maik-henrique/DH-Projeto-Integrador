package br.com.meli.dhprojetointegrador.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.meli.dhprojetointegrador.dto.request.InboundPostRequestBody;
import br.com.meli.dhprojetointegrador.service.InboundOrderService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/fresh-products/inboundorder/")
@AllArgsConstructor
public class InboundOrderController {

  private final InboundOrderService inboundOrderService;

  @PostMapping
  public ResponseEntity<Void> create(@RequestBody InboundPostRequestBody body) {
    inboundOrderService.create(body);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
