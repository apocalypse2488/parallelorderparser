package com.unlimint.orderparser.parser;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.unlimint.orderparser.entity.Order;
import com.unlimint.orderparser.exception.OrderConversionException;

public class ParserUtil {
	
	public static Order converCSVStringToOrder(String orderString)throws OrderConversionException{
		Order order =null;
		if(orderString != null && !orderString.isEmpty() && !orderString.isBlank()){
			order = new Order(orderString);
			order.setResult("OK");
		}
		return order;
	}

	public static Order converJsonStringToOrder(String orderString) throws JsonMappingException, JsonProcessingException,OrderConversionException {
		Order order =null;
		if(orderString != null && !orderString.isEmpty() && !orderString.isBlank()){
			JsonObject a = JsonParser.parseString(orderString).getAsJsonObject();
			order = new Order();
			order.setOrderId(a.get("orderId").getAsLong());
			order.setAmount(a.get("amount").getAsBigDecimal());
			order.setCurrency(a.get("currency").getAsString());
			order.setComment(a.get("comment").getAsString());
			order.setResult("OK");
		}
		return order;	
	}
	
	public static boolean isValidJson(String json, ObjectMapper mapper ) {
	    try {
	        mapper.readTree(json);
	    } catch (JacksonException e) {
	        return false;
	    }
	    return true;
	}
}
