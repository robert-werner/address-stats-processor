package org.robertwertner;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import java.io.FileNotFoundException;
import java.util.List;

public interface IAddressHandler
{
    List<Address> parseAddressesXML(String path);
}
