package com.ssamz.demo.persistance;

import com.ssamz.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // SELECT * FROM user WHERE username = ?1;
    Optional<User> findByUsername(String username);	// JPQL
    // Optional은 NULL 값을 직접 다루는 것을 피하고자 JAVA 8에서 도입된 클래스이며
    // User 객체가 있을수도 있고 없을수도 있음을 나타내줌. username을 가진 User가 없으면
    // Optional.empty() 가 반환된다.
}
