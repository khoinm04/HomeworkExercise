package sum25.khoinm.homework2.service;

import sum25.khoinm.homework2.pojo.Role;
import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> getAllRoles();
    Optional<Role> getRoleById(Long id);
    Optional<Role> findByRoleName(String roleName);
    Role saveRole(Role role);
    void deleteRole(Long id);
}