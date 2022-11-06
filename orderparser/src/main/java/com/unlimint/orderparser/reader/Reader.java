package com.unlimint.orderparser.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class Reader implements Runnable{
		
	volatile long orderIdSerialCounter = 0;
	File[] files;
	BlockingQueue<Map<String,List<String>>> blockingQueue;
	
	public Reader(File[] files, BlockingQueue<Map<String,List<String>>> blockingQueue) {
		this.files = files;
		this.blockingQueue = blockingQueue;
	}

	public void readFileContent() throws IOException  {
		try {
			for(File file : files) {
				try {
					BufferedReader br = new BufferedReader(new FileReader(file));	
					String line;
					List<String> list = new ArrayList<>();
					Map<String,List<String>> map = new HashMap<>();
					while ((line = br.readLine()) != null) 
					{	
						list.add(line);
					}			
					map.put(file.getName(), list);
 					blockingQueue.put(map);
					br.close();
				} catch (IOException e) { 
					System.out.println(e.getMessage());
				}
//				Thread.sleep(1000);
			}
			
		}catch (Exception e) {
			System.out.println(e.getMessage());

		}
	}

	@Override
	public void run() {
		try {
			readFileContent()	;
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
}


