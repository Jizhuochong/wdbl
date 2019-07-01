package com.capinfo.security.tool;

import java.io.IOException;
import java.lang.reflect.Type;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.SerializerBase;

import com.capinfo.security.bean.Menu;

public class MenuToStringSerializer extends SerializerBase<Menu> {

	
	public MenuToStringSerializer() {
		 super(Menu.class);
	}
	protected MenuToStringSerializer(Class<?> t, boolean dummy) {
		super(t, dummy);
	}

	@Override
	public void serialize(Menu menu, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonGenerationException {
		jgen.writeString(menu.getMenuId().toString());
		
	}

}
