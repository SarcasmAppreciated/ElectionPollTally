package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class TallyRunner {

    private static ArrayList<String[]> responses = new ArrayList<String[]>();
    private static ArrayList<String[]> firstYearWithName = new ArrayList<String[]>();
    private static HashMap<String, Integer> results = new HashMap<String, Integer>();
    private static HashSet<String> usedStudentNumbers = new HashSet<String>();


    public static void main(String[] args) {
        int numberOfDuplicates = 0;
        VerifyHelper verifyHelper = new VerifyHelper();
        // expects UTF-8 CSV in format from Fluidsurvey.com - MUST BE IN VOTES HASHMAP ORDER
        responses = verifyHelper.loadResponsesCSV("E:\\Users\\Benson\\Desktop\\responses.csv");

        // for each value, verify student number
        for (String[] line: responses) {
            // expects UTF-8 CSV in format of StudentNumber\nStudentNumber\n etc.
            VerifyStudentNumber verifyStudentNumber = new VerifyStudentNumber("E:\\Users\\Benson\\Desktop\\list.csv");

            if(!usedStudentNumbers.contains(line[10])) { // line[10] is the student number field
                if(verifyStudentNumber.checkInList(line[10])) {
                    TallyVotes tallyVotes = new TallyVotes();
                    String[] candidates = Arrays.copyOfRange(line, 12, line.length);
                    results = tallyVotes.tally(results, candidates);
                } else {
                    if(!line[11].equals("")) {
                        String[] nameAndNumber = new String[2];

                        nameAndNumber[0] = line[10]; // line[10] is the student number field
                        nameAndNumber[1] = line[11]; // line[11] is the name field
                        firstYearWithName.add(nameAndNumber);
                    }
                }
                usedStudentNumbers.add(line[10]);
            } else {
                numberOfDuplicates++;
            }
        }

        resultsPrinter();
        firstYearPrinter();
        System.out.println(numberOfDuplicates + " Duplicate Student Number(s) Detected");
    }

    private static void resultsPrinter() {
        // print out first years that qualify
        System.out.println("Results:");
        for (HashMap.Entry<String, Integer> entry : results.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().toString();

            System.out.println(key + " " + value);
        }
    }

    private static void firstYearPrinter() {
        // print out first years that qualify
        System.out.println("Non-matching Numbers + Names:");
        for (String[] line: firstYearWithName) {
            System.out.println(line[0] + " " + line[1]);
        }
    }
}
