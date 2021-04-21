package ch.uzh.ifi.hase.soprafs21.repository;


import ch.uzh.ifi.hase.soprafs21.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String name);

    User findByUsername(String username);

    User findByEmail(String email);

    /*
     default List<User> findByOnlineUsers() {
         return findOnline(UserStatus.ONLINE);
     }
    */

    List<User> findByStatus(UserStatus status);

}
