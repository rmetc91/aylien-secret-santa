package com.aylien.secretsanta.match;

import com.aylien.secretsanta.participant.Participant;

import java.time.Year;

public class MatchFailedException extends RuntimeException {
    public MatchFailedException(Participant participant, Year year) {
        super("Failed to match " + participant.email + " in year " + year);
    }
}
