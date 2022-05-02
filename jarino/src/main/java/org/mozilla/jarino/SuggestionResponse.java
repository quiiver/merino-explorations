package org.mozilla.jarino;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.auto.value.AutoValue;

import java.util.List;

@AutoValue
@JsonDeserialize(builder = AutoValue_SuggestionResponse.Builder.class)
public abstract class SuggestionResponse {
  @JsonProperty("suggestions")
  abstract List<Suggestion> suggestions();

  @JsonProperty("request_id")
  abstract String requestId();

  public static Builder builder() {
    return new AutoValue_SuggestionResponse.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder suggestions(List<Suggestion> suggestions);

    public abstract Builder requestId(String requestId);

    public abstract SuggestionResponse build();
  }

}
