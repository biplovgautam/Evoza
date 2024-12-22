
# <img src="src/main/resources/images/icons/logo.png" alt="Evoza Logo" width="40" height="40"> Evoza Web Browser 

## Overview

Evoza Web Browser is a project developed for the LED2 course in the 2nd semester at our college. This application is designed to provide a simple and efficient web browsing experience with a custom user interface. The project is developed by a team of five members.

## Team Members

- Biplov Gautam (Scrum Master, Backend Developer, Sometimes Frontend as well)
- Sabin Raj Pokhreal (Frontend developer and UI/UX designer)
- Kalina Shrestha (Frontend developer and UI/UX designer)
- Rohit shah (SQL queries / DBA)
- Prochorus Rai (Backend & Tester)

## Features

- Different profiles for different users in same desktop
- Guest mode to save bookmarks and browsing histories without logging in!
- private / incognito mode to browse without saving data
- Custom title bar with minimize, maximize, and close buttons
- Customizable dark theme
- Modular design with separate UI components

## Project Structure

```
Evoza/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/
│   │   │   │   ├── evoza/
│   │   │   │   │   ├── browser/
│   │   │   │   │   │   ├── BrowserController.java
│   │   │   │   │   │   ├── GuestMode.java
│   │   │   │   │   │   ├── ProfileMode.java
│   │   │   │   │   ├── profile/
│   │   │   │   │   │   ├── ProfileManager.java
│   │   │   │   │   │   ├── Profile.java
│   │   │   │   │   ├── ui/
│   │   │   │   │   │   ├── LandingPageUI.java
│   │   │   │   │   │   ├── CreateProfileUI.java
│   │   │   │   │   │   ├── BrowserUI.java
│   │   │   │   │   ├── utils/
│   │   │   │   │   │   ├── DatabaseHelper.java
│   │   ├── resources/
│   │   │   ├── fxml/
│   │   │   ├── images/
│   │   │   │   ├── avatars/
│   │   │   │   └── icons/
│   │   │   ├── css/
│   │   │   │   ├── app.css
│   │   │   │   ├── newtab.css
│   │   │   ├── templates/
│   │   │   │   ├── newtabtemplate.html
├── test/
│   ├── java/
│   │   ├── com/
│   │   │   ├── evoza/
│   │   │   │   ├── browser/
│   │   │   │   ├── profile/
├── lib/
├── config/
│   ├── browser.properties
├── build.gradle
├── SQLqueries.sql
├── README.md
└── .gitignore
└── .env
```

## How to Run

1. **Clone the repository**:
   ```bash
   git clone https://github.com/biplovgautam/Evoza.git
   cd Evoza
   ```
2. **Run sql queries**:

   run all those quires which is inside SQLqueries.sql file.    

3. **Create .env file**:

   add youremail and app password as inside .env:

   EMAIL_USERNAME=your@gmail.com
   EMAIL_PASSWORD=cyyb rmdd ibmi eftm   

4. **Build the project**:
   ```bash
   gradle build
   ```

5. **Run the application**:
   ```bash
   gradle run
   ```

## Technologies Used

- Java
- JavaFX
- Gradle
- javawebview

## Custom Title Bar

The custom title bar includes the application logo, title, and window control buttons (minimize, maximize, close). It is implemented in the `CustomTitleBar.java` file.


## Contributions

All team members contributed to the development of the Evoza Web Browser. Special thanks to Biplov Gautam for leading the team as the Scrum Master.



