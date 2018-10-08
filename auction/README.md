**Requirements**

* Java 8
* Gradle
* Spring

To run the application:

* gradle clean build bootRun

* Or Follow the below steps:
    * gradle clean build
    * java -jar auction-0.0.1-SNAPSHOT.jar


I have added few unit tests as well

To view the functionality to Record a bid, or get the winning bid for a CarID or view all bids for a CarID:

After having the application running by following the steps above : Use Swagger-UI

http://localhost:8080/swagger-ui.html/


Also things I considered which would have been nice to haves to make the problem more extensible
(this is if I indulged more time in this):

* Have a persistent data store for recording the bids to work in a distributed system, not a in-memory Cache like I have right now.
* Add more meta data fields for User and Car Model
* Add User Login functionality
* Add Bidding Open and close time - to record session of Bids.
* Add more functionality like a portal for Seller or Buyer.

