
package org.mozilla.jarino;

import org.mozilla.jarino.providers.IProvider;
import org.mozilla.jarino.providers.adm.AdmProvider;
import org.mozilla.jarino.providers.elasticsearch.WikipediaProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Defines a controller to handle HTTP requests.
 */
@RestController
public final class JarinoController {

    Map<String, IProvider> providerMap = Map.of();

    // register new providers here
    public Map<String, IProvider> getProviderMap() {
        return Map.of(
            "adm", new AdmProvider(),
            "wiki", new WikipediaProvider());
    }

    public JarinoController() {
        providerMap = getProviderMap();
    }

    /**
     * Create an endpoint for the landing page
     * @return the index view template with a simple message
     */
    @GetMapping("/api/v1/suggest")
    public SuggestionResponse search(
        @RequestParam(value = "providers", defaultValue = "") String providers,
        @RequestParam(value = "q", defaultValue = "") String q) {
        List<Suggestion> suggestions = getProviders(providers).parallelStream()
            .map((provider) -> provider.query(q))
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
        return SuggestionResponse.builder()
            .requestId(UUID.randomUUID().toString())
            .suggestions(suggestions)
            .build();
    }

    public List<IProvider> getProviders(String providerParam) {
        if (providerParam.isEmpty()) {
            List<IProvider> providers = providerMap.values().stream()
                .filter(p -> p.defaultEnabled())
                .collect(Collectors.toList());
            return providers;
        }
        List<String> providerIdList = Arrays.asList(providerParam.split(",", -1));
        return providerIdList.stream()
            .map((id) -> providerMap.get(id))
            .collect(Collectors.toList());
    }
}
