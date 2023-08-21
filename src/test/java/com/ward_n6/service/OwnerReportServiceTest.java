package com.ward_n6.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.exception.RecordNotFoundException;
import com.ward_n6.repository.OwnerReportRepository;
import com.ward_n6.service.Impl.OwnerReportServiceImpl;
import javassist.NotFoundException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//@ExtendWith(MockitoExtension.class)
@WebMvcTest(OwnerReportService.class)
public class OwnerReportServiceTest {

        @Autowired
        MockMvc mockMvc;
        @Autowired
        ObjectMapper objectMapper;

        @MockBean
        OwnerReportRepository ownerReportRepository;
        // создаем несколько объектов тестируемого класса
//        OwnerReport ownerReport3 = new OwnerReport(3L, LocalDateTime.now(), false, "fish", "normal", "Ok", 3L);
@Autowired
        OwnerReportServiceImpl ownerReportService;
//        @Autowired
//        OwnerReportServiceImpl ownerReportService;

//        @Mock
//        private OwnerReportRepository ownerReportRepository;

//        @InjectMocks
//        private OwnerReportServiceImpl ownerReportService;
        @BeforeEach
        public void setup() {

        }

        @Test
        public void testGetOwnerReportsList() {
                OwnerReport ownerReport1 = new OwnerReport(1L, LocalDateTime.now(), true, "Pedigree", "good", "Ok", 1L);
                OwnerReport ownerReport2 = new OwnerReport(2L, LocalDateTime.now(), true, "meat", "very good", "Ok", 2L);
                when(ownerReportRepository.findAll()).thenReturn(Arrays.asList(ownerReport1, ownerReport2));
                List<OwnerReport> ownerReportList = ownerReportService.getAllOwnerReports();
                Assertions.assertEquals(ownerReportList.size(), 2);
                Assertions.assertEquals(ownerReportList.get(0).getPetsHealth(), "good");
                Assertions.assertEquals(ownerReportList.get(1).getPetsHealth(), "very good");
                Assertions.assertEquals(ownerReportList.get(0).getPetsBehavior(), "Ok");
                Assertions.assertEquals(ownerReportList.get(1).getNutrition(), "meat");
        }

        @Test
        public void testGetOwnerReportById() {
                OwnerReport ownerReport = new OwnerReport(7L, LocalDateTime.now(), false, "fish", "normal", "Ok", 3L);
                when(ownerReportRepository.findById(7L)).thenReturn(Optional.of(ownerReport));
                OwnerReport ownerReportById = ownerReportService.getOwnerReportById(7);
                Assertions.assertNotEquals(ownerReportById, null);
                Assertions.assertEquals(ownerReportById.getNutrition(), "fish");
                Assertions.assertEquals(ownerReportById.getId(), 7L);
        }


        @Test
        public void testGetInvalidOwnerReportById() {
                when(ownerReportRepository.findById(17L)).thenThrow(new RecordNotFoundException("OwnerReport Not Found with ID"));
                Exception exception = Assertions.assertThrows(RecordNotFoundException.class, () -> {
                        ownerReportService.getOwnerReportById(17);
                });
                Assertions.assertTrue(exception.getMessage().contains("OwnerReport Not Found with ID"));
        }

        @Test
        public void testAddOwnerReport() {
                OwnerReport ownerReport = new OwnerReport(12L, LocalDateTime.now(), true, "Pedigree", "good", "Ok", 12L);
                ownerReportService.addOwnerReport(ownerReport);
                verify(ownerReportRepository, times(1)).save(ownerReport);
                ArgumentCaptor<OwnerReport> ownerReportArgumentCaptor = ArgumentCaptor.forClass(OwnerReport.class);
                verify(ownerReportRepository).save(ownerReportArgumentCaptor.capture());
                OwnerReport ownerReportCreated = ownerReportArgumentCaptor.getValue();
                Assertions.assertNotNull(ownerReportCreated.getId());
                Assertions.assertEquals("good", ownerReportCreated.getPetsHealth());
        }

        @Test
        public void testDeleteOwnerReport() {
                OwnerReport ownerReport = new OwnerReport(13L, LocalDateTime.now(), true, "Pedigree", "good", "Ok", 13L);
                when(ownerReportRepository.findById(13L)).thenReturn(Optional.of(ownerReport));
                ownerReportService.deleteOwnerReportById(Math.toIntExact(ownerReport.getId()));
                verify(ownerReportRepository, times(1)).deleteById(ownerReport.getId());
                ArgumentCaptor<Long> ownerReportArgumentCaptor = ArgumentCaptor.forClass(Long.class);
                verify(ownerReportRepository).deleteById(ownerReportArgumentCaptor.capture());
                Long ownerReportIdDeleted = ownerReportArgumentCaptor.getValue();
                Assertions.assertNotNull(ownerReportIdDeleted);
                Assertions.assertEquals(13L, ownerReportIdDeleted);
        }

    }


