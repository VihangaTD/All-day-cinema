package com.alldaycinema.alldaycinema.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoResponse {

    private long id;
    private String title;
    private String description;
    private Integer year;
    private String rating;
    private Integer duration;
    private String src;
    private String poster;
    private boolean published;
    private List<String> categories;
    private Instant createdAt;
    private Instant updatedAt;
    private boolean isInWatchlist;

}
