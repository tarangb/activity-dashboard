package com.titania.activitydashboard.service;

import com.titania.activitydashboard.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class GitHubService {

    private String githubApiUrl;

    private final RestTemplate restTemplate;

    @Autowired
    public GitHubService(RestTemplate restTemplate, @Value("${github.api.url}") String githubApiUrl) {
        this.restTemplate = restTemplate;
        this.githubApiUrl = githubApiUrl;
    }

    public AggregatedDeveloperActivity getAggregatedDeveloperActivity(String username, List<String> repos) {
        int totalCommits = 0;
        int totalPullRequests = 0;
        int totalComments = 0;
        String mostActiveRepo = "";
        int maxRepoActivity = 0;
        List<GitHubCommit> recentCommits = new ArrayList<>();
        List<Comment> recentComments = new ArrayList<>();
        List<PullRequest> recentPullRequests = new ArrayList<>();

        for (String repo : repos) {
            RepositoryDeveloperActivity activity = repositoryDeveloperActivity(username, repo);

            totalCommits += activity.getCommits().size();
            totalPullRequests += activity.getPullRequests().size();
            totalComments += activity.getComments().size();

            // Collect recent commits and comments
            recentCommits.addAll(activity.getCommits());
            recentComments.addAll(activity.getComments());
            recentPullRequests.addAll(activity.getPullRequests());

            // Find the most active repository
            int repoActivity = activity.getCommits().size() + activity.getPullRequests().size() + activity.getComments().size();
            if (repoActivity > maxRepoActivity) {
                maxRepoActivity = repoActivity;
                mostActiveRepo = repo;
            }
        }

        double averageCommitsPerRepo = (repos.isEmpty()) ? 0 : (double) totalCommits / repos.size();
        double pullRequestMergeRate = calculatePullRequestMergeRate(recentPullRequests);

        return new AggregatedDeveloperActivity(username, totalCommits, totalPullRequests, totalComments,
                averageCommitsPerRepo, pullRequestMergeRate, mostActiveRepo,
                recentCommits, recentComments, recentPullRequests);
    }

    public RepositoryDeveloperActivity repositoryDeveloperActivity(String username, String repo) {
        List<GitHubCommit> commits = getCommits(username, repo);
        List<PullRequest> pullRequests = getPullRequests(username, repo);
        List<Comment> comments = getComments(username, repo);

        return new RepositoryDeveloperActivity(commits, pullRequests, comments);
    }

    private List<GitHubCommit> getCommits(String username, String repo) {
        String url = String.format("%s/repos/%s/%s/commits", githubApiUrl, username, repo);
        GitHubCommit[] commits = restTemplate.getForObject(url, GitHubCommit[].class);
        return (commits != null) ? List.of(commits) : new ArrayList<>();
    }

    private List<PullRequest> getPullRequests(String username, String repo) {
        String url = String.format("%s/repos/%s/%s/pulls", githubApiUrl, username, repo);
        PullRequest[] pullRequests = restTemplate.getForObject(url, PullRequest[].class);
        return (pullRequests != null) ? List.of(pullRequests) : new ArrayList<>();
    }

    private List<Comment> getComments(String username, String repo) {
        String url = String.format("%s/repos/%s/%s/comments", githubApiUrl, username, repo);
        Comment[] comments = restTemplate.getForObject(url, Comment[].class);
        return (comments != null) ? List.of(comments) : new ArrayList<>();
    }

    private double calculatePullRequestMergeRate(List<PullRequest> pullRequests) {
        int totalPullRequests = pullRequests.size();

        // Count merged pull requests based on their state
        long mergedPullRequestsCount = pullRequests.stream()
                .filter(pr -> "closed".equals(pr.getState())) // Assuming "closed" indicates a merged state
                .count();

        // Calculate merge rate
        return totalPullRequests == 0 ? 0 : (double) mergedPullRequestsCount / totalPullRequests;
    }
}
