package com.aylien.secretsanta.participant;

class AlreadyRegisteredException extends RuntimeException {
    AlreadyRegisteredException(Participant participant) {
        super("Already registered participant " + participant.email);
    }
}