package com.vikash.code.challenge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class PersonTest {

	private Person person;
	
	@Before
	public void init() {
		person = new Person("Rahim", 23, 34000, "Hyderabad", Gender.MALE);
	}
	
	@After
	public void destroy() {
		person = null;
	}
	
	@Test
	public void testName() {
		assertEquals("Rahim", person.getName());
	}
	
	@Test
	public void testAge() {
		assertTrue(23 == person.getAge());
	}
	
	@Test
	public void testSalary() {
		assertTrue(34000 == person.getSalary());
	}
	
	@Test
	public void testLocation() {
		assertEquals("Hyderabad", person.getLocation());
	}
	
	@Test
	public void testGender() {
		assertEquals(Gender.MALE, person.getGender());
	}
	
	@Test
	public void testToString() {
		assertNotNull(person.toString());
	}
}
