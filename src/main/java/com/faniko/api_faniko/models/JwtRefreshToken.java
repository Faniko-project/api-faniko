package com.faniko.api_faniko.models;

import com.faniko.api_faniko.models.base.BaseEntity;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "jwt_refresh_tokens")
public class JwtRefreshToken extends BaseEntity {
    @Field(name = "token", targetType = FieldType.STRING)
    @Indexed(unique = true)
    private String token;

    @DBRef
    private User user;

    @Field(name = "is_blocked", targetType = FieldType.BOOLEAN)
    private Boolean isBlocked = Boolean.FALSE;
}
