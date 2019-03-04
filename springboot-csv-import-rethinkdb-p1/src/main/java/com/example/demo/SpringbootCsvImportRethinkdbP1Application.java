package com.example.demo;

import java.util.HashMap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.model.MaGeObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Cursor;

@SpringBootApplication
public class SpringbootCsvImportRethinkdbP1Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootCsvImportRethinkdbP1Application.class, args);
	}

	public static final RethinkDB r = RethinkDB.r;

	@Override
	public void run(String... args) {
		ObjectMapper mapper = new ObjectMapper();

		Connection conn = r.connection().hostname("localhost").port(28015).connect();

		Cursor cursor = r.table("mageobject").run(conn);
		for (Object doc : cursor) {

			// MaGeObject mageObj = new MaGeObject();
			HashMap mageObj = new HashMap();
			try {
				mageObj = (HashMap) doc;
				mageObj.put("stage", "P1");
				mageObj.put("status", "done");
				// mageObj.setStatus("done");
				r.table("mageobject").insert(mageObj).run(conn);
			} catch (Exception ioex) {
				mageObj.put("stage", "P1");
				mageObj.put("status", "done");
				ioex.printStackTrace();
			}

			try {
				System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(doc));
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        cursor.close();
        conn.close();
	}

}
