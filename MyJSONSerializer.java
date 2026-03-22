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

LATEST CODE
	package com.cinco;

/*
* An implementation of the Serializer interface that utilizes the Google Gson library to convert
* Java object collections into formatted JSON files.
*/

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MyJsonSerializer implements Serializer {
    
    private final Gson gson;
    
    public MyJsonSerializer() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class,
                        (com.google.gson.JsonSerializer<LocalDate>) (src, typeOfSrc, context)
                                -> new com.google.gson.JsonPrimitive(src.toString()))
                .create();
    }

    /*
     * Implements the interface requirement to convert a list of Person objects into a JSON file at the specified path.
     */
    @Override
    public void serializePersons(List<Person> persons, String outputPath) {
        writeJson(persons, outputPath);
    }

    /*
     * Implements the interface requirement to convert a list of Company objects into a JSON file at the specified path.
     */
    @Override
    public void serializeCompanies(List<Company> companies, String outputPath) {
        writeJson(companies, outputPath);
    }

    //Implements the interface requirement to convert a list of Item objects into a JSON file at the specified path.
    @Override
    public void serializeItems(List<Item> items, String outputPath) {
        writeJson(items, outputPath);
    }

    /*
     * centralizes the file-writing logic, using a try-with-resources block to handle the FileWriter and 
     * wrapping IOExceptions in a RuntimeException.
     */
    private void writeJson(Object data, String outputPath) {
        try (FileWriter writer = new FileWriter(outputPath)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            throw new RuntimeException("Error writing JSON file: " + outputPath, e);
        }
    }
}
