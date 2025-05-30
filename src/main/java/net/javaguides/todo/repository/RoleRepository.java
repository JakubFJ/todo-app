package net.javaguides.todo.repository;

import net.javaguides.todo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
