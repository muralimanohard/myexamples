package com.example.demo;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.model.MaGeObject;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Cursor;


public class SpringbootCsvImportRethinkdbApplication2 implements CommandLineRunner {
	
	public static final RethinkDB r = RethinkDB.r;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootCsvImportRethinkdbApplication2.class, args);
	}

	@Override
    public void run(String...args) throws Exception {
		CsvMapper csvMapper = new CsvMapper();
		CsvSchema csvSchema = csvMapper.typedSchemaFor(MaGeObject.class).withHeader();
		List list = new CsvMapper().readerFor(MaGeObject.class)
				.with(csvSchema.withColumnSeparator(CsvSchema.DEFAULT_COLUMN_SEPARATOR))
				.readValues(SpringbootCsvImportRethinkdbApplication2.class.getClassLoader().getResource("trade.csv")).readAll();
		
		Connection conn = r.connection().hostname("localhost").port(28015).connect();


		//r.db("test").tableDrop("mageobject").run(conn);
		
		List tables= r.db("test").tableList().run(conn);
		
		
		
		if(!tables.contains("mageobject")) {
			r.db("test").tableCreate("mageobject").run(conn);
			for (Object obj :list) {
				MaGeObject mageObj = (MaGeObject)obj;
				r.table("mageobject").insert(mageObj).run(conn);
			}
		} else {
			for (Object obj :list) {
				MaGeObject mageObj = (MaGeObject)obj;
				r.table("mageobject").replace(mageObj).run(conn);
			}
		}

		
//		if(!tables.contains("mageobject")) {
//			r.db("test").tableCreate("mageobject").run(conn);
//			for (Object obj :list) {
//				MaGeObject mageObj = (MaGeObject)obj;
//				r.table("mageobject").insert(r.hashMap("isin", mageObj.getIsin())
//						.with("totalSharesPrice", mageObj.getTotalSharesPrice())
//						.with("timestamp", ZonedDateTime.now())
//						.with("price", mageObj.getPrice())).run(conn);
//			}
//		}
		
		
//			for (Object obj :list) {
//				MaGeObject mageObj = (MaGeObject)obj;
//				r.table("mageobject").insert(r.hashMap("isin", mageObj.getIsin())
//						.with("totalSharesPrice", mageObj.getTotalSharesPrice())
//						.with("timestamp", ZonedDateTime.now())
//						.with("price", mageObj.getPrice())).run(conn);
//			}
//		} else {
//			for (Object obj :list) {
//				MaGeObject mageObj = (MaGeObject)obj;
//				r.table("mageobject").update(r.hashMap("isin", mageObj.getIsin())
//						.with("totalSharesPrice", mageObj.getTotalSharesPrice())
//						.with("timestamp", ZonedDateTime.now())
//						.with("price", mageObj.getPrice())).run(conn);
//			}
//		}

		
//		if(!tables.contains("mageobject")) {
//			r.db("test").tableCreate("mageobject").run(conn);
//			for (Object obj :list) {
//				MaGeObject mageObj = (MaGeObject)obj;
//				r.table("mageobject").insert(r.hashMap("isin", mageObj.getIsin())
//						.with("totalSharesPrice", mageObj.getTotalSharesPrice())
//						.with("timestamp", ZonedDateTime.now())
//						.with("price", mageObj.getPrice())).run(conn);
//			}
//		} else {
//			for (Object obj :list) {
//				MaGeObject mageObj = (MaGeObject)obj;
//				r.table("mageobject").update(r.hashMap("isin", mageObj.getIsin())
//						.with("totalSharesPrice", mageObj.getTotalSharesPrice())
//						.with("timestamp", ZonedDateTime.now())
//						.with("price", mageObj.getPrice())).run(conn);
//			}
//		}
		
		//r.table("mageobject").sync().run(conn);

//		for (Object obj :list) {
//			MaGeObject mageObj = (MaGeObject)obj;
//			r.table("mageobject").insert(r.hashMap("id", mageObj.getId())
//					.with("amount", mageObj.getAmount())
//					.with("currency", mageObj.getCurrency())).run(conn);
//		}
		
		for (int i = 0; i < list.size(); i++) {
			System.out.printf(" MaGeObject Row [%d] : %s \n", i + 1, list.get(i));
		}

		// Retrieve all documents
        System.out.println("Fetching data from RethinkDB...");
        Cursor cursor = r.table("mageobject").run(conn);
        //conn.noreplyWait();
        for (Object doc : cursor) {
            System.out.println(doc);
        }
        System.out.println("Fetching data successfully from RethinkDB!");
        System.out.println();
        cursor.close();
        conn.close();
}
}	
