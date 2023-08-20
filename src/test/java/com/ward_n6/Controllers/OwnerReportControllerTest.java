package com.ward_n6.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.repository.OwnerReportRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.*;

import static net.bytebuddy.matcher.ElementMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OwnerReportController.class)
public class OwnerReportControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @MockBean
    OwnerReportRepository ownerReportRepository;
    // создаем несколько объектов тестируемого класса
    OwnerReport ownerReport1 = new OwnerReport(1L, LocalDateTime.now(), true, "Pedigree", "good", "Ok", 1L);
    OwnerReport ownerReport2 = new OwnerReport(2L, LocalDateTime.now(), true, "meat", "very good", "Ok", 2L);
    OwnerReport ownerReport3 = new OwnerReport(3L, LocalDateTime.now(), false, "fish", "normal", "Ok", 3L);

    @Test
    public void getOwnerReportByIdTest() throws Exception {
        //тест для метода getById()
        Mockito.when(ownerReportRepository.findById(ownerReport1.getId())).thenReturn(java.util.Optional.of(ownerReport1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/report/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.nutrition", Matchers.is("Pedigree")));
    }

    @Test
    public void getAllOwnerReportsTest() throws Exception {
        //тест для метода getAll()
        List ownerReports = new ArrayList<>(Arrays.asList(ownerReport1, ownerReport2, ownerReport3));

        Mockito.when(ownerReportRepository.findAll()).thenReturn(ownerReports);

        mockMvc.perform(MockMvcRequestBuilders.get("/report")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[1].nutrition", Matchers.is("meat")));
    }

    @Test
    public void givenOwnerReport_whenAdd_thenStatus201andOwnerReportReturned() throws Exception {
        //тест для метода addOwnerReport()
        Mockito.when(ownerReportRepository.save(Mockito.any())).thenReturn(ownerReport1);
            mockMvc.perform(MockMvcRequestBuilders
                                    .post("/report")
                                    .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(ownerReport1))
                    )
                    .andExpect(status().isCreated())
                    .andExpect(content().json(mapper.writeValueAsString(ownerReport1)));
        }

    @Test
    public void addOwnerReportTest() throws Exception {
        //тест для метода save()
        OwnerReport ownerReport = OwnerReport.builder()
                .reportDateTime(LocalDateTime.now())
                .havePhoto(true)
                .nutrition("Whiskas")
                .petsHealth("good")
                .petsBehavior("Ok")
                .petId(2L)
                .ownerId(3L)
                .build();

        Mockito.when(ownerReportRepository.save(ownerReport)).thenReturn(ownerReport);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/report")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(ownerReport));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.nutrition", Matchers.is("Whiskas")));
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

        Mockito.when(ownerReportRepository.findById(ownerReport1.getId())).thenReturn(Optional.of(ownerReport1));
        Mockito.when(ownerReportRepository.save(editedOwnerReport)).thenReturn(editedOwnerReport);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/report")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(editedOwnerReport));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.nutrition", Matchers.is("Whiskas")));
    }

    @Test
    public void deleteOwnerReportByIdTest() throws Exception{
        Mockito.when(ownerReportRepository.findById(ownerReport2.getId())).thenReturn(Optional.of(ownerReport2));

        mockMvc.perform(MockMvcRequestBuilders
                .delete(("/report/2"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenId_whenGetExistingOwnerReport_thenStatus200andOwnerReportReturned() throws Exception {
        Mockito.when(ownerReportRepository.findById(Mockito.any())).thenReturn(Optional.of(ownerReport3));
        mockMvc.perform(MockMvcRequestBuilders
                .get("/report/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("3"))
                .andExpect(jsonPath("$.nutrition").value("fish"));
    }
    @Test
    public void givenId_whenGetNotExistingOwnerReport_thenStatus404anExceptionThrown() throws Exception {
        Mockito.when(ownerReportRepository.findById(Mockito.any())).
                thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders
                .get("/report/3"))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult -> mvcResult.getResolvedException().getClass().equals(EntityNotFoundException.class));
    }
    @Test
    public void giveOwnerReport_whenUpdate_thenStatus200andUpdatedReturns() throws Exception {
        Mockito.when(ownerReportRepository.save(Mockito.any())).thenReturn(ownerReport1);
        Mockito.when(ownerReportRepository.findById(Mockito.any())).thenReturn(Optional.of(ownerReport1));
        mockMvc.perform(MockMvcRequestBuilders
                .post("/report/1")
                        .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(ownerReport1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Michail"));
    }
    @Test
    public void givenOwnerReport_whenDeleteOwnerReport_thenStatus200() throws Exception {
        Mockito.when(ownerReportRepository.findById(Mockito.any())).thenReturn(Optional.of(ownerReport2));
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/report/2"))
                .andExpect(status().isOk());
    }
    @Test
    public void givenOwnerReport_whenGetOwnerReport_thenStatus200() throws Exception {
        List ownerReports = new ArrayList<>(Arrays.asList(ownerReport1, ownerReport2, ownerReport3));

        Mockito.when(ownerReportRepository.findAll()).thenReturn(ownerReports);

        mockMvc.perform(MockMvcRequestBuilders.get("/report")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
//                .andExpect(jsonPath("$[1].nutrition", Matchers.is("meat")))
                .andExpect(content().json(mapper.writeValueAsString(ownerReports)));
    }
}
