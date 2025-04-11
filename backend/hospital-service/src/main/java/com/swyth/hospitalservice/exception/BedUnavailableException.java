package com.swyth.hospitalservice.exception;

/**
 * This exception is thrown to indicate that there are no available beds
 * for a specific medical specialization or hospital.
 *
 * It is commonly used in scenarios where a certain operation cannot be
 * completed due to the unavailability of hospital beds, such as in bed
 * reservation or availability checks.
 *
 * Example use cases include:
 * - Throwing this exception in service logic when a bed cannot be found for
 *   a given hospital and specialization.
 * - Handling this exception in a global exception handler to return a
 *   meaningful response to the client.
 */
public class BedUnavailableException extends RuntimeException {
  /**
   * Constructs a new BedUnavailableException with a specified message.
   *
   * This exception is used to indicate that no beds are available for a given
   * medical specialization or hospital. The message provides additional details
   * about the specific circumstance in which the exception is thrown.
   *
   * @param message the detail message explaining the reason for the exception
   */
  public BedUnavailableException(String message) {
    super(message);
  }
}
