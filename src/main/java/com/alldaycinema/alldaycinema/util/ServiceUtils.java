package com.alldaycinema.alldaycinema.util;

import com.alldaycinema.alldaycinema.entity.User;
import com.alldaycinema.alldaycinema.entity.Video;
import com.alldaycinema.alldaycinema.exception.ResourceNotFoundException;
import com.alldaycinema.alldaycinema.repo.UserRepository;
import com.alldaycinema.alldaycinema.repo.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceUtils {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VideoRepository videoRepository;

    public User getUserByEmailOrThrow(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(()->new ResourceNotFoundException("User not found with email "+email));
    }

    public User getUserByIdOrThrow(Long id){
        return userRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("User not found with id "+id));
    }

    public Video getVideoByIdOrThrow(Long id){
        return videoRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Video not found with id "+id));
    }

}
