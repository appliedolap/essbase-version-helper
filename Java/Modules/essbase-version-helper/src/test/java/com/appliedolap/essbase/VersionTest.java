package com.appliedolap.essbase;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class VersionTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testToString() {
		assertEquals("1.2.3", Version.of("1.2.3").toString());
		assertEquals("1.2", Version.of("1.2").toString());
		assertEquals("1", Version.of("1").toString());
	}

}
