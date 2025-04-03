# Personal Budget

## Technologies

A web application developed using technologies such as Java, Spring Boot, PostgreSQL, Thymeleaf, HTML, CSS, and JavaScript. 

Maven was used for managing additional libraries, while unit tests were written using the frameworks JUnit and Mockito.

The project was divided into common design patterns, such as Domain (which represents the core business logic and entities of the application) and DTO, which is used to transfer data between different layers.

The principles of the Model-View-Controller architectural pattern were followed.

## Description

After registering and logging into the application, a user has the ability to add income and expenses, generate a balance for a specified period of time, and display charts.

Additionally, there is an option to set spending limits for specific categories, making it easier to manage a household budget.

In the settings, users can add new income, expense, or payment categories, or delete existing ones.

Users can also change their password from the settings page or by sending a password reset email.

## Languanges

The user can switch between three languages: Polish, English, and German.

## Usage

The application is containerized using Docker and hosted on Azure App Service.

https://personal-budget-java-app.azurewebsites.net/








