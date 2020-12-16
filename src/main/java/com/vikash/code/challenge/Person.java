package com.vikash.code.challenge;

public class Person {

    private String name;
    
    private int age;
    
    private int salary;
    
    private String location;
    
    private Gender gender;

    public Person(String name, int age, int salary, String location, Gender gender) {
        super();
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.location = location;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Person [name=").append(name).append(", age=").append(age).append(", salary=").append(salary)
                .append(", location=").append(location).append(", gender=").append(gender).append("]");
        return builder.toString();
    }

}
