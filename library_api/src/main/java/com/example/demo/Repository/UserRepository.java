package com.example.demo.Repository;

import com.example.demo.Model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Modifying(clearAutomatically = true)
    @Query("update #{#entityName} u set u.registerBookCount=:registeredBookCount+1 where u.id=:id")
    int incrementRegisteredBookCount(@Param("id") Integer id);
}
