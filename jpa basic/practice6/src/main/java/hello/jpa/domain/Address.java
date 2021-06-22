package hello.jpa.domain;

import javax.persistence.Embeddable;
import java.util.Objects;

// 값타입임으로 불변객체로 만드는것이중요, 엔티티객체들이 공유하면 안되기떄무네~
@Embeddable
public class Address {
    private String city;

    private String street;

    private String zipcode;
    public Address(String city, String  street, String  zipcode){
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
    public Address(){

    }

    public String getCity() {
        return city;
    }


    public String getStreet() {
        return street;
    }


    public String getZipcode() {
        return zipcode;
    }

    //getter로 접근하도록 하는것이안전함 직접 필드에 접근하는것보단
    //equals, hashcode는 자동으로만들자.. 그게 안전함
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(getCity(), address.getCity()) &&
                Objects.equals(getStreet(), address.getStreet()) &&
                Objects.equals(getZipcode(), address.getZipcode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCity(), getStreet(), getZipcode());
    }
}
