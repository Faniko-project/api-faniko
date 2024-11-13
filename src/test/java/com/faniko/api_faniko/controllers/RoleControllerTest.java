package com.faniko.api_faniko.controllers;

import com.faniko.api_faniko.FanikoTest;
import com.faniko.api_faniko.models.Role;
import com.faniko.api_faniko.utils.TestUtils;
import com.faniko.api_faniko.utils.constants.AppConstantsTest;
import com.faniko.api_faniko.utils.constants.AuthoritiesConstantsTest;
import com.faniko.api_faniko.utils.enums.role.RoleNameEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Role Controller Test")
public class RoleControllerTest extends FanikoTest {
    private static final String BASE_PATH = AppConstantsTest.API_BASE_URL_V1 + "/roles";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails(AuthoritiesConstantsTest.ADMIN)
    @DisplayName("Get all roles with ADMIN role")
    public void testGetAllRoles() throws Exception {
        this.mockMvc.perform(get(BASE_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("ADMIN"));
    }

    @Test
    @WithUserDetails(AuthoritiesConstantsTest.ADMIN)
    @DisplayName("Get one role with ADMIN role")
    public void testGetRoleById() throws Exception {
        this.mockMvc.perform(get(BASE_PATH + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("ADMIN"));
    }

    @Test
    @WithUserDetails(AuthoritiesConstantsTest.ADMIN)
    @DisplayName("Save role with ADMIN role")
    public void testSaveRole() throws Exception {
        Role role = Role.builder().name(RoleNameEnum.USER).build();

        this.mockMvc.perform(post(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.convertObjectToJsonBytes(role)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("USER"));
    }

    @Test
    @WithUserDetails(AuthoritiesConstantsTest.ADMIN)
    @DisplayName("Update role with ADMIN role")
    public void testUpdateRole() throws Exception {
        Role role = Role.builder().name(RoleNameEnum.CLIENT).build();

        this.mockMvc.perform(put(BASE_PATH + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.convertObjectToJsonBytes(role)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("CLIENT"));
    }

    @Test
    @WithUserDetails(AuthoritiesConstantsTest.ADMIN)
    @DisplayName("Delete role with ADMIN role")
    public void testDeleteRole() throws Exception {
        this.mockMvc.perform(delete(BASE_PATH + "/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithUserDetails()
    @DisplayName("Get all roles with USER role should return forbidden because it requires ADMIN role")
    public void testGetAllRolesForbidden() throws Exception {
        this.mockMvc.perform(get(BASE_PATH))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails()
    @DisplayName("Get one role with USER role should return forbidden because it requires ADMIN role")
    public void testGetOneRoleForbidden() throws Exception {
        this.mockMvc.perform(get(BASE_PATH + "/1"))
                .andExpect(status().isForbidden());
    }
}
