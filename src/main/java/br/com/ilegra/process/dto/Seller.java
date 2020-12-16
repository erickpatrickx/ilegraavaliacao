package br.com.ilegra.process.dto;

import java.text.ParseException;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Seller {

	private String cpf;

	private String name;

	private Double salary;
	
	public static Seller sellerBuilder (List<String> tokens) throws ParseException {
		return Seller.builder()
				.cpf(tokens.get(1))
				.name(tokens.get(2))
				.salary(Double.parseDouble(tokens.get(3)))
				.build();
	}
}
