package com.company;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Benson on 2017-03-13.
 */
public class VerifyHelper {

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
}
