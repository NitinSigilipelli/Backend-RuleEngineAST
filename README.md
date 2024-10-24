# Spring Boot Rule Engine with Abstract Syntax Tree (AST)

This project is a simple 3-tier rule engine application designed to evaluate user eligibility based on various attributes such as age, department, income, spend, and other criteria. The application dynamically parses and executes rules using an Abstract Syntax Tree (AST) to represent conditional logic.

## Features

- **Dynamic Rule Parsing**: Rules are represented and evaluated using an Abstract Syntax Tree (AST), allowing flexible and complex rule conditions.
- **Attribute-Based Evaluation**: User eligibility is determined based on dynamic attributes like age, department, income, etc.
- **RESTful API**: Exposes endpoints to create, evaluate, and manage rules and user attributes.
- **No Database Dependency**: Rules and metadata are stored in MongoDB, but this setup does not involve complex database dependencies.
- **Modular Design**: Clean separation between frontend, API layer, and backend logic.

## Technologies Used

- **Spring Boot**: For building the backend RESTful API.
- **MongoDB**: For storing rule definitions and application metadata.
- **Abstract Syntax Tree (AST)**: For parsing and evaluating dynamic rules.
- **IntelliJ IDEA**: For project development and management.
- **VS Code**: Can be used as an alternative IDE.

## Project Structure


## Endpoints

Here are some of the key endpoints exposed by the API:

- `POST /api/rules/create`  
  To create a new rule to the system.

- `POST /api/rules/evaluate`  
  Evaluate a user against a set of rules to determine eligibility.

- `POST /api/rules/combine`  
   To combine the rules with operator

## Example Rule Definition

Rules are defined using a JSON structure that can be easily parsed into an AST. Below is an example of a rule:

```json
{
  "conditions": {
    "type": "AND",
    "conditions": [
      {
        "field": "age",
        "operator": ">=",
        "value": 18
      },
      {
        "field": "income",
        "operator": "<",
        "value": 50000
      }
    ]
  }
}
```
##How It Works
**Rule Creation**: Use the /api/rules endpoint to define and store rules in MongoDB.
**AST Representation**: The rules are parsed into an AST for evaluation, allowing complex logical conditions (e.g., AND/OR).
**User Evaluation**: When user attributes are provided to the /api/rules/evaluate endpoint, the system processes the AST to determine if the user meets the rule conditions.

##etup and Running the Project
###Prerequisites
Java 17+
Maven 3.8+
MongoDB
Intellij Idea / Visual Studio Code (VS Code)

### Clone the Repository:

```bash
git clone 
```
## VS Code Setup

### Step 1: Install VS Code Extensions

To get started with Spring Boot in VS Code, you need to install the following extensions:

- **Java Extension Pack** (includes essential Java development tools like language support, debugger, etc.)
- **Spring Boot Extension Pack** (for Spring Boot support)
- **Debugger for Java** (for debugging Java applications)
- **MongoDB for VS Code** (for managing your MongoDB directly from VS Code)

You can install these extensions via the VS Code marketplace:

1. Open VS Code.
2. Navigate to **Extensions** (left sidebar).
3. Search and install the above extensions.

### Step 2: Open the Project in VS Code

1. Open VS Code.
2. Go to **File** > **Open Folder** and select the root folder of the project (where the `build.gradle` file is located).
3. VS Code will automatically detect that this is a Java project and build it.

### Step 3: Build and Run the Project

1. Open the **Terminal** in VS Code: `Ctrl + ~`
2. Use the following command to build the project:

   ```bash
   ./gradlew build
   ```
3.To run the Spring Boot application, you can use the Gradle plugin in VS Code or simply run:
  ```bash
    ./gradlew bootRun
  ```
## IntelliJ IDEA Setup

### Step 1: Install IntelliJ IDEA

Make sure you have IntelliJ IDEA installed on your system. You can download it from [JetBrains](https://www.jetbrains.com/idea/download/).

### Step 2: Open the Project in IntelliJ IDEA

1. Open IntelliJ IDEA.
2. Navigate to **File** > **Open** and select the root folder of your project (where the `build.gradle` file is located).
3. IntelliJ IDEA will automatically detect the project as a Gradle project and import all necessary dependencies.

### Step 3: Build and Run the Project

1. Open the **Terminal** within IntelliJ IDEA: `View` > `Tool Windows` > `Terminal`.
2. Use the following command to build the project:

   ```bash
   ./gradlew build
   ```
3.To run the Spring Boot application, you can:

Use the Gradle tool window and run the bootRun task, OR

Simply run:
  ```bash
  ./gradlew bootRun
  ```
4.Or just run the `RuleEngineWithASTApplication.java` to start the server.
5.The application will start, and you can access the API at http://localhost:8081/api/rules



