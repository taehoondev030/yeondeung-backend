package me.taehoon.yeondeung.repository;

import me.taehoon.yeondeung.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Boolean existsByUsername(String username);

    // username을 받아 DB 테이블에서 회원을 조회하는 메서드
    UserEntity findByUsername(String username);
}
