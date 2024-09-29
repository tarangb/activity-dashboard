package com.titania.activitydashboard.controller;

import com.titania.activitydashboard.model.AggregatedDeveloperActivity;
import com.titania.activitydashboard.service.GitHubService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class GitHubControllerTest {

    @Mock
    private GitHubService gitHubService;

    @InjectMocks
    private GitHubController gitHubController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetDeveloperActivities() {
        String username = "username";
        var repos = Arrays.asList("Hello-World", "Spoon-Knife");

        // Create a mock AggregatedDeveloperActivity object
        AggregatedDeveloperActivity mockActivity = new AggregatedDeveloperActivity(
                username, 5, 3, 10, 2.5, 0.6, "Hello-World",
                Collections.emptyList(), Collections.emptyList(), Collections.emptyList()
        );

        // Define the behavior of the gitHubService mock
        when(gitHubService.getAggregatedDeveloperActivity(username, repos)).thenReturn(mockActivity);

        // Call the controller method
        ResponseEntity<AggregatedDeveloperActivity> response = gitHubController.getDeveloperActivities(username, repos);

        // Assertions
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockActivity, response.getBody());
    }

    @Test
    public void testGetDeveloperActivities_EmptyRepoList() {
        String username = "octocat";
        List<String> repos = Collections.emptyList();

        // Create a mock AggregatedDeveloperActivity object
        AggregatedDeveloperActivity mockActivity = new AggregatedDeveloperActivity(
                username, 0, 0, 0, 0, 0, null,
                Collections.emptyList(), Collections.emptyList(), Collections.emptyList()
        );

        // Define the behavior of the gitHubService mock
        when(gitHubService.getAggregatedDeveloperActivity(username, repos)).thenReturn(mockActivity);

        // Call the controller method
        ResponseEntity<AggregatedDeveloperActivity> response = gitHubController.getDeveloperActivities(username, repos);

        // Assertions
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockActivity, response.getBody());
    }
}
