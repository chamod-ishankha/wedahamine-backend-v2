package org.bytecub.WedahamineBackend.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorizationAlertException extends RuntimeException {
    private final String message;
    private final String entityName;
    private final String errorKey;

    /**
     * Instantiates a new Bad request alert exception.
     *
     * @param message    the message
     * @param entityName the entity name
     * @param errorKey   the error key
     */
    public AuthorizationAlertException(String message, String entityName, String errorKey) {
        this.message = message;
        this.entityName = entityName;
        this.errorKey = errorKey;
    }

}
