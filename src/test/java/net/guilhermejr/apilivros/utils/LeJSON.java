package net.guilhermejr.apilivros.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.springframework.util.StreamUtils;

public class LeJSON {
	
	public static String conteudo(String resourceName) {
		try {
			InputStream stream = LeJSON.class.getResourceAsStream(resourceName);
			return StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
