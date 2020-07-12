# DS-Assignment-3 DPSS_Webservices

## Distributed Player Status System (DPSS) using Web Services

Submitted By : Vaibhav Malhotra - 40079373 <br>
Github link : https://github.com/Vaibhav3M/DS-DPSS_Webservices

## Build and Run

All code is written in IntelliJ IDE, Java JDK version 8.

This project implements **Option 1** (Build the end point files using the wsgen command before publishing the service. Import the wsdl files using the wsimport command.)

### IDE (IntelliJ)

-	Open the project in folder DPSS_WebServices
-	Setup the SDK

**Generating artifacts and endpoints using wsgen**

-	Compile Server Implementations files in Servers folder
-	Open terminal at DPSS_WebServices
-	Run the command below three commands to generate wsdl files
    - ```wsdl -cp . src.main.java.Servers.America.AmericanServerImpl -wsdl``` 
    - ```wsdl -cp . src.main.java.Servers.Asia.AsianServerImpl -wsdl```
    - ```wsdl -cp . src.main.java.Servers.Europe.EuropeanServerImpl -wsdl```

-	Go to ‘http://localhost:8080/server?wsdl to verify.

**Import wsdl files using wsimport**

-	Open terminal at DPSS_WebServices
-	Run the command below three commands to import wsdl files
    - ```wsimport -keep -d . -p main.java.Client https://localhost:8080/server?wsdl```
    - ```wsimport -keep -d . -p main.java.Client https://localhost:8081/server?wsdl```
    - ```wsimport -keep -d . -p main.java.Client https://localhost:8082/server?wsdl```


**Running the project**
-	Run: AmericaGameServer, EuropeGameServer, AsiaGameServer.
-	Run: PlayerClient (to launch a player window)
-	Run:  AdminClient (to launch a Admin window)
-	To run multiple clients change the configuration to “Allow parallel run”.


### Command Line
 
-	Move to DPSS directoy: cd DPSS_WebServices.
-	Create a new folder named dist in the current folder : mkdir dist
-	Compile the code (outputting into dist folder): javac -d dist src/**/*.java
-	Move to dist folder: cd dist

**Generating artifacts and endpoints using wsgen**

-	Compile Server Implementations files in Servers folder
-	Open terminal at DPSS_WebServices
-	Run the command below three commands to generate wsdl files
    - ```wsdl -cp . src.main.java.Servers.America.AmericanServerImpl -wsdl``` 
    - ```wsdl -cp . src.main.java.Servers.Asia.AsianServerImpl -wsdl```
    - ```wsdl -cp . src.main.java.Servers.Europe.EuropeanServerImpl -wsdl```

-	Go to ‘http://localhost:8080/server?wsdl to verify.

**Import wsdl files using wsimport**

-	Open terminal at DPSS_WebServices
-	Run the command below three commands to import wsdl files
    - ```wsimport -keep -d . -p main.java.Client https://localhost:8080/server?wsdl```
    - ```wsimport -keep -d . -p main.java.Client https://localhost:8081/server?wsdl```
    - ```wsimport -keep -d . -p main.java.Client https://localhost:8082/server?wsdl```

**Start Servers:**
 ```start java Server.America.AmericaServer 
start java Server.Asia.AsiaServer  
start java Server.Europe.EuropeanServer 
```
**Run the clients (will have to open in different terminals):**
```
java PlayerClient
java AdminClient
```
### Ports
- AmericanServer – 8080
- EuropeanServer - 8081
- AsianServer – 8082

The ports can be changed in Constants file.



## Concepts implemented

### 1.	JAX-WS (Java API for XML Web Services)
- Used JAX-WS to develop a web services for DPSS. The communication is happens using SOAP which is a XML-based protocol. 
-	Used WSDL (Web Service Description Language) to generate required artifacts to deploy the service.


### 2.	UDP
For below communication between server UDP is used:
- transferAccount() - When a user request to trasnfer account to another server, a UDP request with user information is generated which instructs the other server to add player to it's database and reponsds with the status. If succesful the player is removed from old server. 
-	getPlayerStatus() – When admin requests this method on a server, that server sends UDP request to other two servers to get the player info.
-	createPlayer() – When a user tries to create a new player on a server, that server sends a UDP request to other servers to check if Username already exists.
 

### 3.	Multi-threading
-	All servers run on their individual thread
-	All UDP requests are sent on a new thread
- All client requests are sent on a new thread

### 4.	HashTables-DataStructure
Player data on server are stored in a Hashtables. Hashtables are thread-safe and promote concurrency.

### 5. Locks
Lock (ReentrantLock) is used for proper synchronization to allow multiple users to perform operations for the same or different accounts at the same time.


## Test screenshots are avilable in [Report](https://github.com/Vaibhav3M/DS-DPSS_Webservices/blob/master/Assignment3-Report.pdf)

