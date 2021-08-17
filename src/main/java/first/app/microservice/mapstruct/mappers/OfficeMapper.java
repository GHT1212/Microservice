package first.app.microservice.mapstruct.mappers;


import first.app.microservice.enums.Shops;
import first.app.microservice.mapstruct.Entities.OfficeDto;
import first.app.microservice.model.Office;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring" , uses = Office.class)
public interface OfficeMapper {

    @Mapping(qualifiedByName = "map", source = "provider.value.", target = "provider")
    OfficeDto officeToOfficeDto(Office office);


    @Mapping(qualifiedByName = "map", source = "officeDto.provider", target = "provider")
    Office officeDtoToOffice(OfficeDto officeDto);


    @Named("map")
    default Shops map(String value){
          Shops shops = null;

          switch (value){

              case "aban-eshop":
                  shops = Shops.ABAN_ESHOP;
                  break;
              case "huawei":
                  shops = Shops.HUAWEI;
                  break;
              case "hami-mymci":
                  shops = Shops.HAMI_MYMCI;
                  break;
          }
          return shops;
    }
}
