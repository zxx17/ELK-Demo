package org.zxx17.elk.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Xinxuan Zhuo
 * @version 2024/5/16
 * <p>
 *
 * </p>
 */

@RestController
@RequestMapping("/api/v1/test-elk")
public class TestController {

    @GetMapping
    public String test() {
        return "test-elk";
    }

}
