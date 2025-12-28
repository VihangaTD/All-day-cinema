package com.alldaycinema.alldaycinema.repo;

import com.alldaycinema.alldaycinema.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
