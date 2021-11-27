package first.app.microservice.service;
import ch.qos.logback.classic.Logger;
import first.app.microservice.MicroserviceApplication;
import first.app.microservice.exception.OfficeNotFoundException;
import first.app.microservice.model.Office;
import first.app.microservice.repository.OfficeRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfficeService {

    private final OfficeRepository officeRepository;

    static final Logger logger = (Logger) LoggerFactory.getLogger(MicroserviceApplication.class);

    @Autowired
    public OfficeService(OfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }


    public Office register(Office office) {
        logger.info("Service : Saving Office {}" , office.toString());
        officeRepository.save(office);
        logger.info("Service : Output for Register is {}" , office.toString());
        return office;
    }

    public List<Office> findAll() {
        List<Office> offices = officeRepository.findAll();
        logger.info("Service: Finding All Offices {}" , offices.toString());
        return offices;

    }
    public int deleteById(Long id) {
        int status = 0;
        logger.info("Service: Fetching Office with id {}", id);
        //Optional<Office> office1 = Optional.ofNullable(officeRepository.findById(id).orElseThrow(() -> new OfficeNotFoundException(id)));
        Optional<Office> office1 = officeRepository.findById(id);
        if (office1.get().getId() != id)
            status = 500;
        else {
            logger.info("Service: Deleting user with id {}", id);
            officeRepository.deleteById(id);
            status = 200;
        }
        return status;
    }

    public Office updateById(Long id, Office office) {
        logger.info("Service: Fetching Office with id {}", id);
        Optional<Office> office1 = Optional.ofNullable(officeRepository.findById(id).orElseThrow(() -> new OfficeNotFoundException(id)));
        officeRepository.deleteById(id);
        logger.info("Service: Updating Office with id {}", id);
        office.setId(id);
        officeRepository.save(office);
//        tmpOffice.get().setName(office.getName());
//        tmpOffice.get().setCode(office.getCode());
//        tmpOffice.get().setProvider(office.getProvider());
//        tmpOffice.get().setInactive(office.isInactive());
        officeRepository.save(office);
        return office;
    }

    public Optional<Office> findById(Long id) {
        logger.info("Service: Finding Office with id {}", id);
        Optional<Office> office1 = Optional.ofNullable(officeRepository.findById(id).orElseThrow(() -> new OfficeNotFoundException(id)));
        Optional<Office> office = officeRepository.findById(id);
        return office;
    }
}
