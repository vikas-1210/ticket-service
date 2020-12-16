package com.vikash.code.challenge;

import java.util.List;
import java.util.Map;

/*
 * Person has 5 Attributes Age ,Salary , Location , Gender , Name
 *
 * Create Sufficient amount of Person Data to meet following Requirements
 *
 * 1. Average Salary of Persons who meet following criterion
 * - Age > 25
 * - Salary > 25000
 * - Location = Hyderabad
 * - Gender = Female
 * - Name starting with "R"
 *
 * 2. Segregate the Persons based on Locations
 *
 *  output : Hyderabad
 *                    Sai
 *                    Srinu
 *                    Rakshita
 *                    Reena
 *           Bangalore
 *                    vishnu
 *                    ram
 *           Chennai
 *                    Selvam
 * Note : Use on Java 8 features like STreams,Lamda , Method reference , optional , Functional Interface etc
 *
 */

public class VikashCodeChallenge1 {

	public static void main(String[] args) {
		List<Person> personList = PersonUtility.prepareTestData();
		
		// Case 1
		double averageSalary = PersonUtility.getAvegargeSalaryPostFilter(personList,
				e -> e.getAge()>25 && e.getSalary()>25000 && "Hyderabad".equalsIgnoreCase(e.getLocation()) && Gender.FEMALE.equals(e.getGender()) && e.getName().startsWith("R"));
		System.out.println("Average Salary: "+ averageSalary);

		//Case 2
		Map<String, List<Person>> byLocation = PersonUtility.groupByLocation(personList);
		System.out.println("Location wise seggregation: "+byLocation);
	}

}
