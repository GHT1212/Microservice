package first.app.microservice.service;

import first.app.microservice.model.Office;
import first.app.microservice.repository.OfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfficeService {

    private OfficeRepository officeRepository;

    @Autowired
    public OfficeService(OfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }

    public Office save(Office office) {
        return officeRepository.save(office);
    }
}
