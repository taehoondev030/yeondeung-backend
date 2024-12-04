package me.taehoon.yeondeung.repository;

import me.taehoon.yeondeung.domain.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LanternRepository extends JpaRepository<Wish, Long> {
    List<Wish> findByUserUsername(String username);
}
