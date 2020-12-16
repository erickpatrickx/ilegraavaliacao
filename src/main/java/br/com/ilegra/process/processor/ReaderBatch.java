package br.com.ilegra.process.processor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.ilegra.process.dto.Client;
import br.com.ilegra.process.dto.Result;
import br.com.ilegra.process.dto.Sale;
import br.com.ilegra.process.dto.Seller;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@StepScope
@Component
public class ReaderBatch implements ItemReader<Result>, InitializingBean {

	private static final String FILE_SEPARATOR = "ç";
	private static final String SELLER_ID = "001";
	private static final String CLIENT_ID = "002";
	private static final String SALE_ID = "003";

	private String[] files;

	@Value("${in.folder}")
	private String PATH_IN;

	@Override
	public Result read() throws Exception {
		try {
			List<Seller> sellers = new ArrayList<>();
			List<Client> clients = new ArrayList<>();
			List<Sale> sales = new ArrayList<>();

			for (String fileName : files) {

				Path path = Paths.get(PATH_IN.concat(File.separator).concat(fileName));

				if (!Files.exists(path)) return  null;				
				
				try (Stream<String> stream = Files.lines(path)) {

					stream.forEach(s -> {
						try {
							List<String> tokens = Arrays.asList(s.split(FILE_SEPARATOR));
							if (tokens != null && tokens.size() == 4 && StringUtils.isNotBlank(tokens.get(0))) {
								if (tokens.get(0).equals(SELLER_ID)) {
									sellers.add(Seller.sellerBuilder(tokens));
								} else if (tokens.get(0).equals(CLIENT_ID)) {
									clients.add(Client.clientBuilder(tokens));
								} else if (tokens.get(0).equals(SALE_ID)) {
									sales.add(Sale.saleBuilder(tokens));
								}
							} else {
								log.error("Parse file error - Invalid file: ", s);
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
					});

				}

				return Result.builder().clients(clients).sellers(sellers).sales(sales).fileName(fileName)
						.pathFileIn(path.toString()).build();

			}

		} catch (IOException e) {
			log.error("expected exception: {}", e.getMessage());
		}

		return null;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		File file = new File(PATH_IN);
		files = file.list();
	}
}