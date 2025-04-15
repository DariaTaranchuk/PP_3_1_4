package ru.kata.spring.boot_security.demo.service;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.Collection;
import java.util.Set;

@Service
public class RoleRepositoryServiceImpl {
    private RoleRepository roleRepository;

    public RoleRepositoryServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Set<Role> findByIdIn(@Param("ids") Collection<Long> ids){
        return roleRepository.findByIdIn(ids);
    }

    public Object findAll() {
        return roleRepository.findAll();
    }
}
