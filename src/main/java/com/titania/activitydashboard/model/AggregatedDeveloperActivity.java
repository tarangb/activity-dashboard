package com.titania.activitydashboard.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AggregatedDeveloperActivity {

    private String username;

    private int totalCommits;

    private int totalPullRequests;

    private int totalComments;

    private double averageCommitsPerRepo;

    private double pullRequestMergeRate;

    private String mostActiveRepo;

    private List<GitHubCommit> recentCommits;

    private List<Comment> recentComments;

    private List<PullRequest> recentPullRequests;
}
