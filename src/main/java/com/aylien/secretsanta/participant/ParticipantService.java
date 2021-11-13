package com.aylien.secretsanta.participant;

import javax.enterprise.context.ApplicationScoped;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@ApplicationScoped
public class ParticipantService {
    private final Set<Participant> participants = Collections.synchronizedSet(new LinkedHashSet<>());

    public Set<Participant> list() {
        return participants;
    }

    public void register(Participant participant) {
        if (!participants.add(participant)) {
            throw new AlreadyRegisteredException(participant);
        }
    }

    public Set<Participant> delete(Participant participant) {
        participants.removeIf(existingParticipant -> existingParticipant.email.contentEquals(participant.email));
        return participants;
    }
}
