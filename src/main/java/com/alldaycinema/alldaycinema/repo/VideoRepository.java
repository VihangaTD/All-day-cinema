package com.alldaycinema.alldaycinema.repo;

import com.alldaycinema.alldaycinema.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video,Long> {
}
