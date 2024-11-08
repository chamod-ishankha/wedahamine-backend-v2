package org.bytecub.WedahamineBackend.error;

import lombok.Getter;
import lombok.Setter;

/**
 * The type Bad request alert exception.
 */
@Getter
@Setter
/**
 * @author : Steffan Devotta
 * @version : 1.0
 * @date : Sep 30, 2021
 * @copyright : © 2010-2021 Informatics International Limited. All Rights Reserved
 */

public class BadRequestAlertException extends RuntimeException {
    private static final long serialVersionUID = 1L;

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
    public BadRequestAlertException(String message, String entityName, String errorKey) {
        this.message = message;
        this.entityName = entityName;
        this.errorKey = errorKey;
    }
}

