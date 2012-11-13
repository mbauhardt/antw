package antw.multiproject.dsl;

import java.util.List;


import org.junit.Assert;
import org.junit.Test;

public class SettingsTest {

	@Test
	public void testSettings_modules() throws Exception {
		Settings settings = Settings.parse(this.getClass().getResourceAsStream(
				"/Settings.antw"));
		List<Module> modules = settings.getModules();
		Assert.assertTrue(modules.size()==2);
		Assert.assertEquals("foo", modules.get(0).getName());
		Assert.assertEquals("bar", modules.get(1).getName());
	}
	
	@Test
	public void testSettings_imports() throws Exception {
		Settings settings = Settings.parse(this.getClass().getResourceAsStream(
				"/Settings.antw"));
		 List<Import> imports = settings.getImports();
		Assert.assertTrue(imports.size()==2);
		Assert.assertEquals("/tmp/build_1.xml", imports.get(0).getPath());
		Assert.assertEquals("/tmp/build_2.xml", imports.get(1).getPath());
	}

}
