package com.faniko.api_faniko.services;

import com.faniko.api_faniko.annotations.FanikoTest;
import com.faniko.api_faniko.exceptions.NotFoundException;
import com.faniko.api_faniko.models.Role;
import com.faniko.api_faniko.utils.enums.role.RoleNameEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

@FanikoTest
public class RoleServiceTest {

    @Autowired
    private RoleService roleService;

    @Test
    public void testGetAllRoles() {
        List<Role> roles = roleService.getAll();

        assertEquals(1, roles.size());
        assertEquals(RoleNameEnum.ADMIN, roles.getFirst().getName());
    }

    @Test
    public void testGetRoleById() {
        Role role = roleService.find("1");
        assertEquals(RoleNameEnum.ADMIN, role.getName());
    }

    @Test
    public void testSaveRole() {
        int sizeBefore = roleService.getAll().size();

        Role role = new Role();
        role.setName(RoleNameEnum.USER);
        role = roleService.create(role);

        List<Role> roles = roleService.getAll();
        assertEquals(sizeBefore + 1, roles.size());
        assertEquals(RoleNameEnum.USER, roles.getLast().getName());
    }

    @Test
    public void testUpdateRole() {
        Role role = roleService.find("1");
        role.setName(RoleNameEnum.USER);
        roleService.update("1", role);

        Role updatedRole = roleService.find("1");
        assertEquals(RoleNameEnum.USER, updatedRole.getName());
    }

    @Test
    public void testDeleteRole() {
        int sizeBefore = roleService.getAll().size();

        roleService.delete("1");

        List<Role> roles = roleService.getAll();
        assertEquals(sizeBefore - 1, roles.size());
        assertThrows(NotFoundException.class, () -> roleService.find("1"));
    }

    @Test
    public  void testGetRoleShouldThrowNotFoundException() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            roleService.find("2");
        });

        assertEquals("Entity not found with id: 2", exception.getReason());
        assertEquals(HttpStatus.NOT_FOUND.value(), exception.getStatusCode().value());
    }
}