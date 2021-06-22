package hellojpa;

public class ValueMain {
    public static void main(String[] args) {
        int a = 10;
        int b = 10;

        System.out.println(new Integer(a).equals(new Integer(b)));

        Address address1 = new Address("seoul", "street", "11032");
        Address address2 = new Address("seoul", "street", "11032");
        System.out.println(address1.equals(address2));
        //Object클래스의 equals메서드임.



    }
}
