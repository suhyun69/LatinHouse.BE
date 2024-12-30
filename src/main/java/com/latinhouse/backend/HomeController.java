package com.latinhouse.backend;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
@Tag(name = "Home", description = "Home Controller Document")
public class HomeController {

    @GetMapping("/hello")
    @Operation(summary = "인사하기", description = "Hello, Starnager.")
    String hello() {
        return "Hello, Stranger.";
    }
}