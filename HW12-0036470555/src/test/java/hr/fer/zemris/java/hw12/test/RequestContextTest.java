package hr.fer.zemris.java.hw12.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class RequestContextTest {

	@Test(expected = IllegalArgumentException.class)
	public void constructorSveJeNullTest() {
		@SuppressWarnings("unused")
		RequestContext sveJeNull = new RequestContext(null, null, null, null);
	}
	@Test
	public void constructorSveJeNullOsimOutputStreamaTest() {
		RequestContext sveJeNull = new RequestContext(System.out, null, null, null);
		assertTrue(sveJeNull.getParameterNames().isEmpty());
		assertTrue(sveJeNull.getPersistentParameterNames().isEmpty());
		assertTrue(sveJeNull.getTemporaryParameterNames().isEmpty());

	}
	@Test(expected = RuntimeException.class)
	public void runtimeExceptionWhenTryToChangeMimeTypeAfterHeaderIsObtainedTest() throws IOException {
		RequestContext newOne = new RequestContext(System.out, null, null, null);
		newOne.setMimeType("image/jpg");
		newOne.setStatusText("very good");
		newOne.setStatusCode(333);
		newOne.setEncoding("UTF-8");
		newOne.setContentLength(123);
		newOne.write("Plain");
		newOne.setMimeType("text/html");
	}

	@Test
	public void setParametersTest() {
		RequestContext newOne = new RequestContext(System.out, null, null, null);
		newOne.setPersistentParameter("ivo", "2");
		newOne.setTemporaryParameter("kevin", "4");
		assertEquals(newOne.getPersistentParameter("ivo"), "2");
		assertEquals(newOne.getTemporaryParameter("kevin"), "4");
	}

	@Test
	public void addCookiesTest() throws IOException {
		RequestContext newOne = new RequestContext(System.out, new HashMap<String, String>(),
				new HashMap<String, String>(), new ArrayList<RCCookie>());
		RCCookie ivan = new RCCookie("ivan", "pavic", 90, "earth", "olala");
		newOne.addRCCookie(ivan);
		newOne.write("Testiranje headera s kukijima");
		assertEquals("ivan=\"pavic\"; Domain=earth; Path=olala; Max-Age=90", ivan.toString());
		assertEquals("ivan", ivan.getName());
		assertEquals("pavic", ivan.getValue());
		assertEquals("earth", ivan.getDomain());
		assertEquals("olala", ivan.getPath());
		assertEquals(90, ivan.getMaxAge());
	}

	@Test
	public void addCookiesNullTest() throws IOException {
		RequestContext newOne = new RequestContext(System.out, new HashMap<String, String>(),
				new HashMap<String, String>(), new ArrayList<RCCookie>());
		RCCookie ivan = new RCCookie("ivan", "pavic", null, null, null);
		newOne.addRCCookie(ivan);
		newOne.write("Testiranje headera s kukijima");
		newOne.write("nanovo");
		assertEquals("ivan=\"pavic\"", ivan.toString());
	}

	@Test
	public void removeParametersTest() {
		RequestContext newOne = new RequestContext(System.out, null, null, null);
		newOne.setPersistentParameter("ivo", "2");
		newOne.setTemporaryParameter("kevin", "4");
		newOne.removePersistentParameter("ivo");
		newOne.removeTemporaryParameter("kevin");
		assertTrue(newOne.getPersistentParameterNames().isEmpty());
		assertTrue(newOne.getTemporaryParameterNames().isEmpty());
	}

	@Test
	public void getParametersTest() {
		Map<String, String> parameters = new HashMap<>();
		parameters.put("ivo", "kabanac");
		RequestContext newOne = new RequestContext(System.out, parameters, null, null);
		assertEquals("kabanac", newOne.getParameter("ivo"));
	}
	@Test
	public void getCharsetTest() throws IOException {
		RequestContext newOne = new RequestContext(System.out, null, null, null);
		assertNull(newOne.getCharset());
		newOne.write("Testiranje charseta");
		assertEquals(StandardCharsets.UTF_8, newOne.getCharset());
	}
	@Test
	public void httpOnlyCookieTest() throws IOException {
		RequestContext newOne = new RequestContext(System.out, new HashMap<String, String>(),
				new HashMap<String, String>(), new ArrayList<RCCookie>());
		RCCookie ivan = new RCCookie("ivan", "pavic", null, null, null);
		ivan.setHttpOnly(true);
		newOne.addRCCookie(ivan);
		newOne.write("hello");
		assertTrue(ivan.toString().endsWith("HttpOnly"));
	}

}
