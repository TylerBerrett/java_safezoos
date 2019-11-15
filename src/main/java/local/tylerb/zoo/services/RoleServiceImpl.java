package local.tylerb.zoo.services;

//import com.lambdaschool.usermodel.exceptions.ResourceFoundException;
import local.tylerb.zoo.exceptions.ResourceNotFoundException;
import local.tylerb.zoo.model.Role;
import local.tylerb.zoo.repo.RoleRepository;
import local.tylerb.zoo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

//@Loggable
@Service(value = "roleService")
public class RoleServiceImpl implements RoleService
{
    @Autowired
    RoleRepository rolerepos;

    @Autowired
    UserRepository userrepos;

    @Autowired
    UserAuditing userAuditing;

    @Override
    public List<Role> findAll()
    {
        List<Role> list = new ArrayList<>();
        rolerepos.findAll()
                 .iterator()
                 .forEachRemaining(list::add);
        return list;
    }


    @Override
    public Role findRoleById(long id)
    {
        return rolerepos.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Role id " + id + " not found!"));
    }

    @Override
    public Role findByName(String name)
    {
        Role rr = rolerepos.findByNameIgnoreCase(name);

        if (rr != null)
        {
            return rr;
        } else
        {
            throw new ResourceNotFoundException(name);
        }
    }

    @Transactional
    @Override
    public void delete(long id)
    {
        rolerepos.findById(id)
                 .orElseThrow(() -> new ResourceNotFoundException("Role id " + id + " not found!"));
        rolerepos.deleteById(id);
    }


    @Transactional
    @Override
    public Role update(long id,
                       Role role)
    {
        if (role.getName() == null)
        {
            throw new ResourceNotFoundException("No role name found to update!");
        }

        if (role.getUserroles()
                .size() > 0)
        {
            throw new ResourceNotFoundException("User Roles are not updated through Role. See endpoint POST: users/user/{userid}/role/{roleid}");
        }

        Role newRole = findRoleById(id); // see if id exists

        rolerepos.updateRoleName(userAuditing.getCurrentAuditor()
                                             .get(),
                                 id,
                                 role.getName());
        return findRoleById(id);
    }


    @Transactional
    @Override
    public Role save(Role role)
    {
        Role newRole = new Role();
        newRole.setName(role.getName());
        if (role.getUserroles()
                .size() > 0)
        {
            throw new ResourceNotFoundException("User Roles are not updated through Role. See endpoint POST: users/user/{userid}/role/{roleid}");
        }

        return rolerepos.save(role);
    }
}
