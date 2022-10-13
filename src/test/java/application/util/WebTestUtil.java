package application.util;

import application.dto.HentBogRequestDto;
import application.dto.HentReviewRequestDto;
import application.dto.OpretBogRequestDto;
import application.dto.OpretReviewRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class WebTestUtil {

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultActions kaldOpretBogEndpoint(MockMvc mockMvc, OpretBogRequestDto requestDto) throws Exception {
        return mockMvc.perform(post("/bog/opret")
                .contentType("application/json")
                .content(asJsonString(requestDto)));
    }

    public static ResultActions kaldHentBogEndpoint(MockMvc mockMvc, HentBogRequestDto requestDto) throws Exception {
        return mockMvc.perform(get("/bog/hent")
                .contentType("application/json")
                .content(asJsonString(requestDto)));
    }

    public static ResultActions kaldOpretReviewEndpoint(MockMvc mockMvc, OpretReviewRequestDto requestDto) throws Exception {
        return mockMvc.perform(post("/review/opret")
                .contentType("application/json")
                .content(asJsonString(requestDto)));
    }

    public static ResultActions kaldHentReviewEndpoint(MockMvc mockMvc, HentReviewRequestDto requestDto) throws Exception {
        return mockMvc.perform(get("/review/hent")
                .contentType("application/json")
                .content(asJsonString(requestDto)));
    }
}
