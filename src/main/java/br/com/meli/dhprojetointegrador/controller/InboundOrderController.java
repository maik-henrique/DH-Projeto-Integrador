package br.com.meli.dhprojetointegrador.controller;

import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

	/**
	 * @Author: Maik
	 *
	 * Endpoint for updating InboundOrders, it assumes that the nested objects
	 * already exists, such as Product, Section, etc. If it is not able to find
	 * them, it will throw a BusinessValidationException, which will then translate
	 * into a status 422 response.
	 * 
	 * inboundOrderUpdateRequest contract of the inbound order that needs to
	 *                                  be updated
	 * @return inboud order updated or exception in case it does not fulfill the
	 *         request
	 */
	@PutMapping()
	public ResponseEntity<?> update(@Valid @RequestBody InboundOrderUpdateRequest inboundOrderUpdateRequest) {
		InboundOrder inboundOrder = modelMapper.map(inboundOrderUpdateRequest, InboundOrder.class);
		InboundOrder updatedInboundOrder = inboundOrderService.update(inboundOrder);
		InboundOrderResponse inboundOrderResponse = modelMapper.map(updatedInboundOrder, InboundOrderResponse.class);

		return ResponseEntity.ok().body(inboundOrderResponse);
	}

	/**
	 * Author: Pedro Dalpa
	 * Method: create
	 * Description: salva o inbound order e cria os registros no stock conforme
	 * necessário
	 * 
	 * inboundOrder an instance of InboundOrder to create
	 * @return instance of InboundOrder created
	 *
	 */
	@PostMapping
	public ResponseEntity<InboundOrderResponse> create(
			@Valid @RequestBody InboundOrderPostRequest inboundOrderPostRequest) {
		InboundOrder inboundOrder = modelMapper.map(inboundOrderPostRequest, InboundOrder.class);
		InboundOrder createInboundOrder = inboundOrderService.create(inboundOrder);
		InboundOrderResponse inboundOrderResponse = modelMapper.map(createInboundOrder, InboundOrderResponse.class);
		return ResponseEntity.ok().body(inboundOrderResponse);
	}

}
