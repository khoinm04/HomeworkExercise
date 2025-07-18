package sum25.khoinm.homework2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sum25.khoinm.homework2.pojo.Role;
import sum25.khoinm.homework2.repository.RoleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    RoleRepository roleRepository;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> getRoleById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Role> findByRoleName(String roleName) {
        return Optional.empty();
    }

    @Override
    public Role saveRole(Role role) {
        return null;
    }

    @Override
    public void deleteRole(Long id) {

    }
}
