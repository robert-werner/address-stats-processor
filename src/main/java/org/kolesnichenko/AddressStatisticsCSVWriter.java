package org.kolesnichenko;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class AddressStatisticsCSVWriter
{
    private List<Address> addressList;
    private AddressStatistics addressStatistics;
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String recordDuplicatesCSVHeader = "record,occurenceQuantity";
    private static final String citiesFloorQuantityCSVHeader = "city,floor,floorQuantity";
    private String citiesFloorQuantityFileName = "citiesFloorQuantity " + LocalDateTime.now().toString() + ".csv";


    public void setAddressStatistics(AddressStatistics addressStatistics, List<Address> addressList)
    {
        this.addressStatistics = addressStatistics;
        this.addressList = addressList;
    }

    public void writeRecordDuplicatesStats() throws IOException
    {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss");
        String recordDuplicatesFileName = "Duplicate records occurences " + dateFormat.format(date) + ".csv";
        File file = new File(recordDuplicatesFileName);
        file.createNewFile();
        FileWriter writer = new FileWriter(file);

        writer.append(recordDuplicatesCSVHeader);
        writer.append(NEW_LINE_SEPARATOR);
        this.addressStatistics.getDuplicateRecords(this.addressList).forEach((record, occurenceQuantity) ->
        {
            try
            {
                writer.append(record.getAddress());
                writer.append(COMMA_DELIMITER);
                writer.append(occurenceQuantity.toString());
                writer.append(NEW_LINE_SEPARATOR);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        });
        writer.close();
        System.out.print("Information about duplicate records in XML-file is written at " + new File(recordDuplicatesFileName).getAbsoluteFile() + NEW_LINE_SEPARATOR);

    }

    public void writeCityFloorStats(int floorQuantity) throws IOException
    {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss");
        String citiesFloorQuantityFileName = floorQuantity + "-floor buildings " + dateFormat.format(date) + ".csv";
        File file = new File(citiesFloorQuantityFileName);
        file.createNewFile();
        FileWriter writer = new FileWriter(file);

        writer.append(citiesFloorQuantityCSVHeader);
        writer.append(NEW_LINE_SEPARATOR);

        this.addressStatistics.getCityFloorQuantityStats(floorQuantity, this.addressList).forEach((city, floorsQuantity) -> {
            try
            {
                writer.append(city);
                writer.append(COMMA_DELIMITER);
                writer.append(String.valueOf(floorQuantity));
                writer.append(COMMA_DELIMITER);
                writer.append(String.valueOf(floorsQuantity.get(floorQuantity)));
                writer.append(NEW_LINE_SEPARATOR);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        });
        writer.close();
        System.out.println("Information about " + floorQuantity + "-floor buildings " + "is written at " + new File(citiesFloorQuantityFileName).getAbsoluteFile());
    }
}
