package br.com.ilegra.process.dto;

import java.text.ParseException;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Client{

	private String cnpj;

	private String name;

	private String bussinessArea;

	public static Client clientBuilder (List<String> tokens) throws ParseException {
		return Client.builder()
				.cnpj(tokens.get(1))
				.name(tokens.get(2))
				.bussinessArea(tokens.get(3))
				.build();
	}
}
