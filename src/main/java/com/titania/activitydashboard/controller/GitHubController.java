package com.titania.activitydashboard.controller;

import com.titania.activitydashboard.model.*;
import com.titania.activitydashboard.service.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class GitHubController {

    private final GitHubService gitHubService;

    @Autowired
    public GitHubController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @PostMapping("/{username}/repos/activity")
    public ResponseEntity<AggregatedDeveloperActivity> getDeveloperActivities(
            @PathVariable String username, @RequestBody List<String> repos) {

        AggregatedDeveloperActivity activity = gitHubService.getAggregatedDeveloperActivity(username, repos);
        return ResponseEntity.ok(activity);
    }
}
