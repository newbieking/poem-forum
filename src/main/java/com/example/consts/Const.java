package com.example.consts;

import java.util.List;

public class Const {


    public static class Redis {
        public static final String LOGIN_PREFIX = "login";
        public static final String RECENT_Visit_PREFIX = "recent_visit";
        public static final String RECENT_SEARCH_PREFIX = "recent_search";
        public static final Long LOGIN_DEFAULT_EXPIRED_TIME = 24 * 60 * 60L;
        public static final Long RECENT_VISIT_DEFAULT_SIZE = 10L;
        public static final Long RECENT_SEARCH_DEFAULT_SIZE = 10L;
        public static final String TOP_RANK = "top_rank";
        public static final long TOP_RANK_DEFAULT_SIZE = 10;

        public static String loginUserId(String userId) {
            return LOGIN_PREFIX + ":" + userId;
        }

        public static String recentVisit(String userId) {
            return RECENT_Visit_PREFIX + ":" + userId;
        }

        public static String recentSearch(String userId) {
            return RECENT_SEARCH_PREFIX + ":" + userId;
        }
    }

    public static final String POEM_DOC = "poem_collect";

    public static class PoemDoc {
        public static final String title = "title";
        public static final String author = "author";
        public static final String content = "content";

        public static final List<String> all = List.of(title, author, content);
    }
}
