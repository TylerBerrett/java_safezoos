package local.tylerb.zoo;

import local.tylerb.zoo.model.User;
import local.tylerb.zoo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class SeedData implements CommandLineRunner {

    @Autowired
    UserService userService;

    @Override
    public void run(String... args) throws Exception {
        List<User> userList = new ArrayList<>();
        userService.findAll().iterator().forEachRemaining(userList::add);

        for (User u : userList) {
            u.setUserroles(new ArrayList<>());
            userService.update(u, u.getUserid());
        }

    }
}
