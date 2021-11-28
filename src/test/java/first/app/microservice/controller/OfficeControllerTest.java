package first.app.microservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import first.app.microservice.mapstruct.Entities.OfficeDto;
import first.app.microservice.mapstruct.mappers.OfficeMapper;
import first.app.microservice.model.Office;
import first.app.microservice.service.OfficeService;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.engine.TestExecutionResult;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static first.app.microservice.enums.Shops.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OfficeControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private OfficeService officeService;

    @MockBean
    private OfficeMapper officeMapper;

    @Test
    public void contextLoads(){
        assertNotNull(officeService);
        assertNotNull(officeMapper);
    }

    @Test
    public void whenFindAllOffices_ThenReturnOfficeDtos() throws JsonProcessingException, JSONException {
        OfficeDto officeDto = new OfficeDto();officeDto.setName("MohammadReza");officeDto.setCode("6104337598632220");officeDto.setProvider("aban-eshop");
        officeDto.setInactive(true);

        List<OfficeDto> officeDtos = Arrays.asList(officeDto);

        when(officeService.findAll()).thenReturn(new ArrayList<Office>());
        when(officeMapper.officesToOfficesDto(any())).thenReturn(officeDtos);//What Is Any Here? //Why We Use It?

        String Json = om.writeValueAsString(officeDtos);

        ResponseEntity<String> response = restTemplate.getForEntity("/office" , String.class);
        assertEquals(HttpStatus.OK ,response.getStatusCode());
        JSONAssert.assertEquals(Json ,response.getBody() , false);

        verify(officeService ,times(1)).findAll();
        verifyNoMoreInteractions(officeService);

    }


//    @Test
//    public void find_All_Offices() throws JsonProcessingException, JSONException {
//        Office office = new Office();office.setId(1L);office.setName("MohammadReza");office.setCode("6104337598632220");office.setProvider(ABAN_ESHOP);
//        office.setInactive(true);
//
//        Office office1 = new Office();office1.setId(2L);office1.setName("Shamsollah");office1.setCode("6037997387779797");office1.setProvider(HUAWEI);
//        office1.setInactive(true);
//
//        List<Office> offices = new ArrayList<>();
//        offices.add(office);
//        offices.add(office1);
//
//        when(officeService.findAll()).thenReturn(offices);
//        String Json = om.writeValueAsString(offices);
//
//        ResponseEntity<String> response = restTemplate.getForEntity("/office", String.class);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        JSONAssert.assertEquals(Json, response.getBody(), false);
//
//        verify(officeService, times(1)).findAll();
//            }

