package org.mozilla.jarino.providers.adm;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;

import java.util.List;

@AutoValue
@JsonDeserialize(builder = AutoValue_AdmResults.Builder.class)
abstract class AdmResults {
  abstract String updatedTime();
  abstract List<Block> blocks();

  public static Builder builder() {
    return new AutoValue_AdmResults.Builder();
  }

  @AutoValue
  @JsonDeserialize(builder = AutoValue_AdmResults_Block.Builder.class)
  abstract static class Block {
    abstract int id();
    abstract String impressionUrl();
    abstract String iabCategory();
    abstract Brand brand();

    public static Builder builder() {
      return new AutoValue_AdmResults_Block.Builder();
    }

    @AutoValue
    @JsonDeserialize(builder = AutoValue_AdmResults_Block_Brand.Builder.class)
    abstract static class Brand {
      abstract int id();
      abstract String name();
      abstract String clickUrl();
      abstract String imageUrl();
      abstract String advertiserUrl();
      abstract String title();

      public static Builder builder() {
        return new AutoValue_AdmResults_Block_Brand.Builder();
      }

      @AutoValue.Builder
      @JsonPOJOBuilder(withPrefix = "")
      public abstract static class Builder {
        @JsonProperty("id")
        public abstract Builder id(int id);

        @JsonProperty("name")
        public abstract Builder name(String name);

        @JsonProperty("click_url")
        public abstract Builder clickUrl(String clickUrl);

        @JsonProperty("image_url")
        public abstract Builder imageUrl(String imageUrl);

        @JsonProperty("advertiser_url")
        public abstract Builder advertiserUrl(String advertiserUrl);

        @JsonProperty("title")
        public abstract Builder title(String title);

        public abstract Brand build();
      }
    }

    @AutoValue.Builder
    @JsonPOJOBuilder(withPrefix = "")
    public abstract static class Builder {
      @JsonProperty("id")
      public abstract Builder id(int id);

      @JsonProperty("impression_url")
      public abstract Builder impressionUrl(String impressionUrl);

      @JsonProperty("iab_category")
      public abstract Builder iabCategory(String iabCategory);

      @JsonProperty("brand")
      public abstract Builder brand(Brand brand);

      public abstract Block build();
    }
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(withPrefix = "")
  public abstract static class Builder {

    @JsonProperty("blocks")
    public abstract Builder blocks(List<Block> blocks);

    @JsonProperty("updated_time")
    public abstract Builder updatedTime(String updatedTime);

    public abstract AdmResults build();
  }
}
