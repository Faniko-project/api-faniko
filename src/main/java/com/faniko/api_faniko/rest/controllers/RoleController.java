package com.faniko.api_faniko.rest.controllers;

import com.faniko.api_faniko.models.Role;
import com.faniko.api_faniko.services.RoleService;
import com.faniko.api_faniko.utils.constants.AuthoritiesConstants;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.faniko.api_faniko.utils.constants.AppConstants.API_BASE_URL_V1;

@RestController
@RequestMapping(API_BASE_URL_V1 + "/roles")
@Slf4j
@PreAuthorize("hasAnyRole('"+ AuthoritiesConstants.ADMIN +"')")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping(value = "", produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<List<Role>> getAll() {
        log.info("start GET {}/roles", API_BASE_URL_V1);

        return ResponseEntity.ok(roleService.getAll());
    }

    @GetMapping(value = "/{id}", produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<Role> getById(@PathVariable String id) {
        log.info("start GET {}/roles/{}", API_BASE_URL_V1, id);

        return ResponseEntity.ok(roleService.find(id));
    }

    @PostMapping(value = "", consumes = {"application/json;charset=UTF-8"}, produces = {"application/json;charset=UTF-8"})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Role> create(@RequestBody @Valid Role role) {
        log.info("start POST {}/roles", API_BASE_URL_V1);

        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.create(role));
    }

    @PutMapping(value = "/{id}", consumes = {"application/json;charset=UTF-8"}, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<Role> update(@PathVariable String id, @RequestBody @Valid Role role) {
        log.info("start PUT {}/roles/{}", API_BASE_URL_V1, id);

        return ResponseEntity.ok(roleService.update(id, role));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.info("start DELETE {}/roles/{}", API_BASE_URL_V1, id);

        roleService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
