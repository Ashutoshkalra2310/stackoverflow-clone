package io.mountblue.stackoverflowclone.repository;

import io.mountblue.stackoverflowclone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

}
