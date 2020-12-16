package com.vikash.code.challenge;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class PersonUtilityTest {

	@Test
	public void testPrepareData() {
		assertFalse(PersonUtility.prepareTestData().isEmpty());
	}
	
	@Test
	public void testGetAvegargeSalaryPostFilter() {
		assertTrue(31500.0d == PersonUtility.getAvegargeSalaryPostFilter(PersonUtility.prepareTestData()
				, e -> e.getAge()>25 && e.getSalary()>25000 && "Hyderabad".equalsIgnoreCase(e.getLocation()) && Gender.FEMALE.equals(e.getGender()) && e.getName().startsWith("R")));
	}
	
	@Test
	public void testGroupByLocation() {
		assertNotNull(PersonUtility.groupByLocation(PersonUtility.prepareTestData()));
	}
}
