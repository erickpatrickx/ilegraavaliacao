package br.com.ilegra.process.processor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.ilegra.process.dto.Report;
import br.com.ilegra.process.dto.Result;
import lombok.extern.slf4j.Slf4j;

@Component
@StepScope
@Slf4j
public class WriterBatch implements ItemWriter<Result> {

	@Value("${out.folder}")
	private String PATH_OUT;

	@Override
	public void write(final List<? extends Result> items) throws Exception {
		if (items.size() > 0) {
			items.forEach(result -> {
				try {

					if (!Files.exists(Paths.get(PATH_OUT)))
						Files.createDirectories(Paths.get(PATH_OUT));

						Files.write(Paths.get(PATH_OUT.concat("out_".concat(result.getFileName()))),
								convert(result.getReport()).getBytes());
						Files.deleteIfExists(Paths.get(result.getPathFileIn()));

				} catch (IOException e) {
					log.error("File output problem", e);
					e.printStackTrace();
				}
			});
		}
	}

	public String convert(Report report) {
		StringBuilder sb = new StringBuilder();
		if (report != null) {
			sb.append("Quantidade de clientes no arquivo de entrada: ");
			sb.append(report.getClientQtd());
			sb.append("\n");
			sb.append("Quantidade de vendedores no arquivo de entrada: ");
			sb.append(report.getSellerQtd());
			sb.append("\n");
			sb.append("ID da venda mais cara: ");
			sb.append(report.getMostExpensiveSale());
			sb.append("\n");
			sb.append("Pior vendedor: ");
			sb.append(report.getBadSeller());
			sb.append("\n");
			return sb.toString();
		}
		return "No report";
	}
}