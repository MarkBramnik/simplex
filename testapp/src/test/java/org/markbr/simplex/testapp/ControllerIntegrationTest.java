package org.markbr.simplex.testapp;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.markbr.simplex.testapp.web.SampleController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SampleController.class)
public class ControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;


    @Test
    @DisplayName("Calling controller with parameters: value = 10, min = 5, max = 20 doesn't produce exception")
    public void test_no_exception_thrown_when_value_in_range() throws Exception {
        mockMvc.perform(get("/")
                        .param("value", "10")
                        .param("min", "5")
                        .param("max", "20")

        ).andExpect(status().isOk())
         .andExpect(MockMvcResultMatchers.content().string("10 within the range 5,20"));
    }

    @Test
    @DisplayName("Calling controller with parameters: value = 50, min = 5, max = 20 throws out of range exception")
    public void test_exception_thrown_when_value_is_out_of_range() throws Exception {
        mockMvc.perform(get("/")
                .param("value", "50")
                .param("min", "5")
                .param("max", "20")

        ).andExpect(status().isBadRequest())
         .andExpect(jsonPath("$.errorCode").value(1003))
         .andExpect(jsonPath("$.rawMessage").value("The value {{value:Integer}} is out of range: [ {{minValue:Integer}}, {{maxValue:Integer}} ]"))
         .andExpect(jsonPath("$.message").value("The value 50 is out of range: [ 5, 20 ]"))
         .andExpect(jsonPath("$.parameters.minValue").value(5))
         .andExpect(jsonPath("$.parameters.maxValue").value(20))
         .andExpect(jsonPath("$.parameters.value").value(50));


    }
}
