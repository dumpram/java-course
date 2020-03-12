package hr.fer.zemris.java.hw13.test;

import static org.junit.Assert.assertNull;
import hr.fer.zemris.java.hw13.servlets.BandInfo;

import org.junit.Test;

public class DummyTest {

	@Test
	public void DummyAtTest() {
		BandInfo band = new BandInfo(0, null, 0, null);
		assertNull(band.getBandName());
	}

}
