package com.faniko.api_faniko.models;

import com.faniko.api_faniko.models.base.BaseEntity;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class User extends BaseEntity {
    @Field(name = "login", targetType = FieldType.STRING)
    private String login;

    @Field(name = "password", targetType = FieldType.STRING)
    private String password;
}
