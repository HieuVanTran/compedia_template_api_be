package vn.compedia.api.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import vn.compedia.api.entity.Role;
import vn.compedia.api.repository.RoleRepository;
import vn.compedia.api.request.RoleCreateRequest;

import java.util.List;

@Log4j2
@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAll() {
        List<Role> list = roleRepository.findAll();
        return list;
    }

//    public Page<Role> search(String sortField, String sortOrder, Integer page, Integer size){
//        return roleRepository.search(sortField, sortOrder, page, size, PageRequest.of(page, size));
//    }

    public Role getOne(Long roleId) {
        Role role = roleRepository.findById(roleId).orElse(null);
        if (role == null) {
            role = new Role();
        }
        return role;
    }

    public void create(RoleCreateRequest request) {
        Role role = new Role();
        role.setCodeRole(request.getCodeRole());
        role.setName(request.getName());
        roleRepository.save(role);
    }

    public void update(RoleCreateRequest request) {
        Role role = new Role();
        role.setRoleId(request.getRoleId());
        role.setCodeRole(request.getCodeRole());
        role.setName(request.getName());
        roleRepository.save(role);
    }

    public void delete(Long id) {

        roleRepository.deleteById(id);
    }
}
