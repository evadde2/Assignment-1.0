package com.cinco;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class XmlSerializer implements Serializer {
	
   private final XStream xstream;
   
   public XmlSerializer() {
       xstream = new XStream(new StaxDriver());
       xstream.allowTypesByWildcard(new String[]{"com.cinco.**"});
       // Register custom LocalDate converter to fix the "InaccessibleObjectException"
       xstream.registerConverter(new Converter() {
           @Override
           public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
               writer.setValue(source.toString());
           }
           @Override
           public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
               return LocalDate.parse(reader.getValue());
           }
           @Override
           public boolean canConvert(Class type) {
               return type.equals(LocalDate.class);
           }
       });
       // Set up standard aliases here so writeXml stays clean
       xstream.alias("person", Person.class);
       xstream.alias("company", Company.class);
       xstream.alias("equipment", Equipment.class);
       xstream.alias("service", Service.class);
       xstream.alias("license", License.class);
   }
   @Override
   public void serializePersons(List<Person> persons, String outputPath) {
       writeXml(persons, "persons", outputPath);
   }
  
   @Override
   public void serializeCompanies(List<Company> companies, String outputPath) {
       writeXml(companies, "companies", outputPath);
   }
   @Override
   public void serializeItems(List<Item> items, String outputPath) {
       writeXml(items, "items", outputPath);
   }
   private <T> void writeXml(List<T> list, String rootName, String outputPath) {
       try (FileWriter writer = new FileWriter(outputPath)) {
           // Tell XStream to treat the List as the rootName provided
           xstream.alias(rootName, List.class);
          
           // toXML(list) automatically handles the wrapping and individual elements
           String xml = xstream.toXML(list);
           writer.write(xml);
       } catch (IOException e) {
           throw new RuntimeException("Error writing XML file: " + outputPath, e);
       }
   }
}
