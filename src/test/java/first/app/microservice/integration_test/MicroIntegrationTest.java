package first.app.microservice.integration_test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jdi.InternalException;
import first.app.microservice.mapstruct.Entities.OfficeDto;
import first.app.microservice.model.Office;
import first.app.microservice.repository.OfficeRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.*;

import static first.app.microservice.enums.Shops.ABAN_ESHOP;
import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.*;

import org.springframework.test.web.servlet.ResultMatcher;



import java.util.ArrayList;
import java.util.List;


import static first.app.microservice.enums.Shops.HUAWEI;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MicroIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OfficeRepository officeRepository;




    @Test
    public void test_GetMethod() throws Exception {
        Office office = new Office();office.setId(null);office.setName("MohammadReza");office.setProvider(HUAWEI);
        office.setCode("6104337598632220");
        office.setInactive(true);

        List<Office> offices = new ArrayList<>();
        offices.add(office);

        Mockito.when(officeRepository.findAll()).thenReturn(offices);
        mockMvc.perform(get("/office")
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect((ResultMatcher) content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", Matchers.equalTo("MohammadReza")));

        verify(officeRepository, times(1)).findAll();
    }


    @Test
    public void find_Office_By_Id() throws Exception {

        Office office = new Office();office.setId(1L);office.setName("MohammadReza");office.setProvider(HUAWEI);
        office.setCode("6104337598632220");
        office.setInactive(true);

        when(officeRepository.save(office)).thenReturn(office);

        String Json = objectMapper.writeValueAsString(office);


        Mockito.when(officeRepository.findById(1L)).thenReturn(java.util.Optional.of(office));
        mockMvc.perform(get("/office/1")
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect((ResultMatcher) content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", Matchers.equalTo("MohammadReza")));

        verify(officeRepository, times(1)).findById(1L);

    }

    @Test
    public void find_OfficeById_InternalServerError_500() throws Exception {
        Office office = new Office();office.setId(1L);office.setName("MohammadReza");office.setProvider(HUAWEI);
        office.setCode("6104337598632220");
        office.setInactive(true);

        when(officeRepository.save(office)).thenReturn(office);

        String Json = objectMapper.writeValueAsString(office);


        Mockito.when(officeRepository.findById(1L)).thenReturn(java.util.Optional.of(office));
        mockMvc.perform(get("/office/2")
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isInternalServerError());
//                .andExpect((ResultMatcher) content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.name", Matchers.equalTo("MohammadReza")));

    }

    @Test
    public void find_OfficeByIdNotFound_404() throws Exception {
        Office office = new Office(); office.setId(1L); office.setName("MohammadReza");office.setCode("6104337598632220");
        office.setProvider(HUAWEI); office.setInactive(true);


        mockMvc.perform(get("/book"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void save_Office() throws Exception {
//        Office office = new Office(); office.setId(1L); office.setName("MohammadReza"); office.setCode("6104337598632220");
//        office.setProvider(ABAN_ESHOP);
        OfficeDto officeDto = new OfficeDto();officeDto.setId(1L);officeDto.setName("MohammadReza");officeDto.setCode("6104337598632220");
        officeDto.setProvider("aban-eshop");officeDto.setInactive(true);

        //when(officeRepository.save(office)).thenReturn(office);

        String Json = objectMapper.writeValueAsString(officeDto);

        mockMvc.perform(post("/office")
                        .content(Json)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.id", is(1)))
                        .andExpect(jsonPath("$.name", is("MohammadReza")));

        //verify(officeRepository, times(1)).save(office);
    }

    @Test
    public void save_Empty_Office() throws Exception {

        OfficeDto officeDto = new OfficeDto();

        String Json = objectMapper.writeValueAsString(officeDto);

        mockMvc.perform(post("/office")
                        .content(Json)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());


    }

    @Test
    public void update_By_Id_Using_PutMethod() throws Exception {
        Office office =new Office();office.setId(1L);office.setName("MohammadReza");office.setCode("6104337598632220");
        office.setProvider(ABAN_ESHOP); office.setInactive(true);

        when(officeRepository.save(office)).thenReturn(office);

        String Json = objectMapper.writeValueAsString(office);


        mockMvc.perform(put("/office/1")
                        .content(Json)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id", is(1)))
                        .andExpect(jsonPath("$.name", is("MohammadReza")))
                        .andExpect(jsonPath("$.code", is("6104337598632220")));

    }

    @Test
    public void delete_By_Id_Using_DeleteMethod() throws Exception {

        Office office =new Office();office.setId(1L);office.setName("MohammadReza");office.setCode("6104337598632220");
        office.setProvider(ABAN_ESHOP); office.setInactive(true);

        doNothing().when(officeRepository).deleteById(1L);

        String Json = objectMapper.writeValueAsString(office);

        mockMvc.perform(delete("/office/1"))
                .andExpect(status().isOk());

        verify(officeRepository, times(1)).deleteById(1L);
    }

    @Test
    public void delete_By_Id_InternalServerError_Status500() throws Exception {
        doNothing().when(officeRepository).deleteById(1L);

        mockMvc.perform(delete("/office/1"))
                .andExpect(status().isOk());

        verify(officeRepository, times(1)).deleteById(1L);
    }

    @Test
    public void methodNotAllowed_Error_405() throws Exception {
        OfficeDto officeDto = new OfficeDto();officeDto.setId(null);officeDto.setName("MohammadReza");officeDto.setCode("6104337598632220");
        officeDto.setProvider("aban-eshop");officeDto.setInactive(true);

        String Json = objectMapper.writeValueAsString(officeDto);

        mockMvc.perform(patch("/office"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void isBadRequest_Error_400() throws Exception {
        String Json = "{ \"id\": 1,\"name\": \"MohammadReza\",]\"code\": \"6104337598632220\",\"provider\": \"ABAN_ESHOP\",\"inactive\": true}";

        mockMvc.perform(post("/office")
                        .content(Json)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}



