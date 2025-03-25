package com.swyth.hospitalservice.exception;

public class BedUnavailableException extends RuntimeException {
  public BedUnavailableException(String message) {
    super(message);
  }
}
