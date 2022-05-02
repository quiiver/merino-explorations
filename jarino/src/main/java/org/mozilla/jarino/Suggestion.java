package org.mozilla.jarino;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.auto.value.AutoValue;

@AutoValue
@JsonDeserialize(builder = AutoValue_Suggestion.Builder.class)

public abstract class Suggestion {
  @JsonProperty("block_id")
  abstract int blockId();

  @JsonProperty("full_keyword")
  abstract String fullKeyword();

  @JsonProperty("title")
  abstract String title();

  @JsonProperty("url")
  abstract String url();

  @JsonProperty("impression_url")
  abstract String impressionUrl();

  @JsonProperty("click_url")
  abstract String clickUrl();

  @JsonProperty("provider")
  abstract String provider();

  @JsonProperty("advertiser")
  abstract String advertiser();

  @JsonProperty("is_sponsored")
  abstract boolean isSponsored();

  @JsonProperty("icon")
  abstract String icon();

  @JsonProperty("score")
  abstract Double score();

  public static Builder builder() {
    return new AutoValue_Suggestion.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder blockId(int blockId);

    public abstract Builder fullKeyword(String fullKeyword);

    public abstract Builder title(String title);

    public abstract Builder url(String url);

    public abstract Builder impressionUrl(String impressionUrl);

    public abstract Builder clickUrl(String clickUrl);

    public abstract Builder provider(String provider);

    public abstract Builder advertiser(String advertiser);

    public abstract Builder isSponsored(boolean isSponsored);

    public abstract Builder icon(String icon);

    public abstract Builder score(Double score);

    public abstract Suggestion build();
  }
}
