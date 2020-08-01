package org.kolesnichenko;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AddressHandler
{
    public List<Address> parseAddressesXML(String path) throws FileNotFoundException, XMLStreamException
    {
        System.out.print("Reading XML-file...");
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(path));

        List<Address> addresses = new ArrayList<Address>();

        while (reader.hasNext())
        {
            Address currentAddress = null;
            XMLEvent nextEvent = reader.nextEvent();
            if (nextEvent.isStartElement())
            {
                if (!nextEvent.asStartElement().getName().getLocalPart().equals("root"))
                {
                    currentAddress = new Address();
                    Iterator<Attribute> attributes = nextEvent.asStartElement().getAttributes();
                    while (attributes.hasNext())
                    {
                        Attribute currentAttribute = attributes.next();
                        checkAttribute(currentAttribute, currentAddress);
                    }
                    addresses.add(currentAddress);
                }
            }
        }
        System.out.println(" Done.");
        return addresses;
    }

    private void checkAttribute(Attribute attribute, Address address)
    {
        final String city = "city";
        final String street = "street";
        final String house = "house";
        final String floor = "floor";
        switch (attribute.getName().getLocalPart())
        {
            case city:
                address.setCity(attribute.getValue());
                break;
            case street:
                address.setStreet(attribute.getValue());
                break;
            case house:
                address.setHouse(attribute.getValue());
                break;
            case floor:
                address.setFloor(Integer.parseInt(attribute.getValue()));
                break;
        }
    }


}
