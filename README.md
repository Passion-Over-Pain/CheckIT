# Learning Management System (LMS) — Android App 


![lms](https://github.com/user-attachments/assets/5c4fb590-4aca-40d7-9b87-4e5978e1c2d1)

> [!Tip] 
> This is an Android application for managing student tasks with role-based login for Admin, Instructor, and Student. You can download the latest release of the app directly from the [Releases](https://github.com/Passion-Over-Pain/IMOB-Assignment/releases/download/v1.0/lms-v1.apk) section.

---

## Table of Contents

1. [Project Overview](#project-overview)
2. [Group Members](#group-members)
3. [Technologies Used](#technologies-used)
4. [App Features](#app-features)
    - [Admin Features](#admin-features)
    - [Instructor Features](#instructor-features)
    - [Student Features](#student-features)
5. [App Structure](#app-structure)
6. [Installation & Running](#installation--running)
7. [Screenshots](#screenshots)
8. [Demo Video](#demo-video)
9. [Acknowledgements](#acknowledgements)

## Project Overview

This Learning Management System (LMS) Android application was developed to support streamlined task management for students within an academic environment. The application features a role-based login system, enabling three distinct user types—Admin, Instructor, and Student—to access specific functionalities tailored to their roles.

- **Admins** can manage core academic entities including student records, module data, and instructor accounts.
- **Instructors** are able to create and assign academic tasks to students.
- **Students** can log in to view their assigned tasks and update their task completion status.

The application was built entirely using **Java** in **Android Studio**, and all major functionalities are distributed across dedicated activities. A navigation-based interface ensures ease of access, with a clean UI supported by custom layouts and drawable assets.

To ensure security and access control, we implemented a login activity that validates user credentials and redirects them to their respective dashboards.

This system is ideal for academic environments looking for a lightweight, offline-capable mobile LMS that simplifies the interaction between students, instructors, and administrators.

## Group Members

This project was completed by the following group members:

- **Jasmin Storm**  
  <img src="https://github.com/Storm-3.png" width="100" /><br>
  GitHub Profile: [Jasmin's GitHub](https://github.com/Storm-3)

- **Juanette Viljoen**  
  <img src="https://github.com/JuanetteRViljoen.png" width="100" /><br>
  GitHub Profile: [Juanette's GitHub](https://github.com/JuanetteRViljoen)

- **Mthi Mzimba**<br>


  <img src="https://github.com/user-attachments/assets/ea40c523-d728-498b-97f4-8bcc88ecca95" width="100" /><br>
  GitHub Profile: *Coming Soon*

- **Tinotenda Mhedziso**  
  <img src="https://github.com/Passion-Over-Pain.png" width="100" /><br>
  GitHub Profile: [Tinotenda's GitHub](https://github.com/Passion-Over-Pain)

- **Zanele Shandu** <br>
  <img src="https://github.com/user-attachments/assets/59826cb3-f0c6-4440-aee3-66b7f772f0ad" width="100" /><br>
  GitHub Profile: *Coming Soon*


<br>

Each member contributed to various aspects of the application including interface design, database logic, role-based access control, activity flow implementation, and testing.

## Technologies Used

This project was developed using the following technologies and tools:

- **Java**  
  Primary programming language used to implement all backend logic, data handling, and user interface interactions within the Android application.

- **Android Studio**  
  The official IDE for Android development, used for coding, debugging, testing, and managing the project structure.

- **XML (Extensible Markup Language)**  
  Used extensively to define UI layouts, custom styles, and drawable resources across the application.

- **SQLite (via SQLiteOpenHelper)**  
  Embedded relational database used for storing and managing persistent data such as user accounts, tasks, students, instructors, and modules.

- **Gradle**  
  Build automation system used to manage project dependencies and compile the application.

- **Android SDK**  
  Provides the necessary libraries and APIs required to build and run Android applications on physical devices or emulators.

- **Git & GitHub**  
  Used for version control and collaborative development. The repository also includes a signed APK for direct installation.

- **Material Design Guidelines**  
  Followed for UI/UX practices to ensure consistency and responsiveness across devices.

  ## App Features

The Learning Management System (LMS) is designed to provide distinct functionalities based on the role of the user. Upon logging in, users are directed to their respective dashboards which provide tailored access and operations.

### Admin Features

Admins are responsible for managing system records and have access to the following features:

- Create new **student** records (including ID, name, surname, and date of birth)
- Create new **instructor** records
- Create new **module** records (including module name and duration)
- View, update, or delete existing students, instructors, and modules
- Central dashboard to manage all administrative tasks

### Instructor Features

Instructors are responsible for managing student tasks and have access to the following features:

- Create **tasks** assigned to students (with task name, due date, and associated module)
- View, update, or delete previously created tasks
- Dashboard displaying all current tasks and student information

### Student Features

Students use the system primarily to manage their assigned tasks:

- View a list of tasks assigned by instructors
- Mark tasks as **complete** or **incomplete**
- Access personal dashboard with task list and completion statuses


## App Structure

The application is organized into modular components, separating core logic, UI layouts, and assets for maintainability. Below is a high-level overview of the structure:

```bash
📁 app/
├── 📁 src/
│ ├── 📁 main/
│ │ ├── 📁 java/com/example/learningmanagementsystem/
│ │ │ ├── Activities: Each screen in the app (e.g., Login.java, AdminDashboard.java)
│ │ │ ├── Models: Data classes (Student.java, Module.java, Task.java)
│ │ │ ├── Adapters: Custom adapters for ListViews (e.g., StudentAdapter.java)
│ │ │ └── DatabaseManager.java: Handles all SQLite database operations
│ │ ├── 📁 res/
│ │ │ ├── drawable/: App images and icons
│ │ │ ├── layout/: XML UI layouts for each activity
│ │ │ ├── mipmap/: App launcher icons for various screen sizes
│ │ │ └── values/: App colors, themes, and strings
│ │ └── AndroidManifest.xml
│
├── 📁 release/
│ └── Signed APK builds for distribution
│
├── build.gradle.kts
├── settings.gradle.kts
└── proguard-rules.pro
```

Each user role has its own dedicated activity and associated layouts, maintaining a clean separation of concerns throughout the app lifecycle.

## Installation & Running

The application has been packaged and signed as an APK for easy installation on Android devices. Follow the steps below to install and run the app:

### Step 1: Download the APK

You can download the latest signed release directly from the [Latest Releases](https://github.com/Passion-Over-Pain/IMOB-Assignment/releases/download/v1.0/lms-v1.apk) section of this GitHub repository.

### Step 2: Install on Your Android Device

1. Transfer the downloaded APK file to your Android phone.
2. Open the file using your file manager or browser.
3. If prompted, allow installation from unknown sources (this is required for apps not installed via the Google Play Store).
4. Complete the installation process.

> **Note**: Some security tools like Avast or Google Play Protect may warn that the app is from an unknown developer. This is expected and safe to bypass for testing purposes. Our APK is signed and verified for demonstration use only.

### Step 3: Launch the App

Once installed, launch the app from your device’s application drawer. You can log in using dummy data for the Admin: <b>Email: admin </b>  <b>Password: password </b><br>
Once logged in you can create students and instructors whom you will login with the respective credentials.


## Screenshots

Below are some screenshots that illustrate the core functionality and user experience across different roles in the application.

### 1. Login Screen
Allows Admins, Instructors, and Students to securely log in based on their assigned role.

<div align="center">
<img src="https://github.com/user-attachments/assets/d41e0fe6-998d-4240-818e-00b00389e988"  height="600">
</div>




---

### 2. Admin Dashboard
Admins can manage Students, Modules, and Instructors from a centralized interface.

<div align="center">
<img src="https://github.com/user-attachments/assets/78a24351-2071-48bf-89d8-c8d9ed814792"  height="600">
</div>

---

### 3. Add Student Screen
Admins can register students into the system by filling in essential information.

<div align="center">
<img src="https://github.com/user-attachments/assets/43161dfa-14b8-4942-9933-53db491efe4a"  height="600">
</div>

---

### 4. Instructor Dashboard
Instructors can view modules and assign tasks to students.

<div align="center">
<img src="https://github.com/user-attachments/assets/60703f96-6ec6-4f18-868f-50e08b3c2914"  height="600">
</div>

---

### 5. View Task Screen
Instructors view the current list of tasks and which student has each task.

<div align="center">
<img src="https://github.com/user-attachments/assets/4e67127e-f48d-4afb-b32a-64674deae01d"  height="600">
</div>


---

### 6. Student Task View
Students can view all assigned tasks and mark them as completed.

<div align="center">
<img src="https://github.com/user-attachments/assets/3660328d-618b-493d-b2f2-d86018416773"  height="600">
</div>

## Demo Video

A short demonstration video has been recorded to showcase the key features and functionality of our Learning Management System Android app. The video walks through the experience of each role: Admin, Instructor, and Student. (Coming Soon)

 [Click here to watch the demo video](https://your-demo-video-link.com)

The video covers:
- Role-based login
- Creating and managing students, modules, and instructors
- Task creation and management by instructors
- Student task views and completion flow

**Note:** The demo video is under 5 minutes as per assignment requirements.

## Acknowledgements
- Canva: For providing design templates for images.
- Pexels: For royalty-free images used in the app.
- Flaticon: For offering free icons used in the app interface.
- Android Developers: For their comprehensive guides and tutorials that greatly helped during development.

---

