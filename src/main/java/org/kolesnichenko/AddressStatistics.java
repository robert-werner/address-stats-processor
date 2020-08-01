package org.kolesnichenko;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class AddressStatistics
{
    public Map<Address, Long> getDuplicateRecords(List<Address> addressList)
    {
       System.out.print("Gathering duplicates in XML-file... ");

       Map<Address, Long> recordsOccurences = addressList // получаем список записей с количеством их появлений в файле address.xml
               .stream()
               .collect(groupingBy(Function.identity(),counting()));

       Map<Address, Long> duplicateRecords = recordsOccurences
               .entrySet()
               .stream()
               .filter(addressEntry -> addressEntry.getValue() > 1) // если запись встречается больше 1 раза
               .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

       return duplicateRecords;
    }

    public HashMap<String, HashMap<Integer, Integer>> getCityFloorQuantityStats(int floorQuantity, List<Address> addressList)
    {
        System.out.printf("Gathering information about %d-floor buildings... ", floorQuantity);

        HashMap<String, HashMap<Integer, Integer>> cityFloorStats = new HashMap<>();
        List<String> cityList = addressList // получаем список городов без дубликатов (все города в единственном экземпляре)
                .stream()
                .map(Address::getCity)
                .distinct()
                .collect(Collectors.toList());
        List<Address> nFloorAdresses = addressList // получаем список адресов только со floorQuantity-этажными зданиями (ускорение обработки)
                .stream()
                .filter(addressEntry -> addressEntry.getFloor() == floorQuantity)
                .collect(toList());
        cityList
                .forEach( // проходимся по списку городов
                        city -> {
                            for (var address : nFloorAdresses) // проходимся по списку адресов с floorQuantity-этажными зданиями
                            {
                                String _city = address.getCity(); // получаем город из Address-элемента для сравнения
                                int floor = address.getFloor(); // этажность
                                if (_city.equals(city))
                                {
                                    if (address.getFloor() == floorQuantity)
                                    {
                                        if (cityFloorStats.containsKey(_city))
                                            // если cityFloorStats имеет элемент с ключом _city,
                                            // то он обязательно имеет и значение в виде HashMap<Integer, Integer> floorsQuantity,
                                            // содержащий элемент вида (этажность:количество зданий с такой этажностью)
                                        {
                                            HashMap<Integer, Integer> floorsQuantity = cityFloorStats.get(_city);
                                            floorsQuantity.put(floor, floorsQuantity.get(floor) + 1);
                                            cityFloorStats.replace(_city, floorsQuantity);
                                        }
                                        else
                                        {
                                            HashMap<Integer, Integer> floorsQuantity = new HashMap<>();
                                            floorsQuantity.put(floor, 1);
                                            cityFloorStats.put(_city, floorsQuantity);
                                        }
                                    }
                                }
                            }
                        }
                );
        return cityFloorStats;
    }
}
