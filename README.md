# Investment Funds Calculator
Calculator for investment funds.

## Design and assumptions
Main function is placed in App class file.
Sample of selected funds is read from example1.csv file located in
resources directory.

Application has been split into two main parts

* InvestmentCalculator which produces percentage distribution of invested money between funds.
* ReportGenerator which is responsible for creating final report
   as entries which can be easily printed to the screen.

To try additional examples - new example file should be created in resources directory and main method in App class should be changed.

example1.csv

    LP,Rodzaj,Nazwa
    1,Polskie,Fundusz Polski 1
    2,Polskie,Fundusz Polski 2
    3,Zagraniczne,Fundusz Zagraniczny 1
    4,Zagraniczne,Fundusz Zagraniczny 2
    5,Zagraniczne,Fundusz Zagraniczny 3
    6,Pieniężne,Fundusz Pieniężny 1

As can be conclude from extension example file must preserve csv formatting.

## Building

To build project following tools should be available on machine
* Java JDK 8
* Maven

Code compilation and package building

    mvn clean package

Run App.main

    mvn exec:java