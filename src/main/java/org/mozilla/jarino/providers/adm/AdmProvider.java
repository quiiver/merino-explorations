package org.mozilla.jarino.providers.adm;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mozilla.jarino.Suggestion;
import org.mozilla.jarino.providers.IProvider;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Provider for adm
 */
public class AdmProvider implements IProvider {
  private static AdmQueries queries;
  private static Map<Integer, AdmResults.Block> resultsTable;

  public AdmProvider() {
    // load test files
    ObjectMapper mapper = new ObjectMapper();

    try {
      queries = mapper.readValue(
          getResource("adm/InstantSuggest_Queries_20220125.json"),
          AdmQueries.class
      );
      AdmResults results = mapper.readValue(
          getResource("adm/InstantSuggest_Results_20220125.json"),
          AdmResults.class
      );

      resultsTable = results.blocks().stream()
          .collect(Collectors.toMap(b -> b.id(), b -> b));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public List<Suggestion> query(String queryString) {
    return Optional.ofNullable(queries.mapping().get(queryString))
        .map(idx -> resultsTable.get(idx))
        .filter(block -> block.brand().name().equals("wikipedia"))
        .map(block -> Suggestion.builder()
            .blockId(block.brand().id())
            .fullKeyword(block.brand().title().toLowerCase())
            .title(block.brand().title())
            .url(block.brand().advertiserUrl())
            .impressionUrl(block.impressionUrl())
            .clickUrl(block.brand().clickUrl())
            .provider("adm")
            .advertiser(block.brand().name())
            .isSponsored(true)
            .icon(block.brand().imageUrl())
            .score(0.5)
            .build()
        ).stream().collect(Collectors.toList());
  }

  private InputStream getResource(String path) {
    // The class loader that loaded the class
    ClassLoader classLoader = getClass().getClassLoader();
    InputStream inputStream = classLoader.getResourceAsStream(path);

    // the stream holding the file content
    if (inputStream == null) {
      throw new IllegalArgumentException("file not found! " + path);
    } else {
      return inputStream;
    }

  }
}
