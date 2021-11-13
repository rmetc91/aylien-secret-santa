package com.aylien.secretsanta.participant;

import javax.enterprise.context.ApplicationScoped;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

@ApplicationScoped
public class ParticipantService {
    private final Set<Participant> participants = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

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
