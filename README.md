
# <img src="src/main/resources/images/icons/logo.png" alt="Evoza Logo" width="40" height="40"> Evoza Web Browser 

## Overview

Evoza Web Browser is java.javafx based desktop application developed using javafx webview for rendring urls. This application is designed to provide a simple and efficient web browsing experience with a custom user interface.

## Browser Interface

![Evoza Browser Interface](browserinterface.png)

## Saving Bookmark
![Saving Bookmark](Screenshot.png)

## Demo Video

Watch the demo video on YouTube: [EVOZA YT video](https://youtu.be/1vTi7G_V2_U?si=dVVuIFzAOAWRvUBh)


## Team Members

- Biplov Gautam (Scrum Master/Developer)
- Sabin Raj Pokhreal (Frontend developer and Backend)
- Kalina Shrestha (Frontend developer and UI/UX designer)
- Rohit shah (SQL database)
- Prochorus Rai (Tester)

## Features

- Different profiles for different users in same desktop
- Guest mode to save bookmarks and browsing histories without logging in!
- private / incognito mode to browse without saving data
- Custom title bar

- Modular design with separate UI components

## Project Structure

```
Evoza/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/
│   │   │   │   ├── evoza/
│   │   │   │   │   ├── profile/
│   │   │   │   │   │   ├── ProfileManager.java
│   │   │   │   │   │   ├── Profile.java
│   │   │   │   │   ├── ui/
│   │   │   │   │   │   ├── LandingPageUI.java
│   │   │   │   │   │   ├── CreateProfileUI.java
│   │   │   │   │   │   ├── BrowserUI.java
│   │   │   │   │   ├── utils/
│   │   │   │   │   │   ├── DatabaseHelper.java
│   │   │   │   │   ├── EvozaApp.java
│   │   ├── resources/
│   │   │   ├── images/
│   │   │   │   ├── avatars/
│   │   │   │   └── icons/
│   │   │   ├── documentation/
│   │   │   │   ├── Evoza.docx 
├── test/
│   ├── java/
│   │   ├── com/
│   │   │   ├── test/
│   │   │   │   ├── TestLauncher.java
│   │   │   │   ├── profileTest.java
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


