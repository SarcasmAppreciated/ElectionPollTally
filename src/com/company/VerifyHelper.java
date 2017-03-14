package com.company;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by Benson on 2017-03-13.
 */
public class VerifyHelper {

    private HashMap<String, String[]> usedStudentNumbers = new HashMap<String, String[]>();

    public ArrayList<String> loadStudentListCSV(String csvFileLocation) {
        ArrayList<String> responses = new ArrayList<String>();
        try {
            Scanner scan = new Scanner(new File(csvFileLocation));
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                responses.add(line);
            }

            return responses;
        } catch(IOException e) {
            System.out.println("Exception Thrown");
        }

        return responses;
    }

    public ArrayList<String[]> loadResponsesCSV(String csvFileLocation) {
        ArrayList<String[]> responses = new ArrayList<String[]>();
        try {
            Scanner scan = new Scanner(new File(csvFileLocation));
            scan.nextLine(); // Removes heading line
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                String[] splitLine = line.split("\"\t\"");
                responses.add(splitLine);
        }

            return responses;
        } catch(IOException e) {
            System.out.println("IO Exception");
        }

        return null;
    }

    // remove dupes by timestamp
    public ArrayList<String[]> combResponses(ArrayList<String[]> responses) {
        ArrayList<String[]> combedResponses = new ArrayList<String[]>();
        int duplicateCount = 0;

        for (String[] line: responses) {
            String studentNumber = line[10];
            if (!usedStudentNumbers.containsKey(studentNumber)) {
                combedResponses.add(line);
                usedStudentNumbers.put(studentNumber, line);
            } else {
                String currentTimeString = usedStudentNumbers.get(studentNumber)[4]; // date updated
                String newTimeString = line[4]; // date updated

                try {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date currentTimeStamp = dateFormat.parse(currentTimeString);
                    Date newTimeStamp = dateFormat.parse(newTimeString);

                    String[] currentEntry = usedStudentNumbers.get(studentNumber);

                    // remove current combedResponses entry
                    combedResponses.remove(currentEntry);

                    if (currentTimeStamp.compareTo(newTimeStamp) > 0) {
                        combedResponses.add(currentEntry);
                    } else {
                        combedResponses.add(line);
                    }
                } catch (ParseException e) {
                    System.out.println("Unable to Parse Date Format.");
                }

                duplicateCount++;
            }
        }

        if (duplicateCount == 1) {
            System.out.println("Removed " + duplicateCount + " Duplicate Entry");
        } else {
            System.out.println("Removed " + duplicateCount + " Duplicate Entries");
        }

        return combedResponses;
    }
}
