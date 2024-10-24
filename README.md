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

- `POST /api/rules`  
  Add a new rule to the system.

- `POST /api/rules/evaluate`  
  Evaluate a user against a set of rules to determine eligibility.

- `GET /api/rules`  
  Get a list of all rules stored in the system.

## Example Rule Definition

Rules are defined using a JSON structure that can be easily parsed into an AST. Below is an example of a rule:

```json
{
  "ruleName": "AgeAndIncomeCheck",
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
