
package org.mozilla.jarino;

import org.mozilla.jarino.providers.IProvider;
import org.mozilla.jarino.providers.adm.AdmProvider;
import org.mozilla.jarino.providers.elasticsearch.WikipediaProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Defines a controller to handle HTTP requests.
 */
@RestController
public final class JarinoController {

    List<IProvider> providerList = List.of();

    // register new providers here
    public List<IProvider> getProviderList() {
        return List.of(new AdmProvider(), new WikipediaProvider());
    }

    public JarinoController() {
        providerList = getProviderList();
    }

    /**
     * Create an endpoint for the landing page
     * @return the index view template with a simple message
     */
    @GetMapping("/search")
    public SuggestionResponse search(@RequestParam(value = "q", defaultValue = "") String q) {
        List<Suggestion> suggestions = providerList.parallelStream()
                .map((provider) -> provider.query(q))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        return SuggestionResponse.builder()
            .requestId("this-is-a-request")
            .suggestions(suggestions)
            .build();
    }
}
