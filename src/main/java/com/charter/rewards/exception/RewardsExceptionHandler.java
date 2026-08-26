package com.charter.rewards.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for the Rewards API.
 * <p>
 * Converts expected API failures and exceptions into consistent, standardized
 * HTTP responses using Spring's {@link ProblemDetail} specification. This
 * ensures clients receive meaningful error messages and appropriate HTTP status
 * codes.
 * </p>
 */
@RestControllerAdvice
public class RewardsExceptionHandler {

	/**
	 * Handles {@link CustomerNotFoundException} when a requested customer is not
	 * found.
	 *
	 * @param exception the exception containing the missing customer details
	 * @return a {@link ResponseEntity} containing a {@link ProblemDetail} with a
	 *         404 (Not Found) status and the error message
	 */
	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<ProblemDetail> handleCustomerNotFound(CustomerNotFoundException exception) {
		ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
		problem.setProperty("error", exception.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
	}

	/**
	 * Handles invalid path parameters or bad requests triggered by validation
	 * failures.
	 * <p>
	 * Intercepts both {@link IllegalArgumentException} and
	 * {@link jakarta.validation.ConstraintViolationException}.
	 * </p>
	 *
	 * @param exception the runtime exception containing the validation or argument
	 *                  error details
	 * @return a {@link ResponseEntity} containing a {@link ProblemDetail} with a
	 *         400 (Bad Request) status and the error message
	 */
	@ExceptionHandler({ IllegalArgumentException.class, jakarta.validation.ConstraintViolationException.class })
	public ResponseEntity<ProblemDetail> handleBadRequest(RuntimeException exception) {
		ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
		problem.setProperty("error", exception.getMessage());
		return ResponseEntity.badRequest().body(problem);
	}
}