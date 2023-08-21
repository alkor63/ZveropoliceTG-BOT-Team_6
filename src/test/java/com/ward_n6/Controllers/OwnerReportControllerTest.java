package com.ward_n6.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.repository.OwnerReportRepository;
import com.ward_n6.service.OwnerReportService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;



// @ExtendWith(SpringExtension.class)
@WebMvcTest(OwnerReportController.class)
public class OwnerReportControllerTest {
    
        @Autowired
        private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
        @MockBean
        private OwnerReportService ownerReportService;

        private OwnerReport ownerReport;

        @BeforeEach
        public void setup() {
            ownerReport = new OwnerReport(10L, LocalDateTime.now(), true, "Pedigree", "good", "Ok", 1L);
        }

        @Test
        public void testGetAllOwnerReportsList() throws Exception {
            Mockito.when(ownerReportService.getAllOwnerReports()).thenReturn(Collections.singletonList(ownerReport));
            mockMvc.perform(MockMvcRequestBuilders
                            .get("/report"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$", hasSize(1)))
                    .andExpect(jsonPath("$").isArray());
        }
    //            Mockito.when(ownerReportService.getOwnerReportById(1)).
//                    thenReturn(ownerReport1);
//            mockMvc.perform(MockMvcRequestBuilders
//                            .get("/report/1")
//                            .contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(status().isOk())
//                    .andExpect(jsonPath("$", notNullValue()));
        @Test
        public void testGetOwnerReportById() throws Exception {
            Mockito.when(ownerReportService.getOwnerReportById(10)).thenReturn(ownerReport);
            mockMvc.perform(MockMvcRequestBuilders
                            .get("/report/10"))
//                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.nutrition", Matchers.is("Pedigree")))
                    .andExpect(jsonPath("$.id", Matchers.is(10)))
                    .andExpect(jsonPath("$").isNotEmpty());
        }

        @Test
        public void testAddOwnerReport() throws Exception {
            //тест для метода save()
            OwnerReport ownerReport2 = OwnerReport.builder()
                    .id(20L)
                    .reportDateTime(LocalDateTime.now())
                    .havePhoto(true)
                    .nutrition("Whiskas")
                    .petsHealth("good")
                    .petsBehavior("Ok")
                    .petId(2L)
                    .ownerId(3L)
                    .build();
            Mockito.when(ownerReportService.addOwnerReport(ownerReport2)).thenReturn(ownerReport2);
            mockMvc.perform(MockMvcRequestBuilders.
                            post("/report")
                                    .content(this.objectMapper.writeValueAsString(ownerReport2))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
//                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.petsHealth", Matchers.is("good")))
                    .andExpect(jsonPath("$.id", Matchers.is(20)))
                    .andExpect(jsonPath("$").isNotEmpty());
        }


        @Test
        public void testDeleteOwnerReportById() throws Exception {
            Mockito.when(ownerReportService.deleteOwnerReportById(Math.toIntExact(ownerReport.getId()))).thenReturn(true);
            mockMvc.perform(MockMvcRequestBuilders
                            .delete("/report/" + ownerReport.getId()))
//                    .andDo(print())
                    .andExpect(status().isOk());
        }
//        @Test
//        public void editOwnerReportByIdTest() throws Exception {
//            //тест для метода editById()
//            OwnerReport editedOwnerReport = OwnerReport.builder()
//                    .id(10L)
//                    .reportDateTime(LocalDateTime.now())
//                    .havePhoto(true)
//                    .nutrition("Whiskas")
//                    .petsHealth("good")
//                    .petsBehavior("Ok")
//                    .petId(1L)
//                    .ownerId(3L)
//                    .build();
//
//            Mockito.when(ownerReportService.getOwnerReportById(Math.toIntExact(ownerReport.getId()))).
//                    thenReturn(ownerReport);
//            Mockito.when(ownerReportService.addOwnerReport(editedOwnerReport)).thenReturn(editedOwnerReport);
//
//            MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/report")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .accept(MediaType.APPLICATION_JSON)
//                    .content(this.objectMapper.writeValueAsString(editedOwnerReport));
//
//            mockMvc.perform(mockRequest)
//                    .andExpect(status().isOk())
//                    .andExpect(jsonPath("$", notNullValue()))
//                    .andExpect(jsonPath("$.nutrition", Matchers.is("Whiskas")));
//        }
    }
//    =================================================================================================================



