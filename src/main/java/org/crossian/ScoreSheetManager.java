package org.crossian;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ScoreSheetManager {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int inputMethod;

        while (true) {
            System.out.println("Choose input method:");
            System.out.println("1. Manual input");
            System.out.println("2. Read from CSV file");
            System.out.print("Enter 1 or 2: ");

            String input = scanner.nextLine();

            try {
                inputMethod = Integer.parseInt(input);
                if (inputMethod == 1 || inputMethod == 2) {
                    break;
                } else {
                    System.out.println("Invalid choice! Please enter 1 or 2.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid integer (1 or 2).");
            }
        }

        String[] names;
        String[] subjects;
        double[][] scoreTable;
        double[] averageScores;

        int studentQuantity = 0;
        int subjectQuantity = 0;

        if (inputMethod == 1) {
            while (true) {
                System.out.print("Enter the number of students: ");
                String input = scanner.nextLine();
                try {
                    studentQuantity = Integer.parseInt(input);
                    if (studentQuantity >= 1) {
                        break;
                    } else {
                        System.out.println("Invalid number! The number of students must be >= 1.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Please enter a valid integer.");
                }
            }

            while (true) {
                System.out.print("Enter the number of subjects: ");
                String input = scanner.nextLine();
                try {
                    subjectQuantity = Integer.parseInt(input);
                    if (subjectQuantity >= 1) {
                        break;
                    } else {
                        System.out.println("Invalid number! The number of subjects must be >= 1.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Please enter a valid integer.");
                }
            }

            names = new String[studentQuantity];
            subjects = new String[subjectQuantity];
            scoreTable = new double[studentQuantity][subjectQuantity];
            averageScores = new double[studentQuantity];

            System.out.println("Enter the subjects:");
            for (int i = 0; i < subjectQuantity; i++) {
                System.out.print("Subject " + (i + 1) + ": ");
                subjects[i] = scanner.nextLine();
            }

            for (int i = 0; i < studentQuantity; i++) {
                System.out.print("Enter name of student " + (i + 1) + ": ");
                names[i] = scanner.nextLine();
                double sum = 0;

                for (int j = 0; j < subjectQuantity; j++) {
                    while (true) {
                        System.out.print("Enter score for " + subjects[j] + " (1 - 10): ");
                        String scoreInput = scanner.nextLine();
                        try {
                            double score = Double.parseDouble(scoreInput);
                            if (score >= 1 && score <= 10) {
                                scoreTable[i][j] = score;
                                sum += score;
                                break;
                            } else {
                                System.out.println("Invalid score! Please enter a number between 1 and 10.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input! Please enter a valid number between 1 and 10.");
                        }
                    }
                }
                averageScores[i] = sum / subjectQuantity;
            }

        } else {
            //Read from file named scoreSheet.csv by default
            try (BufferedReader br = new BufferedReader(new FileReader("scoreSheet.csv"))) {
                //Assume the CSV has a maximum of 1,000,000 characters
                br.mark(1_000_000);
                String headerLine = br.readLine();
                if (headerLine == null) {
                    System.out.println("CSV file is empty!");
                    return;
                }
                String[] headers = headerLine.split(",");
                subjectQuantity = headers.length - 1;
                while ((br.readLine()) != null) {
                    studentQuantity++;
                }

                names = new String[studentQuantity];
                subjects = new String[subjectQuantity];
                scoreTable = new double[studentQuantity][subjectQuantity];
                averageScores = new double[studentQuantity];

                System.arraycopy(headers, 1, subjects, 0, subjectQuantity);

                String line;
                int currentRow = 0;
                br.reset();
                br.readLine();
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    names[currentRow] = data[0];
                    double sum = 0;
                    for (int j = 0; j < subjectQuantity; j++) {
                        scoreTable[currentRow][j] = Double.parseDouble(data[j + 1]);
                        sum += scoreTable[currentRow][j];
                    }
                    averageScores[currentRow] = sum / subjectQuantity;
                    currentRow++;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        //Display Grade Table
        System.out.println("\n===== GRADE REPORT =====");
        System.out.printf("%-5s %-15s", "No.", "Student Name");
        for (String subject : subjects) {
            System.out.printf("%-10s", subject);
        }
        System.out.println("Average");

        for (int i = 0; i < studentQuantity; i++) {
            System.out.printf("%-5d %-15s", (i + 1), names[i]);
            for (int j = 0; j < subjectQuantity; j++) {
                System.out.printf("%-10.2f", scoreTable[i][j]);
            }
            System.out.printf("%.2f%n", averageScores[i]);
        }

        //Find student with highest average
        double maxAvg = averageScores[0];
        int idxMaxAvg = 0;
        for (int i = 1; i < studentQuantity; i++) {
            if (averageScores[i] > maxAvg) {
                maxAvg = averageScores[i];
                idxMaxAvg = i;
            }
        }

        //Find highest single subject score
        double maxScore = scoreTable[0][0];
        int idxStudent = 0, idxSubject = 0;
        for (int i = 0; i < studentQuantity; i++) {
            for (int j = 0; j < subjectQuantity; j++) {
                if (scoreTable[i][j] > maxScore) {
                    maxScore = scoreTable[i][j];
                    idxStudent = i;
                    idxSubject = j;
                }
            }
        }

        //Display summary results
        System.out.println("\nStudent with highest average score: " + names[idxMaxAvg] +
                " (" + String.format("%.2f", maxAvg) + ")");
        System.out.println("Highest individual subject score: " + names[idxStudent] +
                " - " + subjects[idxSubject] + " (" + maxScore + ")");
    }
}