package reflections.JSONSerializer.data;

public class Person {
    private String name;
    private int age;
    private Address address;
    private Company company;
    private int salary;

    public Person(String name, int age, Address address, Company company, int salary) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.company = company;
        this.salary = salary;
    }
}
