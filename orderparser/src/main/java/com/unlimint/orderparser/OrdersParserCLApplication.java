package com.unlimint.orderparser;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.unlimint.orderparser.parser.Parser;
import com.unlimint.orderparser.reader.Reader;

@Component
public class OrdersParserCLApplication implements CommandLineRunner{
 
	BlockingQueue<Map<String,List<String>>> blockingQueue; 
	
	@Override
	public void run(String... args) throws Exception {		
		File[] files = null;
		if(args !=null && args.length >0){
			files = getFileList(args);
			blockingQueue = new ArrayBlockingQueue<>(files.length);
		}
		
		if(files!=null && blockingQueue!=null) {
			
			ExecutorService executoerService = Executors.newFixedThreadPool(2);
			
			Reader reader = new Reader(files, blockingQueue);
			Thread readerThread = new Thread(reader);
			readerThread.start();
			
			Parser parser = new Parser( blockingQueue);
			Thread parserThread = new Thread(parser);
			parserThread.start();
			
			executoerService.execute(reader);
			executoerService.execute(parser);
			
			executoerService.shutdown();
		}
	}
	
	private File[] getFileList(String[] args) {
		File[] files = new File[args.length];
		for(int i=0;i<args.length;i++) {
			files[i] = new File(args[i]);
		}
		return files;
	}
}
