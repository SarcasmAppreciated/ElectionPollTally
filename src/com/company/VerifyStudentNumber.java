package com.company;

import java.util.ArrayList;

/**
 * Created by Benson on 2017-03-13.
 */
public class VerifyStudentNumber {

    String csvFileLocation = "";

    public VerifyStudentNumber(String loc) {
        csvFileLocation = loc;
    }

    public boolean checkInList(String studentNumber) {
        if (checkStudentNumberLength(studentNumber) && studentNumberExistsInList(studentNumber)) {
            return true;
        } else {
            return false;
        }
    }

    /* Check student numbers vs CSV */
    private boolean checkStudentNumberLength(String studentNumber){
        if (studentNumber.length() == 8)  {
            return true;
        } else {
            return false;
        }
    }

    private boolean studentNumberExistsInList(String studentNumber) {
        // get list
        VerifyHelper verifyHelper = new VerifyHelper();
        ArrayList<String> responses = verifyHelper.loadStudentListCSV(csvFileLocation);

        if ((responses.size() == 0) || (responses == null)) {
            System.out.println("File not loaded - aborted");
            return false;
        }

        if (responses.contains(studentNumber)) {
            return true;
        } else {
            return false;
        }
    }
}
