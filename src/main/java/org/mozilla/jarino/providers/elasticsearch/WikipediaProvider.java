package org.mozilla.jarino.providers.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.mozilla.jarino.Suggestion;
import org.mozilla.jarino.providers.IProvider;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class WikipediaProvider implements IProvider {

  String INDEX = "enwiki";
  String HOST = "35.192.164.92";
  int PORT = 9200;

  ElasticsearchClient client;

  public WikipediaProvider() {
    // Create the low-level client
    RestClient restClient = RestClient.builder(
        new HttpHost(HOST, PORT)).build();

    // Create the transport with a Jackson mapper
    ElasticsearchTransport transport = new RestClientTransport(
        restClient, new JacksonJsonpMapper());

    // And create the API client
    client = new ElasticsearchClient(transport);
  }

  @Override
  public List<Suggestion> query(String queryString) {
    SearchResponse<WikipediaDoc> response;
    try {
      response = this.client.search(s -> s
          .index(INDEX)
          .fields(f -> f.field("title"))
          .size(2)
          .query(q -> q
              .bool(b -> b
                  .must(q1 -> q1
                      .term(q2 -> q2.field("title.prefix").value(queryString))
                  )
                  .should(s1 -> s1.rankFeature(r0 -> r0.field("incoming_links.rank")))
                  .should(s1 -> s1.rankFeature(r0 -> r0.field("popularity_score.rank").boost(10000F)))
                  .should(s1 -> s1.rankFeature(r0 -> r0.field("title_length")))
              )
          ),
          WikipediaDoc.class
      );
      return toSuggestions(response);
    } catch (IOException e) {
      return List.of();
    }
  }

  private List<Suggestion> toSuggestions(SearchResponse<WikipediaDoc> response) {
    return response.hits().hits().stream().map((hit) -> Suggestion.builder()
        .blockId(Integer.parseInt(hit.id()))
        .fullKeyword(hit.source().title().toLowerCase())
        .title(hit.source().title())
        .url(toWikiUrl(hit.source().title()))
        .icon("https://en.wikipedia.org/static/favicon/wikipedia.ico")
        .impressionUrl("")
        .clickUrl("")
        .provider("wikipedia")
        .advertiser("wikipedia")
        .isSponsored(false)
        .score(0.5)
        .build()).collect(Collectors.toList());
  }

  private String toWikiUrl(String title) {
    String base = "https://en.wikipedia.org/wiki/";
    return base + URLEncoder.encode(
        title.replace(" ", "_"), StandardCharsets.UTF_8);
  }
}
