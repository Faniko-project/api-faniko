package com.faniko.api_faniko.services;

import com.faniko.api_faniko.models.Role;
import com.faniko.api_faniko.repositories.RoleRepository;
import com.faniko.api_faniko.services.interfaces.ICrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RoleService implements ICrudService<Role, RoleRepository> {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public RoleRepository getRepository() {
        return roleRepository;
    }

    @Override
    public Role formatEntityToUpdate(Role entityToUpdate, Role entity) {
        entityToUpdate.setName(entity.getName());
        entityToUpdate.setUpdatedAt(LocalDateTime.now());
        return entityToUpdate;
    }
}
