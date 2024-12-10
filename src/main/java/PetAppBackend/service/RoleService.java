package PetAppBackend.service;


import PetAppBackend.model.Role;
import PetAppBackend.repo.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;



    //dodajem rolu

    public Role addRole(Role role){
        return roleRepository.save(role);
    }

    //dohvatam sve role

    public List<Role> findAllRoles(){
        return roleRepository.findAll();
    }

    //pronalazim po imenu

    public Optional<Role> findRoleByName(String name){
        return roleRepository.findByName(name);
    }
}