//    @Test
//    public void find_Office_By_Id() throws JsonProcessingException, JSONException {
//        Office office = new Office();office.setId(1L);office.setName("MohammadReza");office.setCode("6104337598632220");office.setProvider(ABAN_ESHOP);
//        office.setInactive(true);
//
//
//        when(officeService.findById(1L)).thenReturn(java.util.Optional.of(office));
//        String Json = om.writeValueAsString(office);
//
//        ResponseEntity<String> response = restTemplate.getForEntity("/office/1", String.class);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        JSONAssert.assertEquals(Json, response.getBody(), false);
//
//        verify(officeService, times(1)).findById(1L);
//    }

    @Test
    public void office_Find_By_Id() throws JsonProcessingException, JSONException {
        OfficeDto officeDto = new OfficeDto();officeDto.setId(1L);officeDto.setName("MohammadReza");officeDto.setCode("6104337598632220");officeDto.setProvider("aban-eshop");
        officeDto.setInactive(true);

        when(officeService.findById(anyLong())).thenReturn(java.util.Optional.of(new Office()));
        when(officeMapper.officeToOfficeDto(any(Office.class))).thenReturn(officeDto);

        String Json = om.writeValueAsString(officeDto);

        ResponseEntity<String> response = restTemplate.getForEntity("/office/1" , String.class);
        assertEquals(HttpStatus.OK , response.getStatusCode());
        JSONAssert.assertEquals(Json , response.getBody() , false);

        verify(officeService , times(1)).findById(1L);
        verifyNoMoreInteractions(officeService);
    }

    @Test
    public void find_by_id_Error(){
        when(officeService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<String> response = restTemplate.getForEntity("/office/1", String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    }

//    @Test
//    public void findAll_Error(){
//        ResponseEntity<String> response = restTemplate.getForEntity("/office", String.class);
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
//
//    }

    @Test
    public void findById_WhenLetterInput_ThenReturnError(){
        ResponseEntity<String> response = restTemplate.getForEntity("/office/A", String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void saveOfficeEntity_ThenReturnStatus201() throws JsonProcessingException, JSONException {
        OfficeDto officeDto = new OfficeDto();officeDto.setName("MohammadReza");officeDto.setCode("6104337598632220");officeDto.setProvider("aban-eshop");officeDto.setInactive(true);

        when(officeService.register(any(Office.class))).thenReturn(new Office());
        when(officeMapper.officeDtoToOffice(officeDto)).thenReturn(new Office());
        when(officeMapper.officeToOfficeDto(any(Office.class))).thenReturn(officeDto);

        String Json = om.writeValueAsString(officeDto);

        ResponseEntity<String> response = restTemplate.postForEntity("/office" , officeDto , String.class);
        assertEquals(HttpStatus.CREATED , response.getStatusCode());
        JSONAssert.assertEquals(Json , response.getBody() , false);

        verify(officeService ,times(1)).register(any(Office.class));
        verifyNoMoreInteractions(officeService);
    }
    @Test
    public void saveNullOfficeEntity_ThenReturnStatus500() throws JsonProcessingException, JSONException {
        OfficeDto officeDto = new OfficeDto();
//        when(officeService.register(any(Office.class))).thenReturn(new Office());
//        when(officeMapper.officeDtoToOffice(officeDto)).thenReturn(new Office());
//        when(officeMapper.officeToOfficeDto(any(Office.class))).thenReturn(officeDto);

        String Json = om.writeValueAsString(officeDto);

        ResponseEntity<String> response = restTemplate.postForEntity("/office" , officeDto , String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR , response.getStatusCode());
        //JSONAssert.assertEquals(Json , response.getBody() , false);

//        verify(officeService ,times(1)).register(any(Office.class));
//        verifyNoMoreInteractions(officeService);
    }

    @Test
    public void saveOfficeEntityWithNullName_ThenReturnStatus500() throws JsonProcessingException, JSONException {
        OfficeDto officeDto = new OfficeDto();officeDto.setName("");officeDto.setCode("6104337598632220");officeDto.setProvider("aban-eshop");officeDto.setInactive(true);
//        when(officeService.register(any(Office.class))).thenReturn(new Office());
//        when(officeMapper.officeDtoToOffice(officeDto)).thenReturn(new Office());
//        when(officeMapper.officeToOfficeDto(any(Office.class))).thenReturn(officeDto);

        String Json = om.writeValueAsString(officeDto);

        ResponseEntity<String> response = restTemplate.postForEntity("/office" , officeDto , String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR , response.getStatusCode());
        //JSONAssert.assertEquals(Json , response.getBody() , false);

//        verify(officeService ,times(1)).register(any(Office.class));
//        verifyNoMoreInteractions(officeService);
    }
    @Test
    public void saveOfficeEntityWithNullCode_ThenReturnStatus500() throws JsonProcessingException, JSONException {
        OfficeDto officeDto = new OfficeDto();officeDto.setId(1L);officeDto.setName("MohammadReza");officeDto.setCode("");officeDto.setProvider("aban-eshop");officeDto.setInactive(true);
//        when(officeService.register(any(Office.class))).thenReturn(new Office());
//        when(officeMapper.officeDtoToOffice(officeDto)).thenReturn(new Office());
//        when(officeMapper.officeToOfficeDto(any(Office.class))).thenReturn(officeDto);

        String Json = om.writeValueAsString(officeDto);

        ResponseEntity<String> response = restTemplate.postForEntity("/office" , officeDto , String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR , response.getStatusCode());
        //JSONAssert.assertEquals(Json , response.getBody() , false);

//        verify(officeService ,times(1)).register(any(Office.class));
//        verifyNoMoreInteractions(officeService);
    }
    @Test
    public void saveOfficeEntityWithNullProvider_ThenReturnStatus500() throws JsonProcessingException, JSONException {
        OfficeDto officeDto = new OfficeDto();officeDto.setId(1L);officeDto.setName("MohammadReza");officeDto.setCode("6104337598632220");officeDto.setProvider("");officeDto.setInactive(true);
//        when(officeService.register(any(Office.class))).thenReturn(new Office());
//        when(officeMapper.officeDtoToOffice(officeDto)).thenReturn(new Office());
//        when(officeMapper.officeToOfficeDto(any(Office.class))).thenReturn(officeDto);

        String Json = om.writeValueAsString(officeDto);

        ResponseEntity<String> response = restTemplate.postForEntity("/office" , officeDto , String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR , response.getStatusCode());
        //JSONAssert.assertEquals(Json , response.getBody() , false);

//        verify(officeService ,times(1)).register(any(Office.class));
//        verifyNoMoreInteractions(officeService);
    }
    @Test
    public void DeleteById(){
        Office office = new Office();office.setId(1L);office.setName("MohammadReza");office.setCode("6104337598632220");office.setProvider(HUAWEI);office.setInactive(true);

        when(officeService.deleteById(office.getId())).thenReturn(200);

        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<String> httpEntity = new HttpEntity(null , httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange("/office/1" , HttpMethod.DELETE, httpEntity ,String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(officeService , times(1)).deleteById(anyLong());

    }
    @Test
    public void sendWrongId_ThenReturnStatus400(){
        Office office = new Office();office.setId(1L);office.setName("MohammadReza");office.setCode("6104337598632220");office.setProvider(HUAWEI);office.setInactive(true);

        //doNothing().when(officeService).deleteById(office.getId());
        when(officeService.deleteById(office.getId())).thenReturn(200);

        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<String> httpEntity = new HttpEntity<>(null , httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange("/office/A" , HttpMethod.DELETE , httpEntity , String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        // verify(officeService , times(1)).deleteById(anyLong());

        //When We Use verify
    }
    @Test
    public void sendWrongRequest_ThenReturnStatus404(){
        Office office = new Office();office.setId(1L);office.setName("MohammadReza");office.setCode("6104337598632220");office.setProvider(HUAWEI);office.setInactive(true);

        when(officeService.deleteById(office.getId())).thenReturn(200);

        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<String> httpEntity = new HttpEntity<>(null , httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange("/office" + 2 , HttpMethod.DELETE , httpEntity , String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }
    @Test
    public void sendInvalidId_ThenReturnStatus500(){
        Office office = new Office();office.setId(1L);office.setName("MohammadReza");office.setCode("6104337598632220");office.setProvider(HUAWEI);office.setInactive(true);

        when(officeService.deleteById(2L)).thenReturn(500);

        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<String> httpEntity = new HttpEntity<>(null , httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange("/office/2" , HttpMethod.DELETE , httpEntity , String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    }
    @Test
    public void updateOffice_ThenReturnStatus200() throws JsonProcessingException, JSONException {
        Long id = 1L;
        OfficeDto officeDto = new OfficeDto();officeDto.setId(1L);officeDto.setName("MohammadReza");officeDto.setCode("6104337598632220");officeDto.setProvider("aban-eshop");officeDto.setInactive(true);

        Office office = new Office();office.setId(officeDto.getId());office.setName(officeDto.getName());office.setCode(officeDto.getCode());office.setProvider(ABAN_ESHOP);office.setInactive(officeDto.isInactive());


        when(officeService.updateById(id,office)).thenReturn(new Office());
        when(officeMapper.officeDtoToOffice(any(OfficeDto.class))).thenReturn(office);

        String Json = om.writeValueAsString(officeDto);
        HttpHeaders httpHeaders = new HttpHeaders();httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(Json , httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange("/office/1" , HttpMethod.PUT , httpEntity ,String.class);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        //JSONAssert.assertEquals(Json,response.getBody(),false);/Because Nothing in the DataBase?

        verify(officeService , times(1)).updateById(id , office);
        //verifyNoMoreInteractions(officeService); Why We Use This?


    }

    @Test
    public void updateOffice_WrongContentType_ReturnError415UnsupportedMediaType() throws JsonProcessingException, JSONException {
        Long id = 1L;
        OfficeDto officeDto = new OfficeDto();officeDto.setId(1L);officeDto.setName("MohammadReza");officeDto.setCode("6104337598632220");officeDto.setProvider("aban-eshop");officeDto.setInactive(true);

        Office office = new Office();office.setId(officeDto.getId());office.setName(officeDto.getName());office.setCode(officeDto.getCode());office.setProvider(ABAN_ESHOP);office.setInactive(officeDto.isInactive());


        when(officeService.updateById(id,office)).thenReturn(new Office());
        when(officeMapper.officeDtoToOffice(any(OfficeDto.class))).thenReturn(office);

        String Json = om.writeValueAsString(officeDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<String> httpEntity = new HttpEntity<>(Json , httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange("/office/1" , HttpMethod.PUT , httpEntity ,String.class);

        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE,response.getStatusCode());

    }






}