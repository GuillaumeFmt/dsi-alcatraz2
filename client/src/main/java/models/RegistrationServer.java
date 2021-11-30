package models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegistrationServer {
    private final String hostname;
    private final int port;
}
