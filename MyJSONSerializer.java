package com.cinco;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class MyJsonSerializer implements Serializer {
	
   private final Gson gson;
   
   public MyJsonSerializer() {
       this.gson = new GsonBuilder()
               .setPrettyPrinting()
               .registerTypeAdapter(LocalDate.class,
                       (com.google.gson.JsonSerializer<LocalDate>) (src, typeOfSrc, context)
                               -> new JsonPrimitive(src.toString()))
               .create();
   }
   @Override
   public void serializePersons(List<Person> persons, String outputPath) {
       JsonObject root = new JsonObject();
       root.add("persons", gson.toJsonTree(persons));
       writeJson(root, outputPath);
   }
   @Override
   public void serializeCompanies(List<Company> companies, String outputPath) {
       JsonObject root = new JsonObject();
       root.add("companies", gson.toJsonTree(companies));
       writeJson(root, outputPath);
   }
   @Override
   public void serializeItems(List<Item> items, String outputPath) {
       JsonObject root = new JsonObject();
       root.add("items", gson.toJsonTree(items));
       writeJson(root, outputPath);
   }
   private void writeJson(JsonObject root, String outputPath) {
       try (FileWriter writer = new FileWriter(outputPath)) {
           gson.toJson(root, writer);
       } catch (IOException e) {
           throw new RuntimeException("Error writing JSON file: " + outputPath, e);
       }
   }
}
