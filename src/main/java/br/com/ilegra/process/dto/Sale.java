package br.com.ilegra.process.dto;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Sale {
	
	private static final String ITEM_SEPARATOR = ",";
	private static final String ITEM_VALUE_SPLIT = "-";

	private String id;

	private String salesManName;

	private List<Item> items;

	private Double total;

	
	public static Sale saleBuilder (List<String> tokens) throws ParseException {
		List<Item> items = new ArrayList<>();
		double totalSale = 0;
		String values = StringUtils.remove(StringUtils.remove(tokens.get(2), "["), "]");
		if (StringUtils.isNotBlank(values)) {
			List<String> itemList = Arrays.asList(values.split(ITEM_SEPARATOR));
			for (String value : itemList) {
				List<String> itemValues = Arrays.asList(value.split(ITEM_VALUE_SPLIT));
				items.add(Item.builder()
					.id(itemValues.get(0))
					.quantity(Integer.parseInt(itemValues.get(1)))
					.price(Double.parseDouble(itemValues.get(2)))
					.build());
				totalSale += Double.parseDouble(itemValues.get(2));
			}
		}
		return Sale.builder()
				.id(tokens.get(1))
				.items(items)
				.salesManName(tokens.get(3))
				.total(totalSale)
				.build();
	}

}
