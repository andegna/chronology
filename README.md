EthiopianChronology
===================

What is EthiopianChronology?
----------------------------

EthiopianChronology is simply a library designed based on the extensible JDK 8 new date and time API.

About
-----
EthiopianChronology is a java library designed based on the JDK 8 new date and time API.
The library used the Ethiopic.org Calender under

Requirements
------------
JDK > 1.8

Sample Usage
-----
```java
EthiopianDate now = EthiopianDate.now();
EthiopianDate yesterday = now.minus(Period.ofDays(1));
EthiopianDate tomorrow = now.plus(Period.ofDays(1));
EthiopianDate myBirthDay = EthiopianDate.of(1986, 3, 21);
EthiopianDate ethiopian2k = EthiopianDate.ofEpochDay(13768);
EthiopianDate nationalityDay = EthiopianDate.ofYearDay(2007, 89);

EthiopianDate[] dates = {yesterday, now, tomorrow, myBirthDay, ethiopian2k, nationalityDay};

for (EthiopianDate ethiopianDate : dates) {
    System.out.print(ethiopianDate.get(ChronoField.DAY_OF_MONTH) + "/");
    System.out.print(ethiopianDate.get(ChronoField.MONTH_OF_YEAR) + "/");
    System.out.print(ethiopianDate.get(ChronoField.YEAR_OF_ERA));

    System.out.print(" is ");

    System.out.println(ethiopianDate.format(DateTimeFormatter.ISO_DATE));
}
````
## Reference
[Ethiopic.org](http://ethiopic.org/Calendars)
