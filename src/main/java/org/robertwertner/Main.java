package org.robertwertner;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main
{
    public static void main(String[] args) throws IOException, XMLStreamException
    {
        if (args.length == 0)
        {
            System.out.println("Too few arguments. Usage: address_xml_statistics <address xml file>");
        }
        else
        {
            if (args.length == 1)
            {
                if (args[0].equals("-h") || args[0].equals("--help") || args[0].equals("/?"))
                {
                    System.out.println("This program parses XML-file with addresses and gives (in *.csv format) information about: " + "\n" +
                            "1) Duplicates in XML-file" + "\n" +
                            "2) Quantity of (1,2,3,4,5)-floor buildings in each city" + "\n" +
                            " Usage: address_xml_statistics <address xml file>");
                    System.exit(1);
                }
                if (new File(args[0]).exists())
                {
                    StaxAddressHandler addressHandler = new StaxAddressHandler();
                    List<Address> addresses = addressHandler.parseAddressesXML(args[0]);

                    AddressStatistics addressStatistics = new AddressStatistics();

                    AddressStatisticsCSVWriter addressStatisticsCSVWriter = new AddressStatisticsCSVWriter();
                    addressStatisticsCSVWriter.setAddressStatistics(addressStatistics, addresses);

                    addressStatisticsCSVWriter.writeRecordDuplicatesStats();

                    for (int i = 1; i < 6; i++)
                    {
                        addressStatisticsCSVWriter.writeCityFloorStats(i);
                    }
                    System.out.println("Done.");
                }
                else
                {
                    System.err.printf("There is no XML-file with addresses by path %s.", args[0]);
                }
            }

        }
    }

}
