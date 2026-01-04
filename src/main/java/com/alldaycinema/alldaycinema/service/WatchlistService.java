package com.alldaycinema.alldaycinema.service;

import com.alldaycinema.alldaycinema.dto.response.MessageResponse;
import com.alldaycinema.alldaycinema.dto.response.PageResponse;
import com.alldaycinema.alldaycinema.dto.response.VideoResponse;

public interface WatchlistService {
    MessageResponse addToWatchlist(String email, Long videoId);

    MessageResponse removeFromWatchlist(Long videoId, String email);

    PageResponse<VideoResponse> getWatchlistPaginated(String email, int page, int size, String search);
}
