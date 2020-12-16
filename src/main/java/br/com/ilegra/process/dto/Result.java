package br.com.ilegra.process.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Result {

	private List<Seller> sellers;

	private List<Client> clients;

	private List<Sale> sales;

	private String pathFileIn;

	private String fileName;

	private Report report;

}
