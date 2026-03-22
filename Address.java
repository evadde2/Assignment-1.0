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

LATEST CODE
package com.cinco;

/*
* A concrete Item subclass representing billable labor that calculates costs based on hourly rates,
* a fixed base fee, and a specific service-related tax percentage.
*/

import com.google.gson.annotations.SerializedName;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("linkedhashmap")
public class Service extends Item {
    
    @SerializedName("CostPerHour")
    private final String costPerHour;

    @XStreamOmitField
    private transient final Person technician;
    
    @XStreamOmitField
    private transient final double hours;

    /*
     * The primary constructor used during invoice processing to link a specific technician and the number of hours
     *  worked to the service.
     */
    public Service(String uuid, String name, double costPerHour, Person technician, double hours) {
        super(uuid, name, "S");
        this.costPerHour = String.format("%.1f", costPerHour);
        this.technician = technician;
        this.hours = hours;
    }

    /*
     * A simplified constructor used by the DataLoader for initial item definitions where technician and hour 
     * data are not yet applicable.
     */
    public Service(String uuid, String name, double costPerHour) {
        this(uuid, name, costPerHour, null, 0.0);
    }

    //Parses the costPerHour string to return the numerical hourly rate of the service.
    @Override
    public double getCost() {
        return Double.parseDouble(costPerHour);
    }

    //Calculates the service tax by applying a flat rate of 3.15% to the hourly cost, rounded to two decimal places.
    @Override
    public double getTax() {
        return round(getCost() * 0.0315);
    }

    //Returns the string "Service" as a type descriptor for reporting purposes.
    @Override
    public String getDetails() {
        return "Service";
    }

    //Calculates the total billable amount for the service by multiplying the hourly cost by the number of hours worked.
    @Override
    public double getDisplayTotal() {
        return getCost() * hours;
    }

    //return technician
    public Person getTechnician() { return technician; }
    
    //return hours
    public double getHours() { return hours; }
}
