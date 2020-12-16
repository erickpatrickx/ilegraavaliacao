package br.com.ilegra.process.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Report{

	private Integer clientQtd;

	private Integer sellerQtd;

	private String mostExpensiveSale;

	private String badSeller;

}
