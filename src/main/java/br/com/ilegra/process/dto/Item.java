package br.com.ilegra.process.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Item {

	private String id;

	private Integer quantity;

	private Double price;

}
