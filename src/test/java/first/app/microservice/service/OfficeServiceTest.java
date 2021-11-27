package first.app.microservice.service;



import first.app.microservice.exception.OfficeNotFoundException;
import first.app.microservice.model.Office;
import first.app.microservice.repository.OfficeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import static first.app.microservice.enums.Shops.ABAN_ESHOP;

@DataJpaTest
public class OfficeServiceTest {

    @Autowired
    private OfficeService officeService;

    @MockBean
    private OfficeRepository officeRepository;


    @Test
    public void saveOfficeTest(){
        Office office = new Office();office.setName("MohammadReza");office.setCode("6104337598632220");office.setProvider(ABAN_ESHOP);office.setInactive(true);
        officeService.register(office);

        assertThat(officeService.findById(1L).get().getId().equals(1L));
    }

    @Test
    public void saveOffice(){
        Office office = new Office();office.setName("MohammadReza");office.setCode("6458791232587413");office.setProvider(ABAN_ESHOP);office.setInactive(true);
        officeService.register(office);

        assertThat(officeService.findById(1L).get().getId().equals(1L));
    }


    @Test
    public void getOfficeByIdTest(){
        Office office = new Office();office.setName("MohammadReza");office.setCode("6104337598632220");office.setProvider(ABAN_ESHOP);office.setInactive(true);
        officeService.register(office);
        Optional<Office> office1 = officeService.findById(1L);

        assertThat(office1).isNotNull();
    }

    @Test
    public void getOfficeById(){
        //Office office = new Office();office.setId(1L);office.setName("MohammadReza");office.setCode("6104337598632220");office.setProvider(ABAN_ESHOP);office.setInactive(true);

        assertThatThrownBy(() -> {
            officeService.findById(1L);
        }).isInstanceOf(OfficeNotFoundException.class).hasMessageContaining("Could Not Find Office1");
    }


    @Test
    public void getListOfOffices(){
        Office office = new Office();office.setName("MohammadReza");office.setCode("6104337598632220");office.setProvider(ABAN_ESHOP);office.setInactive(true);
        officeService.register(office);
        //Office office1 = new Office();office.setName("AliReza");office.setCode("6037991942473392");office.setProvider(ABAN_ESHOP);office.setInactive(true);
        //officeService.register(office1);

        List<Office> offices = officeService.findAll();

        assertThat(offices.size()).isGreaterThan(0);
    }

    @Test
    public void updateOffice(){
        Office office = new Office();office.setName("MohammadReza");office.setCode("6104337598632220");office.setProvider(ABAN_ESHOP);office.setInactive(true);
        officeService.register(office);
        Office office1 = new Office();office1.setName("AliReza");office1.setCode("6104337598632220");office1.setProvider(ABAN_ESHOP);office1.setInactive(true);

        Office office2 = officeService.updateById(1L , office1);

        assertThat(office2.getName()).isEqualTo("AliReza");
    }

    @Test
    public void updateOfficeOfficeNotFoundException(){
        Office office = new Office();office.setName("MohammadReza");office.setCode("6104337598632220");office.setProvider(ABAN_ESHOP);office.setInactive(true);
        officeService.register(office);
        Office office1 = new Office();office1.setName("AliReza");office1.setCode("6104337598632220");office1.setProvider(ABAN_ESHOP);office1.setInactive(true);


        assertThatThrownBy(() -> {
            officeService.updateById(2L ,office1);
        }).isInstanceOf(OfficeNotFoundException.class).hasMessageContaining("Could Not Find Office2");

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
    public void deleteByIdOfficeNotFoundException(){
        Office office = new Office();office.setName("MohammadReza");office.setCode("6104337598632220");office.setProvider(ABAN_ESHOP);office.setInactive(true);
        officeService.register(office);

        assertThatThrownBy(() -> {
            officeService.deleteById(2L);
        }).isInstanceOf(OfficeNotFoundException.class).hasMessageContaining("Could Not Find Office2");

    }

}