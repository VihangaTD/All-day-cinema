package com.alldaycinema.alldaycinema.service;

import com.alldaycinema.alldaycinema.dto.request.VideoRequest;
import com.alldaycinema.alldaycinema.dto.response.MessageResponse;
import com.alldaycinema.alldaycinema.dto.response.PageResponse;
import com.alldaycinema.alldaycinema.dto.response.VideoResponse;
import com.alldaycinema.alldaycinema.dto.response.VideoStatsResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface VideoService {
    MessageResponse createVideoByAdmin(@Valid VideoRequest videoRequest);

    PageResponse<VideoResponse> getAllAdminVideos(int page, int size, String search);

    MessageResponse updateVideoByAdmin(Long id, @Valid VideoRequest videoRequest);

    MessageResponse deleteVideoByAdmin(Long id);

    MessageResponse toggleVideoPublishStatusByAdmin(Long id, boolean value);

    VideoStatsResponse getAdminStats();

    PageResponse<VideoResponse> getPublishedVideos(int page, int size, String search, String email);

    List<VideoResponse> getFeaturedVideos();
}
