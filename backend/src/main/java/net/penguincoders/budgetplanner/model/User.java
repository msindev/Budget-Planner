package net.penguincoders.budgetplanner.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    private ObjectId id;

    @NonNull
    @NotBlank(message = "Username cannot be empty")
    @Size(max = 20, message = "Username must be less than 20 characters")
    private String username;

    @NonNull
    @NotBlank(message = "Email cannot be empty")
    @Size(max = 50)
    @Email(message = "Email should be valid")
    private String email;

    @NonNull
    @NotBlank(message = "Password cannot be empty")
    @Size(max = 120)
    private String password;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
