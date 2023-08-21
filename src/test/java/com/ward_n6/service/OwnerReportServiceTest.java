package com.ward_n6.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.repository.OwnerReportRepository;
import com.ward_n6.service.Impl.OwnerReportServiceImpl;
import javassist.NotFoundException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
        ObjectMapper mapper;

//        @MockBean
//        OwnerReportRepository ownerReportRepository;
        // создаем несколько объектов тестируемого класса
        OwnerReport ownerReport1 = new OwnerReport(1L, LocalDateTime.now(), true, "Pedigree", "good", "Ok", 1L);
        OwnerReport ownerReport2 = new OwnerReport(2L, LocalDateTime.now(), true, "meat", "very good", "Ok", 2L);
        OwnerReport ownerReport3 = new OwnerReport(3L, LocalDateTime.now(), false, "fish", "normal", "Ok", 3L);



        @Mock
        private OwnerReportRepository ownerReportRepository;

        @InjectMocks
        private OwnerReportServiceImpl ownerReportService;

//        private OwnerReport ownerReport;


        // JUnit test for addOwnerReport method
//        @DisplayName("JUnit test for addOwnerReport method")
//        @Test
//        public void givenOwnerReportObject_whenSaveOwnerReport_thenReturnOwnerReportObject(){
//            // given - precondition or setup
//            given(ownerReportRepository.findById(ownerReport1.getId()))
//                    .willReturn(Optional.empty());
//
//            given(ownerReportRepository.save(ownerReport1)).willReturn(ownerReport1);
//
//            // when -  action or the behaviour that we are going test
//            OwnerReport savedOwnerReport = ownerReportService.addOwnerReport(ownerReport1);
//
//            System.out.println(savedOwnerReport);
//            // then - verify the output
//            assertThat(savedOwnerReport).isNotNull();
//        }
//
//        // JUnit test for saveOwnerReport method
//        @DisplayName("JUnit test for saveOwnerReport method which throws exception")
//        @Test
//        public void givenExistingEmail_whenSaveOwnerReport_thenThrowsException(){
//            // given - precondition or setup
//            given(ownerReportRepository.findById(ownerReport1.getId()))
//                    .willReturn(Optional.of(ownerReport1));
//
//            System.out.println(ownerReportRepository);
//            System.out.println(ownerReportService);
//
//            // when -  action or the behaviour that we are going test
//            org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class, () -> {
//                ownerReportService.addOwnerReport(ownerReport1);
//            });
//
//            // then
//            verify(ownerReportRepository, never()).save(any(OwnerReport.class));
//        }
//
//        // JUnit test for getAllOwnerReports method
//        @DisplayName("JUnit test for getAllOwnerReports method")
//        @Test
//        public void givenOwnerReportsList_whenGetAllOwnerReports_thenReturnOwnerReportsList(){
//            // given - precondition or setup
//
//            given(ownerReportRepository.findAll()).willReturn(List.of(ownerReport1,ownerReport2,ownerReport3));
//
//            // when -  action or the behaviour that we are going test
//            List<OwnerReport> ownerReportList = ownerReportService.getAllOwnerReports();
//
//            // then - verify the output
//            assertThat(ownerReportList).isNotNull();
//            assertThat(ownerReportList.size()).isEqualTo(3);
//        }
//
//        // JUnit test for getAllOwnerReports method
//        @DisplayName("JUnit test for getAllOwnerReports method (negative scenario)")
//        @Test
//        public void givenEmptyOwnerReportsList_whenGetAllOwnerReports_thenReturnEmptyOwnerReportsList(){
//            // given - precondition or setup
//
//            given(ownerReportRepository.findAll()).willReturn(Collections.emptyList());
//
//            // when -  action or the behaviour that we are going test
//            List<OwnerReport> ownerReportList = ownerReportService.getAllOwnerReports();
//
//            // then - verify the output
//            assertThat(ownerReportList).isEmpty();
//            assertThat(ownerReportList.size()).isEqualTo(0);
//        }
//
//        // JUnit test for getOwnerReportById method
//        @DisplayName("JUnit test for getOwnerReportById method")
//        @Test
//        public void givenOwnerReportId_whenGetOwnerReportById_thenReturnOwnerReportObject(){
//            // given
//            given(ownerReportRepository.findById(1L)).willReturn(Optional.of(ownerReport1));
//
//            // when
//            OwnerReport savedOwnerReport = ownerReportService.getOwnerReportById(Math.toIntExact(ownerReport1.getId()));
//
//            // then
//            assertThat(savedOwnerReport).isNotNull();
//
//        }
//
//        // JUnit test for updateOwnerReport method
//        @DisplayName("JUnit test for updateOwnerReport method")
//        @Test
//        public void givenOwnerReportObject_whenUpdateOwnerReport_thenReturnUpdatedOwnerReport(){
//            // given - precondition or setup
//            given(ownerReportRepository.save(ownerReport2)).willReturn(ownerReport2);
//            ownerReport2.setPetsHealth("Ok");
//            ownerReport2.setNutrition("ChtoDadut");
//            // when -  action or the behaviour that we are going test
//            OwnerReport updatedOwnerReport = ownerReportService.editOwnerReportById(2, ownerReport2);
//
//            // then - verify the output
//            assertThat(updatedOwnerReport.getPetsHealth()).isEqualTo("Ok");
//            assertThat(updatedOwnerReport.getNutrition()).isEqualTo("ChtoDadut");
//        }
//
//        // JUnit test for deleteOwnerReport method
//        @DisplayName("JUnit test for deleteOwnerReport method")
//        @Test
//        public void givenOwnerReportId_whenDeleteOwnerReport_thenNothing(){
//            // given - precondition or setup
//            long ownerReportId = 1L;
//
//            willDoNothing().given(ownerReportRepository).deleteById(ownerReportId);
//
//            // when -  action or the behaviour that we are going test
//            ownerReportService.deleteOwnerReportById(1);
//
//            // then - verify the output
//            verify(ownerReportRepository, times(1)).deleteById(ownerReportId);
//        }
    }


