package org.mozilla.jarino.providers.elasticsearch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;

@AutoValue
@JsonDeserialize(builder = AutoValue_WikipediaDoc.Builder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class WikipediaDoc {
  public abstract String title();

  public static WikipediaDoc create(String title) {
    return builder()
        .title(title)
        .build();
  }

  public static Builder builder() {
    return new AutoValue_WikipediaDoc.Builder();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(withPrefix = "")
  @JsonIgnoreProperties(ignoreUnknown = true)
  public abstract static class Builder {
    @JsonProperty("title")
    public abstract Builder title(String title);

    public abstract WikipediaDoc build();
  }
}
