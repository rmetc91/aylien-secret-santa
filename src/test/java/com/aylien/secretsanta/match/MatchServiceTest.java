package com.aylien.secretsanta.match;

import com.aylien.secretsanta.participant.Participant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Year;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

class MatchServiceTest {
    private static final Year YEAR_1 = Year.of(1999);
    private static final Year YEAR_2 = Year.of(2000);
    private static final Year YEAR_3 = Year.of(2001);

    private final MatchService matchService = new MatchService();

    private Set<Participant> sampleParticipants(int size) {
        return IntStream.range(0, size)
                .mapToObj(index -> new Participant("participant" + index + "@example.com"))
                .collect(Collectors.toUnmodifiableSet());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    void matchAll_cannotMatchZeroOrOneParticipants(int size) {
        Set<Participant> participants = sampleParticipants(size);

        assertThatThrownBy(() -> matchService.matchAll(participants, YEAR_1))
                .isInstanceOf(NotEnoughParticipantsException.class);
    }

    @Test
    void matchAll_matchesParticipantsAtRandom() {
        Set<Participant> participants = sampleParticipants(2);

        matchService.matchAll(participants, YEAR_1);

        for (Participant participant : participants) {
            assertThat(participant.matchHistory.get(YEAR_1))
                    .as(describeParticipant(participant))
                    .isNotNull();
        }
    }

    @Test
    void matchAll_findsUniqueMatchesInAThreeYearPeriod() {
        Set<Participant> participants = sampleParticipants(3);
        matchService.matchAll(participants, YEAR_1);

        matchService.matchAll(participants, YEAR_2);

        for (Participant participant : participants) {
            assertThat(participant.matchHistory.get(YEAR_2))
                    .as(describeParticipant(participant))
                    .isNotEqualTo(participant.matchHistory.get(YEAR_1));
        }
    }

    @Test
    void matchAll_throwsException_ifUnableToFindUniqueMatch() {
        Set<Participant> participants = sampleParticipants(3);
        matchService.matchAll(participants, YEAR_1);
        matchService.matchAll(participants, YEAR_2);

        assertThatThrownBy(() -> matchService.matchAll(participants, YEAR_3))
                .isInstanceOf(MatchFailedException.class);
    }

    @Test
    void matchAll_allowsDuplicateMatchAfterThreeYears() {
        Set<Participant> participants = sampleParticipants(4);
        IntStream.range(1970, 1980)
                .mapToObj(Year::of)
                .forEach(year -> assertThatCode(() -> matchService.matchAll(participants, year))
                        .doesNotThrowAnyException());
    }

    private String describeParticipant(Participant participant) {
        return participant.email + ": " + participant.matchHistory;
    }
}