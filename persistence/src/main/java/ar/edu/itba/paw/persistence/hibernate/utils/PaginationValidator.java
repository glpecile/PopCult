package ar.edu.itba.paw.persistence.hibernate.utils;

import ar.edu.itba.paw.interfaces.exceptions.InvalidPaginationParametersException;

public class PaginationValidator {

    public PaginationValidator() {
        throw new AssertionError();
    }

    public static void validate(int page, int pageSize) throws InvalidPaginationParametersException {
        if(page < 1 || pageSize < 1) {
            throw new InvalidPaginationParametersException();
        }
    }
}
