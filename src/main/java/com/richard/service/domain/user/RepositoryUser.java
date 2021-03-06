package com.richard.service.domain.user;

import com.richard.service.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryUser extends JpaRepository<User, Long> {
    User findByQqOpenId(String qqOpenId);
    User findByWxUnionId(String wxUnionId);
    User findByUniqueName(String uniqueName);
    User findByName(String name);

    @Override
    Page<User> findAll(Pageable pageable);

    User findById(long id);


//    @Query("from User u where u.name=:name")
//    User findUser(@Param("name") String name);

}
