package com.aylien.secretsanta.participant;

import javax.ws.rs.*;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

@Consumes("application/json")
@Produces("application/json")
@Path("/participants")
public class ParticipantResource {
    private Set<Participant> participants = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

    public ParticipantResource() {
        participants.add(new Participant("johndoe@example.com"));
        participants.add(new Participant("janedoe@example.com"));
        participants.add(new Participant("joebloggs@example.com"));
    }

    @GET
    public Set<Participant> list() {
        return participants;
    }

    @POST
    public Set<Participant> add(Participant participant) {
        participants.add(participant);
        return participants;
    }

    @DELETE
    public Set<Participant> delete(Participant participant) {
        participants.removeIf(existingParticipant -> existingParticipant.email.contentEquals(participant.email));
        return participants;
    }
}