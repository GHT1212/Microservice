package first.app.microservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import first.app.microservice.controller.OfficeController;
import first.app.microservice.enums.Shops;
import first.app.microservice.mapstruct.Entities.OfficeDto;
import first.app.microservice.model.Office;
import first.app.microservice.repository.OfficeRepository;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static first.app.microservice.enums.Shops.ABAN_ESHOP;
import static first.app.microservice.enums.Shops.HUAWEI;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // For restTemplate
@ActiveProfiles("test")
class MicroserviceApplicationTests {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private OfficeRepository officeRepository;

    @Autowired
    private OfficeController officeController;


    @Test
    void contextLoads() {
        assertThat(officeController).isNotNull();
    }


    @Test
    public void find_All_Offices() throws JsonProcessingException, JSONException {
        Office office = new Office();
        office.setId(1L);
        office.setName("MohammadReza");
        office.setCode("6104337598632220");
        office.setProvider(ABAN_ESHOP);
        office.setInactive(true);
        Office office1 = new Office();
        office1.setId(2L);
        office1.setName("Shamsollah");
        office1.setCode("6037997387779797");
        office1.setProvider(HUAWEI);
        office1.setInactive(true);

        List<Office> offices = new ArrayList<>();
        offices.add(office);
        offices.add(office1);

        when(officeRepository.findAll()).thenReturn(offices);
        String expected = om.writeValueAsString(offices);

        ResponseEntity<String> response = restTemplate.getForEntity("/office", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);

        verify(officeRepository, times(1)).findAll();
    }

    @Test
    public void find_OfficeById() throws JsonProcessingException, JSONException {
        Office office = new Office();
        office.setId(1L);
        office.setName("MohammadReza");
        office.setCode("6104337598632220");
        office.setProvider(ABAN_ESHOP);
        office.setInactive(true);

        when(officeRepository.findById(1L)).thenReturn(java.util.Optional.of(office));
        String expected = om.writeValueAsString(office);

        ResponseEntity<String> response = restTemplate.getForEntity("/office/1", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);

        verify(officeRepository, times(1)).findById(1L);
    }

    @Test
    public void find_OfficeById_Internal_Server_Error_500() throws JSONException {
        OfficeDto officeDto = new OfficeDto();
        officeDto.setId(1L);
        officeDto.setName("MohammadReza");
        officeDto.setCode("6104337598632220");
        officeDto.setProvider("aban-eshop");
        officeDto.setInactive(true);

        String Json = " {id: 1,name: \"MohammadReza\",code: \"6104337598632220\",provider: \"ABAN_ESHOP\",inactive: true}";

        ResponseEntity<String> response = restTemplate.getForEntity("/office/2", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void find_OfficeById_Status404() {
        OfficeDto officeDto = new OfficeDto();
        officeDto.setId(1L);
        officeDto.setName("MohammadReza");
        officeDto.setCode("6104337598632220");
        officeDto.setProvider("aban-eshop");
        officeDto.setInactive(true);

        String Json = " {id: 1,name: \"MohammadReza\",code: \"6104337598632220\",provider: \"ABAN_ESHOP\",inactive: true}";

        ResponseEntity<String> response = restTemplate.getForEntity("/book/1", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void save_Office() throws JsonProcessingException, JSONException {
        OfficeDto officeDto = new OfficeDto();
        officeDto.setId(1L);
        officeDto.setName("MohammadReza");
        officeDto.setCode("6104337598632220");
        officeDto.setProvider("aban-eshop");
        officeDto.setInactive(true);

        Office office = new Office();
        office.setId(1L);
        office.setName("MohammadReza");
        office.setCode("6104337598632220");
        office.setProvider(ABAN_ESHOP);
        office.setInactive(true);

        String expected = om.writeValueAsString(officeDto);

        ResponseEntity<String> response = restTemplate.postForEntity("/office", officeDto, String.class);
        assertThat(HttpStatus.CREATED).isEqualTo(response.getStatusCode());

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }
    @Test
    public void save_Office_InternalServerError_Status500() throws JsonProcessingException, JSONException {
        OfficeDto officeDto = new OfficeDto();

        String expected = om.writeValueAsString(officeDto);

        ResponseEntity<String> response = restTemplate.postForEntity("/office", officeDto, String.class);
        assertThat(HttpStatus.INTERNAL_SERVER_ERROR).isEqualTo(response.getStatusCode());
    }

    @Test
    public void methodNotAllowed_Error_405() throws JsonProcessingException, JSONException {
        String expected = "{status:405,error:\"Method Not Allowed\"}";
        String Json = "{id: 1,name: \"MohammadReza\",code: \"6104337598632220\",provider: \"ABAN_ESHOP\",inactive: true}";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(Json, httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange("/office", HttpMethod.PATCH, entity, String.class);
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
        //JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void isBadRequest_Error_400() throws JSONException {

        String Json = "{ \"id\": 1,\"name\": \"MohammadReza\",]\"code\": \"6104337598632220\",\"provider\": \"ABAN_ESHOP\",\"inactive\": true}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(Json, headers);

        String expected = "{\"status\":400}";


        ResponseEntity<String> response = restTemplate.postForEntity("/office", entity, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        JSONAssert.assertEquals(expected, response.getBody(), false);

    }

    @Test
    public void deleteById() {
        Office office = new Office();
        office.setId(1L);
        office.setName("MohammadReza");
        office.setCode("6104337598632220");
        office.setProvider(ABAN_ESHOP);
        office.setInactive(true);

        doNothing().when(officeRepository).deleteById(1L);

        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange("/office/1", HttpMethod.DELETE, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(officeRepository, times(1)).deleteById(1L);
    }

//    @Test
//    public void deleteById_InternalServerError_Status500(){
//        Office office = new Office();
//        office.setId(1L);
//        office.setName("MohammadReza");
//        office.setCode("6104337598632220");
//        office.setProvider(ABAN_ESHOP);
//        office.setInactive(true);
//
//        officeRepository.save(office);
//        HttpHeaders httpHeaders = new HttpHeaders();
//        HttpEntity<String> entity=new HttpEntity<>(null,httpHeaders);
//
//        ResponseEntity<String> response=restTemplate.exchange("/office/4",HttpMethod.DELETE,entity,String.class);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }

    @Test
    public void update_Office() throws JsonProcessingException, JSONException {
        OfficeDto officeDto = new OfficeDto();
        officeDto.setId(1L);
        officeDto.setName("MohammadReza");
        officeDto.setCode("6104337598632220");
        officeDto.setProvider("aban-eshop");
        officeDto.setInactive(true);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(om.writeValueAsString(officeDto), headers);

        ResponseEntity<String> response = restTemplate.exchange("/office/1", HttpMethod.PUT, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        //JSONAssert.assertEquals(om.writeValueAsString(officeDto), response.getBody(), false);

    }

//    @Test
//    public void update_Office_InternalServerError_Status500() throws JsonProcessingException, JSONException {
//        OfficeDto officeDto = new OfficeDto();officeDto.setId(1L);officeDto.setName("MohammadReza");officeDto.setCode("6104337598632220");
//        officeDto.setProvider("aban-eshop");
//        officeDto.setInactive(true);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<String> entity = new HttpEntity<>(om.writeValueAsString(officeDto), headers);
//
//        ResponseEntity<String> response = restTemplate.exchange("/office/11", HttpMethod.PUT, entity, String.class);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        //JSONAssert.assertEquals(om.writeValueAsString(officeDto), response.getBody(), false);
//
//    }
}