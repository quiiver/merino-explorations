package org.mozilla.jarino.providers;

import org.mozilla.jarino.Suggestion;

import java.util.List;

public interface IProvider {
  public List<Suggestion> query(String queryString);
}
