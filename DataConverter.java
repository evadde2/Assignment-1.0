package com.cinco;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.File;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

public class DataConverter {

    public static void main(String[] args) {

        try {

            DataLoader loader = new DataLoader();

            loader.loadPersons("data/Persons.csv");
            loader.loadCompanies("data/Companies.csv");

            List<Item> items = ItemReader.read("data/Items.csv");

            new File("data").mkdirs();

            // ---------- XML ----------
            XStream xstream = new XStream(new DomDriver());

            xstream.alias("person", Person.class);
            xstream.alias("company", Company.class);
            xstream.alias("equipment", Equipment.class);
            xstream.alias("service", Service.class);
            xstream.alias("license", License.class);

            write("data/Persons.xml", xstream.toXML(loader.getPersons()));
            write("data/Companies.xml", xstream.toXML(loader.getCompanies()));
            write("data/Items.xml", xstream.toXML(items));

            // ---------- JSON ----------
            MyJsonSerializer serializer = new MyJsonSerializer();

            serializer.serializePersons(loader.getPersons(), "data/Persons.json");
            serializer.serializeCompanies(loader.getCompanies(), "data/Companies.json");
            serializer.serializeItems(items, "data/Items.json");

            System.out.println("Success: All files generated.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void write(String path, String content) {

        try (PrintWriter out = new PrintWriter(path)) {
            out.print(content);
        } catch (Exception e) {
            System.err.println("Error writing: " + path);
        }
    }
}
