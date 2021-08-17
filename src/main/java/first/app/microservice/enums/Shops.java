package first.app.microservice.enums;

public enum Shops {
    HUAWEI("huawei"),
    ABAN_ESHOP("aban-eshop"),
    HAMI_MYMCI("hami-mymci");


    private final String value;

    Shops(String value){
        this.value = value;
    }

   public String getValue() {return  value;}

}
