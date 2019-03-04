package com.example.demo;

import java.util.HashMap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Cursor;

@SpringBootApplication
public class SpringbootCsvImportRethinkdbP2Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootCsvImportRethinkdbP2Application.class, args);
	}

	public static final RethinkDB r = RethinkDB.r;
	
	@Override
	public void run(String... args) {
		ObjectMapper mapper = new ObjectMapper();

		Connection conn = r.connection().hostname("localhost").port(28015).connect();

		Cursor cursor = r.table("mageobject").filter(row -> row.g("status").eq("done")).changes().run(conn);
		for (Object doc : cursor) {
			System.out.println(doc);
		}
	    cursor.close();
	    conn.close();
	}
}
