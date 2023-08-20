package com.ward_n6.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ward_n6.Controllers.OwnerReportController;
import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.enums.PetsType;
import com.ward_n6.repository.OwnerReportRepository;
import org.hamcrest.Matchers;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OwnerReportController.class)
public class OwnerReportControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @MockBean
    OwnerReportRepository ownerReportRepository;
    // создаем несколько объектов тестируемого класса
    OwnerReport ownerReport1 = new OwnerReport(1l, 9932688l, LocalDateTime.now(), PetsType.CAT, false,
            "fff","dddd", "dse", 23L);
    OwnerReport ownerReport2 = new OwnerReport(2l, 1236688l, LocalDateTime.now(), PetsType.CAT, false,
            "f[pf","diudd", ";ose", 21L);
    OwnerReport ownerReport3 = new OwnerReport(3l, 1236458l, LocalDateTime.now(), PetsType.DOG, false,
            "efWQ","dduiyu", "Fg", 27L);

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

//    @Test
//    public void shouldReturnExceptionWhenReportOrIdIsNull() throws Exception {
//// проверяем бросание исключений при ownerReport or id = null
//        OwnerReport nullOwnerReport = new OwnerReport();
//        OwnerReport editedOwnerReport = OwnerReport.builder()
                //пропустили id
//                .reportDateTime(LocalDateTime.now())
//                .havePhoto(true)
//                .nutrition("Whiskas")
//                .petsHealth("good")
//                .petsBehavior("Ok")
//                .petId(1L)
//                .id(3L)
//                .build();

//        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/report")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(this.mapper.writeValueAsString(nullOwnerReport));
//
//        mockMvc.perform(mockRequest)
//                .andExpect(status().isBadRequest())
//                .andExpect(result ->
//                        assertTrue(result.getResolvedException() instanceof NullPointerException))
//                .andExpect(result ->
//                        assertEquals(("OwnerRecord или Id не должны быть null"), result.getResolvedException().getMessage()));
//    }

//    @Test
//    public void shouldReturnExceptionWhenReportNotFound() throws Exception {
// проверяем бросание исключений если запись ownerReport не обнаружена
        OwnerReport nullOwnerReport = new OwnerReport();
//        OwnerReport editedOwnerReport = OwnerReport.builder()
//                .id(321L)
//                .reportDateTime(LocalDateTime.now())
//                .havePhoto(true)
//                .nutrition("Whiskas")
//                .petsHealth("good")
//                .petsBehavior("Ok")
//                .petId(1L)
//                .id(3L)
//                .build();

//        Mockito.when(ownerReportRepository.findById(nullOwnerReport.getId())).thenReturn(null);
//
//        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/report")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(this.mapper.writeValueAsString(nullOwnerReport));
//
//        mockMvc.perform(mockRequest)
//                .andExpect(status().isBadRequest())
//                .andExpect(result ->
//                        assertTrue(result.getResolvedException() instanceof NotFoundException))
//                .andExpect(result ->
//                        assertEquals(("Записи OwnerRecord с Id = 321 не существует"), result.getResolvedException().getMessage()));
//    }

    @Test
    public void deleteOwnerReportByIdTest() throws Exception{
        Mockito.when(ownerReportRepository.findById(ownerReport2.getId())).thenReturn(Optional.of(ownerReport2));

        mockMvc.perform(MockMvcRequestBuilders
                .delete(("/report/2"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

//    @Test
//    public void deleteOwnerReportByIdTestWhenNotFoundException()throws Exception{
//        Mockito.when((ownerReportRepository.findById(5L))).thenReturn(null);
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .delete(("/report/2"))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest())
//                .andExpect(result ->
//                        assertTrue(result.getResolvedException() instanceof NotFoundException))
//                .andExpect(result -> assertEquals("В базе нет отчёта с id = 5 ",
//                        result.getResolvedException().getMessage()));
//    }
}
