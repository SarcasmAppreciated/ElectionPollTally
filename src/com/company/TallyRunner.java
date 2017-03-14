package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class TallyRunner {

    private static ArrayList<String[]> responses = new ArrayList<String[]>();
    private static ArrayList<String> firstYearWithName = new ArrayList<String>();
    private static HashMap<String, Integer> results = new HashMap<String, Integer>();
    private static HashSet<String> usedStudentNumbers = new HashSet<String>();

    public static void main(String[] args) {

        String pathOfResponses = "E:\\Users\\Benson\\Desktop\\responses.csv";
        String pathOfStudentNumbers = "E:\\Users\\Benson\\Desktop\\list.csv";

        if (args.length > 0) {
            pathOfResponses = args[0];
            pathOfStudentNumbers = args[1];
        }

        int duplicateCount = 0;
        VerifyHelper verifyHelper = new VerifyHelper();
        // expects UTF-8 CSV in format from Fluidsurvey.com - MUST BE IN VOTES HASHMAP ORDER
        responses = verifyHelper.loadResponsesCSV(pathOfResponses);
        responses = verifyHelper.combResponses(responses); // remove dupes

        // for each value, verify student number
        for (String[] line: responses) {
            // expects UTF-8 CSV in format of StudentNumber\nStudentNumber\n etc.
            VerifyStudentNumber verifyStudentNumber = new VerifyStudentNumber(pathOfStudentNumbers);
            String studentNumber = line[10];
            String studentName = line[11];

            if (!usedStudentNumbers.contains(studentNumber)) {
                if (verifyStudentNumber.checkInList(studentNumber)) {
                    TallyVotes tallyVotes = new TallyVotes();
                    String[] candidates = Arrays.copyOfRange(line, 12, line.length);
                    results = tallyVotes.tally(results, candidates);
                } else {
                    if (!studentName.equals("")) {
                        firstYearWithName.add(studentNumber + " " + studentName);
                    }
                }
                usedStudentNumbers.add(studentNumber);
            } else {
                duplicateCount++;
            }
        }

        resultsPrinter();
        System.out.println(duplicateCount + " Duplicate Student Number(s) During Tally Detected"); // should be 0
        firstYearPrinter();
    }

    private static void resultsPrinter() {
        // print out first years that qualify
        System.out.println("Results:");
        for (HashMap.Entry<String, Integer> entry : results.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().toString();

            System.out.println(" * " + key + " " + value);
        }
    }

    private static void firstYearPrinter() {
        // print out first years that qualify
        System.out.println("Non-matching Numbers + Names:");
        if (firstYearWithName.size() > 0) {
            for (String line: firstYearWithName) {
                System.out.println(line);
            }
        } else {
            System.out.println("- None");
        }
    }
}
