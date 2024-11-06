package com.faniko.api_faniko.services;

import com.faniko.api_faniko.models.Role;
import com.faniko.api_faniko.repositories.RoleRepository;
import com.faniko.api_faniko.services.interfaces.ICrudService;
import com.faniko.api_faniko.utils.enums.role.RoleNameEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    /**
     * Get default roles
     * @return list of default roles {@link List<String>}
     */
    public List<String> defaultRoles() {
        return List.of(RoleNameEnum.USER.toString(), RoleNameEnum.ADMIN.toString());
    }
}
