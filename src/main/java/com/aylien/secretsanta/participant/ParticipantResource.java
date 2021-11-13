package com.aylien.secretsanta.participant;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.Set;

@Consumes("application/json")
@Produces("application/json")
@Path("/participants")
public class ParticipantResource {
    @Inject
    ParticipantService service;

    @GET
    public Set<Participant> list() {
        return service.list();
    }

    @POST
    public Set<Participant> register(Participant participant) {
        service.register(participant);
        return service.list();
    }

    @POST
    @Path("bulk")
    public Set<Participant> register(Set<Participant> participants) {
        participants.forEach(service::register);
        return service.list();
    }

    @DELETE
    public Set<Participant> delete(Participant participant) {
        return service.delete(participant);
    }
}