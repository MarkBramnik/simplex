package org.markbr.simplex.testapp.web;

import org.markbr.simplex.testapp.exceptions.OutOfRangeException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/")
public class SampleController {

    @GetMapping
    public String checkRange(@RequestParam("value") int value, @RequestParam("min") int min, @RequestParam int max) {
        if(value >= min && value <= max) {
            return value + " within the range " + min + "," + max;
        }
        else {
            throw new OutOfRangeException(value, min, max);
        }
    }
}
