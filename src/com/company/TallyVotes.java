package com.company;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Benson on 2017-03-14.
 */
public class TallyVotes {

    HashMap<Integer, String> positions = new HashMap<Integer, String>();
    public TallyVotes () {
        positions.put(0, "VPC");
        positions.put(1, "VPS");
        positions.put(2, "VPA");
        positions.put(3, "VPF");
        positions.put(4, "VPI");
        positions.put(5, "VPV");
        positions.put(6, "VPE");
        positions.put(7, "PRE");
    }

    public HashMap<String, Integer> tally (HashMap<String, Integer> results, String[] candidates) {
        HashMap<String, String> ballots = new HashMap<String, String>();
        ballots = assembleBallot(candidates);
        return countBallots(results, ballots);
    }

    private HashMap<String, String> assembleBallot (String[] candidates) {
        HashMap<String, String> candidateMap = new HashMap<String, String>();

        for(int i = 0; i < candidates.length; i++) {
            candidateMap.put(candidates[i], positions.get(i));
        }

        return candidateMap;
    }

    private HashMap<String, Integer> countBallots (HashMap<String, Integer> results, HashMap<String, String> ballots) {
        HashMap<String, String> ballot = ballots;
        HashMap<String, Integer> votes = results;

        for (HashMap.Entry<String, String> entry : ballot.entrySet()) {
            String name = entry.getKey();
            String position = entry.getValue();

            if(!name.equals("Abstain")) {
                if (!votes.containsKey(name)) {
                    votes.put(name, 1);
                } else {
                    votes.put(name, votes.get(name) + 1);
                }
            } else {
                if (!votes.containsKey(name)) {
                    votes.put(name + " " + position, 1);
                } else {
                    votes.put(name + " " + position, votes.get(name) + 1);
                }
            }
        }

        return votes;
    }
}
