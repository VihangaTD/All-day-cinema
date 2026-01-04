package com.alldaycinema.alldaycinema.service.serviceImpl;

import com.alldaycinema.alldaycinema.dto.response.MessageResponse;
import com.alldaycinema.alldaycinema.dto.response.PageResponse;
import com.alldaycinema.alldaycinema.dto.response.VideoResponse;
import com.alldaycinema.alldaycinema.entity.User;
import com.alldaycinema.alldaycinema.entity.Video;
import com.alldaycinema.alldaycinema.repo.UserRepository;
import com.alldaycinema.alldaycinema.repo.VideoRepository;
import com.alldaycinema.alldaycinema.service.WatchlistService;
import com.alldaycinema.alldaycinema.util.PaginationUtils;
import com.alldaycinema.alldaycinema.util.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class WatchlistServiceImpl implements WatchlistService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private ServiceUtils serviceUtils;

    @Override
    public MessageResponse addToWatchlist(String email, Long videoId) {
        User user = serviceUtils.getUserByEmailOrThrow(email);

        Video video = serviceUtils.getVideoByIdOrThrow(videoId);

        user.addToWatchList(video);
        userRepository.save(user);
        return new MessageResponse("Video added to watchlist successfully");
    }

    @Override
    public MessageResponse removeFromWatchlist(Long videoId, String email) {
        User user = serviceUtils.getUserByEmailOrThrow(email);
        Video video = serviceUtils.getVideoByIdOrThrow(videoId);

        user.removeFronWatchList(video);
        userRepository.save(user);
        return new MessageResponse("Video removed from watchlist successfully");
    }

    @Override
    public PageResponse<VideoResponse> getWatchlistPaginated(String email, int page, int size, String search) {
        User user = serviceUtils.getUserByEmailOrThrow(email);
        Pageable pageable = PaginationUtils.createPageRequest(page,size);
        Page<Video> videoPage;

        if (search != null && !search.trim().isEmpty()){
            videoPage = userRepository.searchWatchlistByUserId(user.getId(),search.trim(),pageable);
        }else {
            videoPage = userRepository.findWatchlistByUserId(user.getId(),pageable);
        }

        return PaginationUtils.toPageResponse(videoPage, VideoResponse::fromEntity);
    }
}
