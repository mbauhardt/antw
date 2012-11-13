package antw.multiproject.dsl;

import java.util.List;


import org.junit.Assert;
import org.junit.Test;

public class SettingsTest {

	@Test
	public void testSettings() throws Exception {
		Settings settings = Settings.parse(this.getClass().getResourceAsStream(
				"/Settings.antw"));
		List<Module> modules = settings.getModules();
		Assert.assertTrue(modules.size()==2);
		Assert.assertEquals("foo", modules.get(0).getName());
		Assert.assertEquals("bar", modules.get(1).getName());
	}
}
