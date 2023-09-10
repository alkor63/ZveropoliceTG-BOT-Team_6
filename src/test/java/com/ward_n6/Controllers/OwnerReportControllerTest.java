package com.ward_n6.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ward_n6.entity.reports.OwnerReport;

import com.ward_n6.service.OwnerReportService;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OwnerReportController.class)
public class OwnerReportControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @MockBean
    OwnerReportService ownerReportService;
    // создаем несколько объектов тестируемого класса
    OwnerReport ownerReport1 = new OwnerReport(1,1,1, LocalDateTime.now(), true,
            "Pedigree", "good", "Ok");
    OwnerReport ownerReport2 = new OwnerReport(2,2,2, LocalDateTime.now(), true,
            "meat", "very good", "Ok");
    OwnerReport ownerReport3 = new OwnerReport(3,3,3, LocalDateTime.now(), false,
            "fish", "normal", "Ok");

    @Test
    public void getOwnerReportByIdTest() throws Exception {
        //тест для метода getById()
        Mockito.when(ownerReportService.getOwnerReportById(1)).thenReturn(ownerReport1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/report/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.nutrition", Matchers.is("Pedigree")));
    }
    @Test
    public void testGetOwnerReportById() {
        OwnerReport ownerReport = new OwnerReport(7L,8L,9L, LocalDateTime.now(), false, "fish", "normal", "Ok");
        when(ownerReportService.getOwnerReportById(7)).thenReturn(ownerReport);
        OwnerReport ownerReportById = ownerReportService.getOwnerReportById(7);
        Assertions.assertNotEquals(ownerReportById, null);
        Assertions.assertEquals(ownerReportById.getNutrition(), "fish");
        Assertions.assertEquals(ownerReportById.getId(), 7L);
    }
    @Test
    public void getAllOwnerReportsTest() throws Exception {
        //тест для метода getAll()
        List ownerReports = new ArrayList<>(Arrays.asList(ownerReport1, ownerReport2, ownerReport3));

        Mockito.when(ownerReportService.getAllOwnerReports()).thenReturn(ownerReports);

        mockMvc.perform(MockMvcRequestBuilders.get("/report")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[1].nutrition", Matchers.is("meat")));
    }

    @Test
    public void addOwnerReportTest() throws Exception {
        //тест для метода save()


        Mockito.when(ownerReportService.addOwnerReport(ownerReport3)).thenReturn(ownerReport3);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/report")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(ownerReport3));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.nutrition", Matchers.is("fish")));
    }

    @Test
    public void editOwnerReportByIdTest() throws Exception {
        //тест для метода editById()
        OwnerReport editedOwnerReport = OwnerReport.builder()
                .id(1L)
                .reportDateTime(LocalDateTime.now())
                .havePhoto(true)
                .nutrition("Whiskas")
                .petsHealth("good")
                .petsBehavior("Ok")
                .petId(1L)
                .ownerId(3L)
                .build();

        Mockito.when(ownerReportService.getOwnerReportById(1)).thenReturn(ownerReport1);
        Mockito.when(ownerReportService.addOwnerReport(editedOwnerReport)).thenReturn(editedOwnerReport);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/report")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(editedOwnerReport));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.nutrition", Matchers.is("Whiskas")));
    }


    @Test
    public void deleteOwnerReportByIdTest() throws Exception{
        Mockito.when(ownerReportService.deleteOwnerReportById(2)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders
                .delete(("/report/2"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteOwnerReportByIdTestWhenReturnFalse()throws Exception{
        Mockito.when(ownerReportService.deleteOwnerReportById(5)).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(("/report/5"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
