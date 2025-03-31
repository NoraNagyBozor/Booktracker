# BookTracker

BookTracker is a command-line Java application that allows users to track their reading habits using an SQLite database. The application provides functionalities for adding users, tracking reading habits, and performing statistical calculations based on reading data.

## Features
- Add a new user to the database
- Retrieve all reading habit data for a specific user
- Update the title of a book in the database
- Delete a reading habit record
- Calculate the mean age of users
- Count the number of users who have read a specific book
- Compute the total number of pages read by all users
- Count users who have read more than one book

## Prerequisites
- **Java Development Kit (JDK) 8 or higher** (Install instructions below)
- **SQLite JDBC Driver** (Download from [this link](https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.43.2.1/sqlite-jdbc-3.43.2.1.jar))
- **SLF4J API** (Download from [this link](https://repo1.maven.org/maven2/org/slf4j/slf4j-api/2.0.9/slf4j-api-2.0.9.jar))
- SQLite database (included in the repository)

## Installing Java SDK and Setting Up the System Path
### **1️⃣ Install Java Development Kit (JDK)**
1. **Download Java JDK** from the official [Oracle JDK](https://www.oracle.com/java/technologies/javase-downloads.html) or use [OpenJDK](https://adoptium.net/).
2. **Install the JDK** following the on-screen instructions.

### **2️⃣ Add Java to System Path (Windows Users)**
1. Open **Start Menu** and search for **"Environment Variables"**.
2. Click **"Edit the system environment variables"**.
3. In **System Properties**, click **Environment Variables**.
4. Under **System Variables**, find **Path** and click **Edit**.
5. Click **New**, and add the path to Java’s `bin` folder, e.g., `C:\Program Files\Java\jdk-XX.X.X\bin`.
6. Click **OK** and **Apply**.
7. Close and restart your terminal or Command Prompt.
8. Verify installation by running:
   ```bash
   java -version
   ```

## Setup Instructions
1. Clone this repository:
   ```bash
   git clone <repository-url>
   ```
2. Navigate to the project directory:
   ```bash
   cd booktracker
   ```
3. Download the SQLite JDBC driver and SLF4J API, then place them in the project folder:
   ```bash
   wget https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.43.2.1/sqlite-jdbc-3.43.2.1.jar
   wget https://repo1.maven.org/maven2/org/slf4j/slf4j-api/2.0.9/slf4j-api-2.0.9.jar
   ```
4. Compile the Java program with SQLite JDBC and SLF4J included:
   ```bash
   javac -cp .;sqlite-jdbc-3.43.2.1.jar;slf4j-api-2.0.9.jar BookTracker.java
   ```
5. Run the program:
   ```bash
   java -cp .;sqlite-jdbc-3.43.2.1.jar;slf4j-api-2.0.9.jar BookTracker
   ```

For **Linux/macOS**, use `:` instead of `;`:
```bash
javac -cp .:sqlite-jdbc-3.43.2.1.jar:slf4j-api-2.0.9.jar BookTracker.java
java -cp .:sqlite-jdbc-3.43.2.1.jar:slf4j-api-2.0.9.jar BookTracker
```

## Usage
The program presents a menu with different options. Users can input the corresponding number to perform operations such as adding users, updating book titles, or retrieving statistical data.

## Database Structure
The application uses an SQLite database with two tables:

### User Table
| Column  | Type    |
|---------|--------|
| userID  | INTEGER (Primary Key) |
| age     | INTEGER |
| gender  | TEXT |
| name    | TEXT |

### ReadingHabit Table
| Column            | Type    |
|------------------|--------|
| habitID          | INTEGER (Primary Key) |
| userID           | INTEGER (Foreign Key referencing User) |
| pagesRead        | INTEGER |
| book             | TEXT |
| submissionMoment | DATETIME |

## Author
Developed for BookTracker Management System.
Created by Nora Nagy-Bozor

## License
This project is open-source and available for use and modification.
