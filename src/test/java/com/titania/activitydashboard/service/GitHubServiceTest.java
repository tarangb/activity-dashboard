package com.titania.activitydashboard.service;

import com.titania.activitydashboard.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class GitHubServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GitHubService gitHubService;

    private static final String GITHUB_API_URL = "http://api.github.com";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        gitHubService = new GitHubService(restTemplate, GITHUB_API_URL);
    }

    @Test
    public void getAggregatedDeveloperActivity() {
        // Given
        String username = "username";
        List<String> repos = List.of("Repo1", "Repo2");

        GitHubCommit commit1 = new GitHubCommit();
        commit1.setSha("6dcb09b5b57875f334f61aebed695e2e4193db5e");

        GitHubCommit commit2 = new GitHubCommit();
        commit2.setSha("7dcb09b5b57875f334f61aebed695e2e4193db5f");

        PullRequest pullRequest1 = new PullRequest();
        pullRequest1.setState("closed"); // Assuming closed indicates merged
        pullRequest1.setTitle("Add feature");

        Comment comment1 = new Comment();
        comment1.setBody("Test comment");

        when(restTemplate.getForObject(anyString(), eq(GitHubCommit[].class))).thenReturn(new GitHubCommit[]{commit1, commit2});

        when(restTemplate.getForObject(anyString(), eq(PullRequest[].class))).thenReturn(new PullRequest[]{pullRequest1});

        when(restTemplate.getForObject(anyString(), eq(Comment[].class))).thenReturn(new Comment[]{comment1});

        AggregatedDeveloperActivity result = gitHubService.getAggregatedDeveloperActivity(username, repos);

        assertEquals(4, result.getTotalCommits());
        assertEquals(2, result.getTotalPullRequests());
        assertEquals(2, result.getTotalComments());
        assertEquals("Repo1", result.getMostActiveRepo()); // Assuming Repo1 is the most active
        assertEquals(2.0, result.getAverageCommitsPerRepo());
        assertEquals(1.0, result.getPullRequestMergeRate()); // Assuming one pull request is merged
    }

    @Test
    public void getDeveloperActivity() {
        String username = "octocat";
        String repo = "Hello-World";

        GitHubCommit commit = new GitHubCommit(); // Mock Commit object setup
        PullRequest pullRequest = new PullRequest(); // Mock PullRequest object setup
        Comment comment = new Comment(); // Mock Comment object setup

        when(restTemplate.getForObject(anyString(), eq(GitHubCommit[].class))).thenReturn(new GitHubCommit[]{commit});
        when(restTemplate.getForObject(anyString(), eq(PullRequest[].class))).thenReturn(new PullRequest[]{pullRequest});
        when(restTemplate.getForObject(anyString(), eq(Comment[].class))).thenReturn(new Comment[]{comment});

        RepositoryDeveloperActivity result = gitHubService.repositoryDeveloperActivity(username, repo);

        assertEquals(1, result.getCommits().size());
        assertEquals(1, result.getPullRequests().size());
        assertEquals(1, result.getComments().size());
    }

    @Test
    public void getDeveloperActivity_NoData() {
        String username = "octocat";
        String repo = "Hello-World";

        when(restTemplate.getForObject(anyString(), eq(GitHubCommit[].class))).thenReturn(null);
        when(restTemplate.getForObject(anyString(), eq(PullRequest[].class))).thenReturn(null);
        when(restTemplate.getForObject(anyString(), eq(Comment[].class))).thenReturn(null);

        RepositoryDeveloperActivity result = gitHubService.repositoryDeveloperActivity(username, repo);

        assertEquals(0, result.getCommits().size());
        assertEquals(0, result.getPullRequests().size());
        assertEquals(0, result.getComments().size());
    }
}
