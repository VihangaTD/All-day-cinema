package com.alldaycinema.alldaycinema.dto.response;

import com.alldaycinema.alldaycinema.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private long id;
    private String email;
    private String fullName;
    private String role;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;

    public static UserResponse fromEntity(User user){
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getRole().name(),
                user.isActive(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
