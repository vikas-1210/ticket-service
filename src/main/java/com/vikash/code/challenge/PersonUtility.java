package com.vikash.code.challenge;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class PersonUtility {
    
    private PersonUtility() {}
    
    public static List<Person> prepareTestData(){
		List<Person> personList = new ArrayList<>();
		personList.add(new Person("Sai", 35, 24000, "Hyderabad", Gender.MALE));
		personList.add(new Person("Srinu", 30, 21000, "Hyderabad", Gender.MALE));
		personList.add(new Person("Rakshita", 29, 29000, "Hyderabad", Gender.FEMALE));
		personList.add(new Person("Reena", 33, 34000, "Hyderabad", Gender.FEMALE));
		personList.add(new Person("Vishnu", 25, 20000, "Bangalore", Gender.MALE));
		personList.add(new Person("Ram", 42, 80000, "Bangalore", Gender.MALE));
		personList.add(new Person("Selvam", 21, 24000, "Chennai", Gender.MALE));
		return personList;
	}
    
    public static double getAvegargeSalaryPostFilter(List<Person> personList, Predicate<? super Person> filterPredicate) {
        return personList.stream().filter(filterPredicate).mapToInt(Person::getSalary).average().getAsDouble();
    }

    public static Map<String, List<Person>> groupByLocation(List<Person> personList){
        return personList.stream().collect(Collectors.groupingBy(Person::getLocation));
    }
    
}
