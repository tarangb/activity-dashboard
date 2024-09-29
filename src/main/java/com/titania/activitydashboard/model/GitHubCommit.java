package com.titania.activitydashboard.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GitHubCommit {
    private String url;
    private String sha;
    private String node_id;
    private String html_url;
    private String comments_url;
    private Commit commit;
    private User author;
    private User committer;
    private List<Parent> parents;
    private Stats stats;
    private List<File> files;

    // Nested classes for different parts of the response
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Commit {
        private String url;
        private Author author;
        private Author committer;
        private String message;
        private Tree tree;
        private int comment_count;
        private Verification verification;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Author {
        private String name;
        private String email;
        private String date;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Tree {
        private String url;
        private String sha;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Verification {
        private boolean verified;
        private String reason;
        private String signature;
        private String payload;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User {
        private String login;
        private int id;
        private String node_id;
        private String avatar_url;
        private String gravatar_id;
        private String url;
        private String html_url;
        private String followers_url;
        private String following_url;
        private String gists_url;
        private String starred_url;
        private String subscriptions_url;
        private String organizations_url;
        private String repos_url;
        private String events_url;
        private String received_events_url;
        private String type;
        private boolean site_admin;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Parent {
        private String url;
        private String sha;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Stats {
        private int additions;
        private int deletions;
        private int total;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class File {
        private String filename;
        private int additions;
        private int deletions;
        private int changes;
        private String status;
        private String raw_url;
        private String blob_url;
        private String patch;
    }
}
