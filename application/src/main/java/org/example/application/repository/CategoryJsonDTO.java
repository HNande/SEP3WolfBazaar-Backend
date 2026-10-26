package org.example.application.repository;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Locale;

public enum CategoryJsonDTO {
  CLOTHING, ACCESSORIES, CURIOS;

  @JsonCreator public static CategoryJsonDTO fromJson(String category) {
    if (category == null)
      return null;
    try {
      return valueOf(category.strip().toUpperCase(Locale.ROOT));
    }
    catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(
          "Unknown or invalid category" + category);
    }
  }
    @JsonValue public String toJson () {
      return name().toLowerCase(Locale.ROOT);
    }
}