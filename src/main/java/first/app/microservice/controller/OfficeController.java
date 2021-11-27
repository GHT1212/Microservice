package first.app.microservice.controller;


import ch.qos.logback.classic.Logger;
import first.app.microservice.MicroserviceApplication;
import first.app.microservice.exception.OfficeNotFoundException;
import first.app.microservice.mapstruct.Entities.OfficeDto;
import first.app.microservice.mapstruct.mappers.OfficeMapper;
import first.app.microservice.model.Office;
import first.app.microservice.service.OfficeService;
import lombok.Data;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(OfficeController.OFFICE_URL)
@Data
public class OfficeController {

    public static final String OFFICE_URL = "/office";

    private final OfficeService officeService;
    private final OfficeMapper officeMapper;
    static final Logger logger = (Logger) LoggerFactory.getLogger(MicroserviceApplication.class);




    @PostMapping
    public ResponseEntity<OfficeDto> addOffice(@RequestBody OfficeDto officeDto){
        logger.info("Controller:Registering new Office  {}" ,officeDto.toString());
        Office office = officeService.register(officeMapper.officeDtoToOffice(officeDto));
        OfficeDto officeDto1  =  officeMapper.officeToOfficeDto(office);
        logger.info("Controller:Registering new Office  {}" ,officeDto1.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(officeDto1);
    }

    @GetMapping
    public ResponseEntity<List<OfficeDto>> all(){
        logger.info("Controller:Fetching All Offices.");
        return ResponseEntity.ok(officeMapper.officesToOfficesDto(officeService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfficeDto> findById(@PathVariable Long id){
        Optional<Office> office = Optional.ofNullable(officeService.findById(id).orElseThrow(() -> new OfficeNotFoundException(id)));
        OfficeDto officeDto = officeMapper.officeToOfficeDto(office.get());
        logger.info("Controller: Fetching user with id {}", id);
        return ResponseEntity.ok(officeDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id){
        logger.info("Controller: Finding user with id {}", id);
        officeService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<OfficeDto> updateOffice(@PathVariable Long id , @RequestBody OfficeDto officeDto){
        logger.info("Controller:Sending new Office and Fetching Office with id {}", id);
        Office office = officeMapper.officeDtoToOffice(officeDto);
        Office office1 = officeService.updateById(id,office);
        OfficeDto officeDto1 = officeMapper.officeToOfficeDto(office1);
        return new ResponseEntity<>(officeDto1 , HttpStatus.OK);
    }















}
