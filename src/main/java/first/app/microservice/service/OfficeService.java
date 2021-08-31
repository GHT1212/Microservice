package first.app.microservice.service;
import ch.qos.logback.classic.Logger;
import first.app.microservice.MicroserviceApplication;
import first.app.microservice.model.Office;
import first.app.microservice.repository.OfficeRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class OfficeService {

    private OfficeRepository officeRepository;

    static final Logger logger = (Logger) LoggerFactory.getLogger(MicroserviceApplication.class);

    @Autowired
    public OfficeService(OfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }


    public Office register(Office office) {
        logger.info("Service : Saving Office {}" , office.toString());
        Office office1 = officeRepository.save(office);
        logger.info("Service : Output for Register is {}" , office1.toString());
        return office1;
    }

    public List<Office> findAll() {
        List<Office> offices = officeRepository.findAll();
        logger.info("Service: Finding All Offices {}" , offices.toString());
        return offices;

    }

    public void deleteById(Long id) {
        logger.info("Service: Deleting user with id {}", id);
        officeRepository.deleteById(id);
    }

    public Office updateById(Long id, Office office) {
        logger.info("Service: Fetching Office with id {}", id);
        Office tmpOffice = officeRepository.findById(id).get();
        logger.info("Service: Updating Office with id {}", id);
        tmpOffice.setName(office.getName());
        tmpOffice.setCode(office.getCode());
        tmpOffice.setProvider(office.getProvider());
        tmpOffice.setProvider(tmpOffice.getProvider());
        officeRepository.save(tmpOffice);
        return tmpOffice;
    }

    public Optional<Office> findById(Long id) {
        logger.info("Service: Finding Office with id {}", id);
       Optional<Office> office = officeRepository.findById(id);
       return office;
    }
}
