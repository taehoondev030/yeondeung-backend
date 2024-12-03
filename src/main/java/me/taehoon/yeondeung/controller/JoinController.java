package me.taehoon.yeondeung.controller;

import me.taehoon.yeondeung.dto.JoinDTO;
import me.taehoon.yeondeung.repository.UserRepository;
import me.taehoon.yeondeung.service.JoinService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
public class JoinController {

    private final JoinService joinService;
    private final UserRepository userRepository;

    public JoinController(JoinService joinService, UserRepository userRepository) {
        this.joinService = joinService;
        this.userRepository = userRepository;
    }

    @PostMapping("/join")
    public String joinProcess(JoinDTO joinDTO) {

        joinService.joinProcess(joinDTO);
        return "successful!";
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody JoinDTO joinDTO) {
        try {
            joinService.joinProcess(joinDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());  // 예외 메시지 반환
        }
    }

    @GetMapping("/check")
    public ResponseEntity<?> checkUsername(@RequestParam String username) {
        if (userRepository.existsByUsername(username)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is already taken.");
        } else {
            return ResponseEntity.ok("Username is available!");
        }
    }
}
