package net.penguincoders.budgetplanner.dto.response;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
@RequiredArgsConstructor
public class JwtResponse {
    @NonNull
    private String token;
    @NonNull
    private ObjectId id;
    @NonNull
    private String username;
    @NonNull
    private String email;

    private String type = "Bearer";
}