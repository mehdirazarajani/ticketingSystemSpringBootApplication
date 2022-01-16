package com.mehdi.ticketingSystem.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mehdi.ticketingSystem.ConstantManager;
import com.mehdi.ticketingSystem.model.CustomerType;
import com.mehdi.ticketingSystem.model.DeliveryDetails;
import com.mehdi.ticketingSystem.model.DeliveryDetailsList;
import com.mehdi.ticketingSystem.model.DeliveryStatus;
import com.mehdi.ticketingSystem.model.dtos.InsertDeliveryDetailsDTO;
import com.mehdi.ticketingSystem.model.dtos.LoginUserDTO;
import com.mehdi.ticketingSystem.model.response.TokenResponse;
import com.mehdi.ticketingSystem.model.response.common.SuccessResponse;
import com.mehdi.ticketingSystem.utils.ExtTimestamp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class DeliveryDetailsControllerTest {

    private Gson gson;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        gson = new Gson();
    }

    @Test
    public void test() throws Exception {
        long baseTimestamp = System.currentTimeMillis() + DeliveryDetails.MEAN_TIME_TO_PREPARE_FOOD + minutes(30);

        // login to get token
        LoginUserDTO loginUserDTO = new LoginUserDTO();
        loginUserDTO.setEmail("mehdi@gmail.com").setPassword("123456");
        String jsonRequest = gson.toJson(loginUserDTO);
        MvcResult result = mockMvc.perform(post("/api/users/login").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        Type type = new TypeToken<SuccessResponse<TokenResponse>>() {
        }.getType();

        SuccessResponse<TokenResponse> response = gson.fromJson(resultContent, type);
        assertTrue(response.isSuccess(), response.getMessage());

        String token = response.getData().getToken();
        assertNotNull(token);

        // add multiple records
        DeliveryDetailsList deliveryDetailsList = new DeliveryDetailsList(new ArrayList<>(List.of(
                new DeliveryDetails(1, CustomerType.NEW, DeliveryStatus.ORDER_PREPARING, new ExtTimestamp(baseTimestamp + minutes(30)), 100, new ExtTimestamp(baseTimestamp + minutes(30))),
                new DeliveryDetails(2, CustomerType.LOYAL, DeliveryStatus.ORDER_PREPARING, new ExtTimestamp(baseTimestamp + minutes(10)), 100, new ExtTimestamp(baseTimestamp + minutes(50))),
                new DeliveryDetails(3, CustomerType.VIP, DeliveryStatus.ORDER_RECEIVED, new ExtTimestamp(baseTimestamp + minutes(10)), 100, new ExtTimestamp(baseTimestamp + minutes(50))),
                new DeliveryDetails(4, CustomerType.NEW, DeliveryStatus.ORDER_RECEIVED, new ExtTimestamp(baseTimestamp + minutes(100)), 100, new ExtTimestamp(baseTimestamp + minutes(30))),
                new DeliveryDetails(5, CustomerType.LOYAL, DeliveryStatus.ORDER_RECEIVED, new ExtTimestamp(baseTimestamp - minutes(100)), 100, new ExtTimestamp(baseTimestamp + minutes(30))),
                new DeliveryDetails(6, CustomerType.VIP, DeliveryStatus.ORDER_RECEIVED, new ExtTimestamp(baseTimestamp - minutes(100)), 100, new ExtTimestamp(baseTimestamp + minutes(30))),
                new DeliveryDetails(7, CustomerType.VIP, DeliveryStatus.ORDER_RECEIVED, new ExtTimestamp(baseTimestamp - minutes(100)), 100, new ExtTimestamp(baseTimestamp + minutes(30))),
                new DeliveryDetails(8, CustomerType.LOYAL, DeliveryStatus.ORDER_RECEIVED, new ExtTimestamp(baseTimestamp + minutes(100)), 100, new ExtTimestamp(baseTimestamp + minutes(50))),
                new DeliveryDetails(9, CustomerType.LOYAL, DeliveryStatus.ORDER_RECEIVED, new ExtTimestamp(baseTimestamp + minutes(100)), 100, new ExtTimestamp(baseTimestamp + minutes(50))),
                new DeliveryDetails(10, CustomerType.LOYAL, DeliveryStatus.ORDER_PREPARING, new ExtTimestamp(baseTimestamp + minutes(1000)), 100, new ExtTimestamp(baseTimestamp + minutes(60))),
                new DeliveryDetails(11, CustomerType.VIP, DeliveryStatus.ORDER_DELIVERED, new ExtTimestamp(baseTimestamp + minutes(1000)), 100, new ExtTimestamp(baseTimestamp + minutes(60))),
                new DeliveryDetails(12, CustomerType.LOYAL, DeliveryStatus.ORDER_DELIVERED, new ExtTimestamp(baseTimestamp + minutes(1000)), 100, new ExtTimestamp(baseTimestamp + minutes(60))),
                new DeliveryDetails(13, CustomerType.VIP, DeliveryStatus.ORDER_PICKED, new ExtTimestamp(baseTimestamp + minutes(1000)), 100, new ExtTimestamp(baseTimestamp + minutes(60))),
                new DeliveryDetails(14, CustomerType.LOYAL, DeliveryStatus.ORDER_PICKED, new ExtTimestamp(baseTimestamp + minutes(1000)), 100, new ExtTimestamp(baseTimestamp + minutes(60)))
        )));
        for (DeliveryDetails d : deliveryDetailsList) {
            InsertDeliveryDetailsDTO dto = new InsertDeliveryDetailsDTO();
            dto.setDeliveryId(d.getDeliveryId()).setCustomerType(d.getCustomerType()).setDeliveryStatus(d.getDeliveryStatus()).
                    setExpectedDeliveryTime(d.estimatedTimeOfDelivery().getTime()).setCurrentDistanceFromDestinationInMeters(d.getCurrentDistanceFromDestinationInMeters()).
                    setTimeToReachDestination(d.getTimeToReachDestination().getTime());
            jsonRequest = gson.toJson(dto);
            mockMvc.perform(post("/api/delivery_details/insert")
                            .header(ConstantManager.getInstance().AUTH_KEY, token)
                            .content(jsonRequest).contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isOk());
        }

        // get the values
        result = mockMvc.perform(get("/api/delivery_details/uncompleted_details_in_priority")
                .header(ConstantManager.getInstance().AUTH_KEY, token)
                .content(jsonRequest).contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        resultContent = result.getResponse().getContentAsString();
        type = new TypeToken<SuccessResponse<List<Map<String, Object>>>>() {
        }.getType();

        SuccessResponse<List<Map<String, Object>>> response1 = gson.fromJson(resultContent, type);
        assertTrue(response1.isSuccess(), response1.getMessage());

        List<Map<String, Object>> list = response1.getData();
        assertEquals(list.size(), 10); // not 14 because 4 are completed orders

        List<Integer> expectedList = List.of(6, 7, 1, 4, 5, 3, 2, 8, 9, 10);
        List<Integer> actualList = new ArrayList<>();
        for (Map<String, Object> map : list){
            actualList.add((int) Math.round((Double) map.get("deliveryId")));
        }

        assertThat(expectedList).isEqualTo(actualList);

        // clear the table for next run (no conflicts in UT)
        mockMvc.perform(delete("/api/delivery_details/delete_all")
                .header(ConstantManager.getInstance().AUTH_KEY, token)
                .content("{}").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }

    private long minutes(int min) {
        return (long) min * 1000 * 60;
    }


}