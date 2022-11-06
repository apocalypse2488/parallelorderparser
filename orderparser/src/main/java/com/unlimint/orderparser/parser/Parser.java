package com.unlimint.orderparser.parser;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unlimint.orderparser.entity.Order;
import com.unlimint.orderparser.exception.OrderConversionException;

public class Parser implements Runnable{
	volatile long orderIdSerialCounter = 0;
	ObjectMapper mapper = new ObjectMapper();
	BlockingQueue<Map<String,List<String>>> blockingQueue;
	
	public Parser(BlockingQueue<Map<String,List<String>>> blockingQueue) {
		this.blockingQueue = blockingQueue;
	}
	
	@Override
	public void run() {
		try {
			parseOrder();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public void parseOrder() throws IOException  {
		Map<String, List<String>> map;
		try {
			map = blockingQueue.take();
			if(map!=null) {
				for(Map.Entry<String, List<String>> entry: map.entrySet()) {
					List<String> list = entry.getValue();
					int orderLineCounter =1;
					for(String line : list) {
							
						Order order = null;
						try {
								if(ParserUtil.isValidJson(line, mapper)){
									order =ParserUtil.converJsonStringToOrder(line);
								}
								else{
									order =ParserUtil.converCSVStringToOrder(line);
								}
							} catch (OrderConversionException e) {
								order = getMalformedOrder(e.getMessage());
							}catch (Exception e) {
								order = getMalformedOrder(e.getMessage());
							}
						if(order !=null) {
							persistValidOrder(entry, orderLineCounter, order);
							++orderLineCounter;
							
						}	
					}
				}
			}
//			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private void persistValidOrder(Map.Entry<String, List<String>> entry, int orderLineCounter, Order order)
			throws JsonProcessingException {
		order.setId(Long.valueOf(++orderIdSerialCounter));
		order.setFileName(entry.getKey());
		order.setLine(BigInteger.valueOf(Long.valueOf(orderLineCounter)));
		
		String orderData = mapper.writeValueAsString(order);
		System.out.println(orderData);
	}

	private Order getMalformedOrder(String message) {
		Order order;
		order = new Order();
		order.setOrderId(-1);
		order.setResult(message);
		return order;
	}
}
