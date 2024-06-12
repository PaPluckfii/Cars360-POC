# Cars360-POC

This application is a proof of concept for a car servicing application.
Customers can register their car/cars and book slots for diffrent types of services, check status of on-going services and download bills of the services.
Service Advisors (Servicing company workers) can use the app to fill in details of the customer and car and the type and details of services to be done.

General Flow of app :

4 activitites - Main, OnBoarding, Admin and Customer

Main - Just a launcher activity, check for user login state and other initial checks and directs to required activity

OnBoarding - Flow by on_boarding_navigation - Types of logiun handled - Customer/Admin Partial login or complete OTP verified login

Admin - All Masters and service log creation and update

Customer - All services and offers based on selected car, service history and other customer data like invoices and enquiries.



Data layer - Local (Room) - cahced data from networkBoundResource function in util package
             Remote(Retrofit) - all request in ApiClient interface
              
Cacheitory - cahce for getting data and cahcing to room
              
