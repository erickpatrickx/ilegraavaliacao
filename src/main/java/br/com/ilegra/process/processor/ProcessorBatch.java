package br.com.ilegra.process.processor;

import java.util.Comparator;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import br.com.ilegra.process.dto.Report;
import br.com.ilegra.process.dto.Result;
import br.com.ilegra.process.dto.Sale;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@StepScope
@Component
public class ProcessorBatch implements ItemProcessor<Result, Result> {

	@Override
	public Result process(Result result) throws Exception {
		Sale saleMax = result.getSales().stream().max(Comparator.comparing(Sale::getTotal)).orElse(null);
		Sale saleMin = result.getSales().stream().min(Comparator.comparing(Sale::getTotal)).orElse(null);

		Report report = Report.builder().clientQtd(result.getClients().size()).sellerQtd(result.getSellers().size())
				.mostExpensiveSale(saleMax != null ? saleMax.getId() : null)
				.badSeller(saleMin != null ? saleMin.getSalesManName() : null).build();

		result.setReport(report);

		return result;

	}
}