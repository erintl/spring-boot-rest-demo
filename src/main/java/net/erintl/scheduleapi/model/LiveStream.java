package net.erintl.scheduleapi.model;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;

public record LiveStream(
  String id,
  @NotEmpty(message = "Stream title is required") String title,
  String description,
  String url,
  LocalDateTime startDate,
  LocalDateTime endDate) {
}
