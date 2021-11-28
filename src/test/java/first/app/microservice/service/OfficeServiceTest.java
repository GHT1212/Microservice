package first.app.microservice.service;



import first.app.microservice.exception.OfficeNotFoundException;
import first.app.microservice.model.Office;
import first.app.microservice.repository.OfficeRepository;
import org.h2.command.dml.MergeUsing;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static first.app.microservice.enums.Shops.HUAWEI;
import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static first.app.microservice.enums.Shops.ABAN_ESHOP;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@SpringBootTest
public class OfficeServiceTest {

    @Autowired
    private OfficeService officeService;

    @MockBean
    private OfficeRepository officeRepository;

    @Test
    void testContext() {
        assertNotNull(officeRepository);
        assertNotNull(officeService);
    }

    @Test
    public void findAllOffices_ReturnListOfOffices(){
        Office office = new Office();office.setId(1L);office.setName("MohammadReza");office.setCode("6104337598632220");office.setProvider(ABAN_ESHOP);office.setInactive(true);
        Office office1 = new Office();office.setId(2L);office.setName("AliReza");office.setCode("6037991945200933");office.setProvider(HUAWEI);office.setInactive(true);

        List<Office> offices = new ArrayList<>();

        offices.add(office);
        offices.add(office1);

        for (Office validOffice:offices) {
              officeService.register(validOffice);
        }
        when(officeRepository.findAll()).thenReturn(offices);

        List<Office> actualOffices = officeService.findAll();

        assertEquals(actualOffices , offices);
    }

    @Test
    public void findOfficeById_ReturnOffice(){
        Long id = 1L;
        Office office = new Office();office.setId(id);office.setName("MohammadReza");office.setCode("6104337598632220");office.setProvider(ABAN_ESHOP);office.setInactive(true);


        when(officeRepository.save(office)).thenReturn(new Office());
        when(officeRepository.findById(id)).thenReturn(Optional.of(office));
        officeService.register(office);
        //what exacly when and then retuen do?

        Optional<Office> actualOffice = officeService.findById(id);


        assertEquals(actualOffice , Optional.of(office));
    }
    @Test
    public void sendWrongId_ThenReturnOfficeNotFoundException(){
        Long id = 1L;
        Office office = new Office();office.setId(id);office.setName("MohammadReza");office.setCode("6104337598632220");office.setProvider(ABAN_ESHOP);office.setInactive(true);

        when(officeRepository.save(office)).thenReturn(new Office());
        when(officeRepository.findById(id)).thenReturn(Optional.of(office));
        officeService.register(office);

        assertThatThrownBy(() -> {
            officeService.findById(2L);
        }).isInstanceOf(OfficeNotFoundException.class).hasMessageContaining("Could Not Find Office2");
    }

    @Test
    public void saveOffice(){
        Long id = 1L;
        Office office = new Office();office.setId(id);office.setName("MohammadReza");office.setCode("6104337598632220");office.setProvider(ABAN_ESHOP);office.setInactive(true);

        when(officeRepository.save(office)).thenReturn(new Office());

        assertNotNull(officeService.register(office));
        assertEquals(Office.class,officeService.register(office).getClass());
    }

    @Test
    public void deleteOfficeById(){
        Office office = new Office();office.setName("MohammadReza");office.setCode("6104337598632220");office.setProvider(ABAN_ESHOP);office.setInactive(true);
        officeService.register(office);

        officeService.deleteById(1L);
        assertThatThrownBy(() -> {
            officeService.deleteById(1L);
        }).isInstanceOf(OfficeNotFoundException.class).hasMessageContaining("Could Not Find Office1");

    }
    @Test
    public void deleteById(){
        Long id = 1L;
        Office office = new Office();office.setId(id);office.setName("MohammadReza");office.setCode("6104337598632220");office.setProvider(ABAN_ESHOP);office.setInactive(true);

        when(officeRepository.save(office)).thenReturn(new Office());
        when(officeRepository.findById(id)).thenReturn(Optional.of(office));
        officeService.register(office);

        doNothing().when(officeRepository).deleteById(anyLong());

        officeService.deleteById(office.getId());

        verify(officeRepository, times(1)).deleteById(office.getId());
    }
    @Test
    public void updateById(){
        Long id = 1L;
        Office office = new Office();office.setId(id);office.setName("MohammadReza");office.setCode("6104337598632220");office.setProvider(ABAN_ESHOP);office.setInactive(true);
        Office office1 = new Office();office.setName("AliReza");office.setCode("6037991945200933");office.setProvider(HUAWEI);office.setInactive(true);

        when(officeRepository.save(any(Office.class))).thenReturn(new Office());
        doNothing().when(officeRepository).deleteById(id);
        when(officeService.updateById(id , office1)).thenReturn(office1);

        officeService.register(office);
        Office office2 =  officeService.updateById(id , office1);


        assertEquals(office2 , office1);

    }

    @Test
    public void deleteByIdOfficeNotFoundStatus500(){
        Office office = new Office();office.setId(1L);office.setName("MohammadReza");office.setCode("6104337598632220");office.setProvider(ABAN_ESHOP);office.setInactive(true);
        officeService.register(office);

        assertThatThrownBy(() -> {
            officeService.deleteById(2L);
        }).isInstanceOf(OfficeNotFoundException.class).hasMessageContaining("Could Not Find Office2");

    }

}