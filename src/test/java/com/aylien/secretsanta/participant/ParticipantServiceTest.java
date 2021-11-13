package com.aylien.secretsanta.participant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ParticipantServiceTest {
    private static final Participant JOHN = new Participant("johndoe@example.com");
    private static final Participant JANE = new Participant("janedoe@example.com");
    private static final Participant JOE = new Participant("joebloggs@example.com");

    private final ParticipantService service = new ParticipantService();

    @BeforeEach
    void setUp() {
        service.register(JOHN);
        service.register(JANE);
    }

    @Test
    void list() {
        assertThat(service.list()).containsExactly(JOHN, JANE);
    }

    @Test
    void register() {
        service.register(JOE);
        assertThat(service.list()).containsExactly(JOHN, JANE, JOE);
    }

    @Test
    void register_throwsException_whenAlreadyRegistered() {
        assertThatThrownBy(() -> service.register(JOHN)).isInstanceOf(AlreadyRegisteredException.class);
    }

    @Test
    void delete() {
        service.delete(JOHN);
        assertThat(service.list()).containsExactly(JANE);
    }
}