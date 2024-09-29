package com.titania.activitydashboard.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepositoryDeveloperActivity {
    private List<GitHubCommit> commits;

    private List<PullRequest> pullRequests;

    private List<Comment> comments;
}
