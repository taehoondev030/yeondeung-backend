package me.taehoon.yeondeung.controller;

import lombok.RequiredArgsConstructor;
import me.taehoon.yeondeung.domain.Wish;
import me.taehoon.yeondeung.dto.AddWishRequest;
import me.taehoon.yeondeung.dto.UpdateWishRequest;
import me.taehoon.yeondeung.dto.WishResponse;
import me.taehoon.yeondeung.service.LanternService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController // HTTP Response body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
public class LanternApiController {

    private final LanternService lanternService;

    // 유저별 소망 조회 API
    @GetMapping("/wishes")
    public ResponseEntity<List<Wish>> getUserWishes(@RequestHeader("Authorization") String authorizationHeader) {
        // Authorization 헤더에서 Bearer 토큰을 추출
        String accessToken = authorizationHeader.substring(7);  // "Bearer " 제거

        // JWT 토큰을 통해 사용자의 wish 데이터만 가져옴
        List<Wish> wishes = lanternService.getWish(accessToken);

        return ResponseEntity.ok(wishes);
    }

    // HTTP 메서드가 POST일때 전달받는 URL과 동일하면 메서드로 매핑
    @PostMapping("/api/wishes")
    // @RequestBody로 요청 본문 값 매핑
    public ResponseEntity<Wish> addWish(@RequestBody AddWishRequest request) {
        Wish saveWish = lanternService.save(request);

        // 요청한 자원이 성공적으로 생성되었으며 저장된 블로그 글 정보를 응답 객체에 담아 전송
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(saveWish);
    }

    // HTTP 메서드가 GET일때 전달받는 URL과 동일하면 메서드로 매핑
    @GetMapping("/api/wishes")
    public ResponseEntity<List<WishResponse>> findAllWishes() {
        List<WishResponse> wishes = lanternService.findAll()
                .stream()
                .map(WishResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(wishes);
    }

    // HTTP 메서드가 GET일때 전달받는 URL과 동일하면 메서드로 매핑
    @GetMapping("/api/wishes/{id}")
    // URL 경로에서 값 추출
    public ResponseEntity<WishResponse> findWish(@PathVariable long id) {
        Wish wish = lanternService.findById(id);

        return ResponseEntity.ok()
                .body(new WishResponse(wish));
    }

    // HTTP 메서드가 DELETE일때 전달받는 URL과 동일하면 메서드로 매핑
    @DeleteMapping("/api/wishes/{id}")
    public ResponseEntity<Void> deleteWish(@PathVariable long id) {
        lanternService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    // HTTP 메서드가 PUT일때 전달받는 URL과 동일하면 메서드로 매핑
    @PutMapping("/api/wishes/{id}")
    public ResponseEntity<Wish> updateWish(@PathVariable long id,
                                           @RequestBody UpdateWishRequest request) {
        Wish updatedWish = lanternService.update(id, request);

        return ResponseEntity.ok()
                .body(updatedWish);
    }
}
