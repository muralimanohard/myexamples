package com.example.demo;

import java.io.IOException;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.model.MaGeObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Cursor;

@SpringBootApplication
public class SpringbootCsvImportRethinkdbApplication implements CommandLineRunner {
	
	public static final RethinkDB r = RethinkDB.r;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootCsvImportRethinkdbApplication.class, args);
	}

	@Override
    public void run(String...args) {
		CsvMapper csvMapper = new CsvMapper();
		CsvSchema csvSchema = csvMapper.typedSchemaFor(MaGeObject.class).withHeader();
		List list = null;
			try {
				list = new CsvMapper().readerFor(MaGeObject.class)
						.with(csvSchema.withColumnSeparator(CsvSchema.DEFAULT_COLUMN_SEPARATOR))
						.readValues(SpringbootCsvImportRethinkdbApplication.class.getClassLoader().getResource("trade.csv")).readAll();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		
		Connection conn = r.connection().hostname("localhost").port(28015).connect();

		List tables= r.db("test").tableList().run(conn);
		
		if(!tables.contains("mageobject")) {
			//r.db("test").tableDrop("mageobject").run(conn);
			r.db("test").tableCreate("mageobject").run(conn);
		}
		
		//r.db("test").tableCreate("mageobject").run(conn);
		for (Object obj : list) {
			MaGeObject mageObj = null;
			try {
			mageObj = (MaGeObject)obj;
//			mageObj.setStage("P1");
//			mageObj.setStatus("done");
			r.table("mageobject").insert(mageObj).run(conn);
			} catch (Exception ioex) {
//				mageObj.setStage("P1");
//				mageObj.setStatus("error");
				ioex.printStackTrace();
			}
		}	

		ObjectMapper mapper = new ObjectMapper();
		  
		
		for (int i = 0; i < list.size(); i++) {
			System.out.printf(" MaGeObject Row [%d] : %s \n", i + 1, list.get(i));
		}

		// Retrieve all documents
		System.out.println("#######################################");
        System.out.println("Fetching data from RethinkDB...");
        System.out.println("#######################################");
        Cursor cursor = r.table("mageobject").run(conn);
        //conn.noreplyWait();
        for (Object doc : cursor) {
        	try {
				System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(doc));
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            //System.out.println(doc);
        }
        System.out.println("#######################################");
        System.out.println("Fetching data successfully from RethinkDB!");
        System.out.println("#######################################");
        cursor.close();
        conn.close();
}
}	
