package service;

import java.util.List;
import model.EventPerformance;

/**
 * Exposes search and retrieval operations for performances.
 */
public interface PerformanceSearchService {
    List<EventPerformance> search(String queryText);

    EventPerformance getPerformance(String performanceId);
}
