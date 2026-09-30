package com.example.mybackend.controller;

import com.example.mybackend.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "测试接口", description = "基础连通性测试")
@RestController
@RequestMapping("/api/test")
public class TestController {

    @Operation(summary = "健康检查")
    @GetMapping("/ping")
    public Result<String> ping() {
        return Result.success("pong");
    }

    @Operation(summary = "带参数的测试")
    @GetMapping("/hello")
    public Result<String> hello(@RequestParam(defaultValue = "world") String name) {
        return Result.success("Hello, " + name + "!");
    }
}
