package me.taehoon.yeondeung.repository;

import me.taehoon.yeondeung.domain.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanternRepository extends JpaRepository<Wish, Long> {
}
