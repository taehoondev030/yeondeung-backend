package me.taehoon.yeondeung.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.taehoon.yeondeung.domain.Wish;
import me.taehoon.yeondeung.dto.AddWishRequest;
import me.taehoon.yeondeung.dto.UpdateWishRequest;
import me.taehoon.yeondeung.jwt.JWTUtil;
import me.taehoon.yeondeung.repository.LanternRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor // final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
@Service // 빈으로 등록
public class LanternService {

    private final LanternRepository lanternRepository;
    private final JWTUtil jwtUtil;

    // 유저별 소망 조회
    public List<Wish> getWish(String accessToken) {
        // JWT 토큰에서 사용자 이름을 추출
        String username = jwtUtil.getUsername(accessToken);

        return lanternRepository.findByUserUsername(username);
    }

    // 소망 추가 메서드
    public Wish save(AddWishRequest request) {
        return lanternRepository.save(request.toEntity());
    }

    // 소망 조회 메서드
    public List<Wish> findAll() {
        return lanternRepository.findAll();
    }

    // 소망 1개 조회 메서드
    public Wish findById(long id) {
        return lanternRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    // 소망 삭제 메서드
    public void delete(long id) {
        lanternRepository.deleteById(id);
    }

    // 소망 수정 메서드
    @Transactional // 트랜잭션 메서드
    public Wish update(long id, UpdateWishRequest request) {
        Wish wish = lanternRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        wish.update(request.getContent());

        return wish;
    }
}