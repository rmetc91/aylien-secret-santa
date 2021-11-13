package com.aylien.secretsanta.match;

import com.aylien.secretsanta.participant.Participant;

import javax.enterprise.context.ApplicationScoped;
import java.time.Year;
import java.util.Random;
import java.util.Set;

@ApplicationScoped
public class MatchService {
    private final Random random = new Random();

    public void matchAll(Set<Participant> participants, Year year) {
        if (participants.size() < 2) {
            throw new NotEnoughParticipantsException();
        }

        participants.stream().
                filter(participant -> notMatched(participant, year))
                .forEach(participant -> tryMatch(participants, participant, year));
    }

    private void tryMatch(Set<Participant> participants, Participant participant, Year year) {
        var possibleMatches = participants.stream()
                .filter(possibleMatch -> isNotSelf(participant, possibleMatch))
                .filter(possibleMatch -> isNotRecentlyMatched(possibleMatch, participant, year))
                .toList();

        if (possibleMatches.isEmpty()) {
            throw new MatchFailedException(participant, year);
        }

        Participant match = possibleMatches.get(random.nextInt(possibleMatches.size()));

        participant.matchHistory.put(year, match);
    }

    private boolean notMatched(Participant participant, Year year) {
        return participant.matchHistory.get(year) == null;
    }

    private boolean isNotRecentlyMatched(Participant possibleMatch, Participant participant, Year year) {
        return participant.matchHistory
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().isAfter(year.minusYears(3)))
                .noneMatch(entry -> entry.getValue().equals(possibleMatch));
    }

    private boolean isNotSelf(Participant participant, Participant possibleMatch) {
        return !participant.email.contentEquals(possibleMatch.email);
    }
}
