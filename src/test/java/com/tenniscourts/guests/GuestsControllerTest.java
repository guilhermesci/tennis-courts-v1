package com.tenniscourts.guests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import static com.tenniscourts.utils.GuestUtils.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class GuestsControllerTest {

    public static final String GUEST_API_URL_PATH = "/api/v2/guests";
    private MockMvc mockMvc;

    @Mock
    private GuestService guestService;

    @InjectMocks
    private GuestController guestController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(guestController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers(((viewName, locale) -> new MappingJackson2JsonView()))
                .build();
    }

    @Test
    void testWhenPostIsCalledThenAGuestShouldBeCreated() throws Exception {
        GuestDTO guestDTO = createFakeGuestDTO();

        when(guestService.addGuest(guestDTO)).thenReturn(guestDTO);

        mockMvc.perform(post(GUEST_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(guestDTO)))
                .andExpect(status().isCreated());
    }
}
