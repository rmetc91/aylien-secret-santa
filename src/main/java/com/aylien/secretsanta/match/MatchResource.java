package com.aylien.secretsanta.match;

import com.aylien.secretsanta.participant.Participant;
import com.aylien.secretsanta.participant.ParticipantService;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.time.Year;
import java.util.Set;

@Consumes("application/json")
@Produces("application/json")
@Path("/match")
public class MatchResource {
    @Inject
    ParticipantService participantService;

    @Inject
    MatchService matchService;

    @POST
    public Set<Participant> matchAll(@QueryParam("year") Integer year) {
        matchService.matchAll(participantService.list(), year == null ? Year.now() : Year.of(year));
        return participantService.list();
    }
}
