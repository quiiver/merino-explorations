package org.mozilla.jarino.providers.adm;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;

import java.util.Map;

@AutoValue
@JsonDeserialize(builder = AutoValue_AdmQueries.Builder.class)
public abstract class AdmQueries {
  public abstract String updatedTime();
  public abstract Map<String, Integer> mapping();

  public static Builder builder() {
    return new AutoValue_AdmQueries.Builder();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(withPrefix = "")
  public abstract static class Builder {
    @JsonProperty("mapping")
    public abstract Builder mapping(Map<String, Integer> mapping);
    @JsonProperty("updated_time")
    public abstract Builder updatedTime(String updatedTime);
    public abstract AdmQueries build();
  }
}
