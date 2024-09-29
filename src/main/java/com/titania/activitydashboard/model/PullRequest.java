package com.titania.activitydashboard.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PullRequest {
    private String id;

    private String title;

    private String url;

    private String state;

    private LocalDateTime createdAt;
}
