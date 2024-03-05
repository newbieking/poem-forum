package com.example.service;

import java.util.List;

/**
 * service for recent poem record
 */
public interface RecentPoemService {

    /**
     * update recent poems id list
     * store 10 poems recently visited
     * @param recentPoems recent poems to be updated
     */
    void updateRecentVisitedPoems(String userId, List<Long> recentPoems);

    void updateRecentSearch(String userId, List<String> recentSearches);

}
