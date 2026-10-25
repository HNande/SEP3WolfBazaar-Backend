package org.example.application.repository;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Locale;

public enum ImageFileTypeDTO {
  PNG("png", "image/png"),
  JPG("jpg", "image/jpeg"),
  JPEG("jpeg","image/jpeg");

  private final String json;
  private final String mimeType;

  ImageFileTypeDTO(String json, String mimeType) {
    this.json = json;
    this.mimeType = mimeType;
  }

  @JsonValue
  public String json() { return json; }

  public String mimeType() { return mimeType; }

  @JsonCreator
  public static ImageFileTypeDTO fromJson(String value) {
    if (value == null) return null;
    String v = value.strip().toLowerCase(Locale.ROOT);
    if (v.equals("jpeg")) v = "jpg";               // accept the common alias
    for (ImageFileTypeDTO t : values()) {
      if (t.json.equals(v)) return t;
    }
    throw new IllegalArgumentException(
        "Unknown filetype: " + value + ". Allowed: png, jpg");
  }
}