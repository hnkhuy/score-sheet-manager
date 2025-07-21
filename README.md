# Score Sheet Manager

This project is a Java console application for managing student scores.  
It allows users to either **enter student and subject data manually** or **import the data from a CSV file**.  
The program calculates the average scores for each student, displays a grade report, and identifies:
- The student with the highest average score.
- The highest single subject score across all students.

---

## Branch Information
- The main branch of this project is `main`.

---

## Input Methods

When you run the program, you will be prompted to select an input method:

1. **Manual Input**
  - Enter the number of students (must be >= 1).
  - Enter the number of subjects (must be >= 1).
  - Enter each subject name.
  - Enter each student’s name and their score for each subject.
  - The program validates all inputs and will prompt again if the entered value is invalid (e.g., not a number, negative, etc.).

2. **CSV File Input**
  - The program reads scores from a file named `scoreSheet.csv` by default (placed in the project root folder).
  - The first row must contain the header:
    ```
    Name,Subject1,Subject2,...
    ```
  - Each subsequent row contains the student’s name followed by their scores.

---

## Example CSV Format

```csv
Name,Math,Physics,Chemistry,English
Student_1,7.6,6.9,5.3,8.5
Student_2,8.2,7.5,6.0,9.1
Student_3,5.9,8.0,7.2,6.3
```

---

## Output

The program will:
- Display a formatted grade table with all students, their scores, and average scores.
- Identify the student with the highest average score.
- Identify the highest single subject score across all students.

---

## Requirements
- Java 21 (recommended)
- A properly formatted CSV file (if using CSV input mode)

---

## Notes
- The project is currently structured as a single runnable file, focused on demonstrating algorithmic logic rather than full Object-Oriented Programming (OOP) principles. If the goal is to evaluate skills in structuring projects using **classes, objects, and OOP design**, this program can be refactored accordingly as a separate version.


