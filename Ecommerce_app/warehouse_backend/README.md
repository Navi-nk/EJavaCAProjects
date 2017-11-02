E-commerce Warehouse BackEnd
----------------------------

This application is built using the provided [project template](https://github.com/chukmunnlee/ejava2017_ca1). The template consists of the angular web front end and we were instructed to create backend components to populate the provided view.

__Prerequistes for running the application__
* Navigate to ***db_model*** directory in ***warehouse_backend*** project folder and run the ***warehouse_dbsetup.sql*** script to create the database schema objects used by this application. (Project uses MySQL database) 
* Create ***JDBC Resource*** and ***Connection Pool*** in Payara server console in order to connect to the created schema. Make sure the name of the resources are same as that used in the ***persistence.xml***  
* The JMS resource created while configuring JMS queue (explained in e-commerce frontend readme) should match the ***mappedName*** attribute provided in the ***WarehouseBean.java*** MDB class.

Once the project is setup and ran in netbeans, the subsequent webpage launched will prompt user to provide the websocket url in order to connect to the backend. Use one of the following url -

>ws://localhost:8080/warehouse/orders/ 
>As soon as a client connects using this url, the web page will start displaying the live order messages received from e-commerce front application from that moment on.

>ws://localhost:8080/warehouse/orders/true 
>As soon as a client connects using this url, the history of order messages stored in the database will also be displayed and from then on the live messages received will be displayed,

#### Features implemented in the application ####

__Persistance Model__

The below diagram shows the ER diagram of the tables used for persistance in the application.
![picture alt](db_model/ER_diagram.png)
