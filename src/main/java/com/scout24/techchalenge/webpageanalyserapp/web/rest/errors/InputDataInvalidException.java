package com.scout24.techchalenge.webpageanalyserapp.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class InputDataInvalidException extends AbstractThrowableProblem {
    private static final long serialVersionUID = 1L;

    public InputDataInvalidException() {
        super(ErrorConstants.INVALID_INPUT_TYPE, "Incorrect input data", Status.UNPROCESSABLE_ENTITY);
    }
}
