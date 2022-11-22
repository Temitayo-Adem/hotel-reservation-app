# hotel-reservation-app

In this project, I designed a Java hotel reservation application that allows customers to find and book a hotel room based on room availability. The application also allows hotel administrators to add additional rooms, and check the status of all rooms,reservations, and users. 

## Structure

The project is split into four packages: **User Interface**, **Service**, **Model**, and **API**. 

The **userInterface** utilizes the command line to display an admin menu and user menu. The **Service** layer utilizes the **Model** classes to create the business logic of the application. The Service layer also uses collections to act as the in-memory storage of the application. The **API** layer serves as the intermediary between the user interface and the service layer. It receives requests from the UI and returns information from the service layer.
