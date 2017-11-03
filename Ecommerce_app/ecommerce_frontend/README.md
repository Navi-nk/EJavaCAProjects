E-commerce FrontEnd - FruitMart
-------------------------------

The E-Commerence FrontEnd application is developed using the **PrimeFaces** UI framework. The below diagram shows the screen flow the application.
<p align="center">
  <img src="web/resources/images/pageflow.png" alt="ER diagram"/>
</p>

__Prerequistes for running the application__
* Navigate to ***db_model*** directory in ***ecommerce_frontend*** project folder and run the ***fruimart_setup_dbsetup.sql*** script to create the database schema objects used by this application. (Project uses MySQL database) 
* Create ***JDBC Resource*** and ***Connection Pool*** in Payara server console in order to connect to the created schema. Make sure the name of the resources are same as that used in the ***persistence.xml***  
* Create the JMS ***ConnectionFactory*** and ***Destination*** resource in Payara server console in order to configure JMS Queue. Make sure the name of the resources is same as that used in ***CustomerView.java***. 

#### Features implemented in the application ####


1. The below diagram shows the ER diagram of the tables used for persistance in the application.
<p align="center">
  <img src="db_model/ER_diagram.png" alt="ER diagram"/>
</p>
 
