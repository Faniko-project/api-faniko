package com.faniko.api_faniko.models;

import com.faniko.api_faniko.models.base.BaseEntity;
import com.faniko.api_faniko.utils.enums.role.RoleNameEnum;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "roles")
public class Role extends BaseEntity {
    @Field(name = "name", targetType = FieldType.STRING)
    @Indexed(unique = true)
    @NotNull
    private RoleNameEnum name;
}
