package local.tylerb.zoo.services;


import local.tylerb.zoo.model.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService
{
    List<User> findAll();

    List<User> findByNameContaining(String username,
                                    Pageable pageable);

    User findUserById(long id);

    User findByName(String name);

    void delete(long id);

    User save(User user);

    User update(User user,
                long id);

    void deleteUserRole(long userid,
                        long roleid);

    void addUserRole(long userid,
                     long roleid);

    //List<UserNameCountEmails> getCountUserEmails();
}