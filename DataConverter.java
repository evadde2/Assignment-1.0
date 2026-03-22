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

LATEST CODE
    package com.cinco;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DataConverter {

    public static void main(String[] args) {

        try {
            // Initialize Loader and Load Persons/Companies
            DataLoader loader = new DataLoader();
            loader.loadPersons("data/Persons.csv");
            loader.loadCompanies("data/Companies.csv");

            // Read ItemTemplates from CSV
            List<ItemTemplate> templates = ItemReader.read("data/Items.csv");

            // Transform Templates into Polymorphic Item Subclasses
           
            List<Item> items = new ArrayList<>();
            for (ItemTemplate t : templates) {
                String type = t.getTypeCode();

                if (type.equals("E")) {
                    
                    items.add(new Equipment(t.getUuid(), t.getName(), t.getBaseRate()));
                } else if (type.equals("S")) {
                    
                    items.add(new Service(t.getUuid(), t.getName(), t.getBaseRate(), (Person)null, 0.0));
                } else if (type.equals("L")) {
                    
                    items.add(new License(t.getUuid(), t.getName(), t.getExtraFee(), t.getBaseRate(), (java.time.LocalDate)null,(java.time.LocalDate) null));
                }
            }

            // 4. Create output directory if it doesn't exist
            new File("data").mkdirs();

            // Run XML Serialization 
            XmlSerializer xmlSerializer = new XmlSerializer();
            xmlSerializer.serializePersons(loader.getPersons(), "data/Persons.xml");
            xmlSerializer.serializeCompanies(loader.getCompanies(), "data/Companies.xml");
            xmlSerializer.serializeItems(items, "data/Items.xml");

            // Run JSON Serialization 
            MyJsonSerializer jsonSerializer = new MyJsonSerializer();
            jsonSerializer.serializePersons(loader.getPersons(), "data/Persons.json");
            jsonSerializer.serializeCompanies(loader.getCompanies(), "data/Companies.json");
            jsonSerializer.serializeItems(items, "data/Items.json");

            System.out.println("Success: All files generated in data/ folder.");

        } catch (Exception e) {
            System.err.println("Conversion failed:");
            e.printStackTrace();
        }
    }
}
