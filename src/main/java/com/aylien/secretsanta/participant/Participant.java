package com.aylien.secretsanta.participant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Year;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public final class Participant {
    @EqualsAndHashCode.Include
    @ToString.Include
    public String email;

    @JsonIgnoreProperties({"matchHistory"})
    public Map<Year, Participant> matchHistory = new HashMap<>();

    public Participant(String email) {
        this.email = email;
    }
}