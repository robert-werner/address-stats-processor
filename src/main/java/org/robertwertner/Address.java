package org.robertwertner;

import java.util.Objects;

public class Address
{
    private String city;
    private String street;
    private String house;
    private int floor;

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(getCity(), address.getCity()) &&
                Objects.equals(getStreet(), address.getStreet()) &&
                Objects.equals(getHouse(), address.getHouse()) &&
                Objects.equals(getFloor(), address.getFloor());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getCity(), getStreet(), getHouse(), getFloor());
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getStreet()
    {
        return street;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }

    public String getHouse()
    {
        return house;
    }

    public void setHouse(String house)
    {
        this.house = house;
    }

    public int getFloor()
    {
        return floor;
    }

    public void setFloor(int floor)
    {
        this.floor = floor;
    }

    public String getAddress()
    {
        return "\"" + this.getCity() + ", " + this.getStreet() + ", " + this.getHouse() + ", " + this.getHouse() + "\"";
    }

}
