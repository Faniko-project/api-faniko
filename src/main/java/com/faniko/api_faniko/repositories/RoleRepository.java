package com.faniko.api_faniko.repositories;

import com.faniko.api_faniko.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {
    Role findByName(String name);
}
