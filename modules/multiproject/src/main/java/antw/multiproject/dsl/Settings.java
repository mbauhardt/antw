package antw.multiproject.dsl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenRewriteStream;
import org.antlr.runtime.tree.CommonTree;

import antw.multiproject.dsl.SettingsParser.settings_return;

public class Settings {

	public static Settings parse(InputStream inputStream) throws IOException,
			RecognitionException {
		CharStream input = new ANTLRInputStream(inputStream);
		SettingsLexer lexer = new SettingsLexer(input);
		TokenRewriteStream tokens = new TokenRewriteStream(lexer);
		tokens.setTokenSource(lexer);
		SettingsParser parser = new SettingsParser(tokens);
		settings_return settingsReturn = parser.settings();
		if (parser.getNumberOfSyntaxErrors() > 0) {
			throw new IllegalArgumentException("settings file has sytax errors");
		}
		CommonTree root = (CommonTree) settingsReturn.getTree();
		return buildSettings(root);
	}

	private static Settings buildSettings(CommonTree tree) {
		Settings settings = new Settings();
		buildSettings(tree, settings);
		return settings;
	}

	@SuppressWarnings("unchecked")
	private static void buildSettings(CommonTree tree, Settings settings) {
		// System.out.println(tree.getType()+":"+tree.getText());
		if (tree.getType() == SettingsLexer.MODULENAME) {
			settings.addModule(new Module(tree.getChild(0).getText()));
		} else if (tree.getType() == SettingsLexer.IMPORTPATH) {
			settings.addImport(new Import(tree.getChild(0).getText()));
		}
		List<CommonTree> childrens = tree.getChildren();
		if (childrens == null) {
			return;
		}
		for (CommonTree commonTree : childrens) {
			buildSettings(commonTree, settings);
		}
	}

	private List<Module> _modules = new ArrayList<Module>();
	private List<Import> _imports = new ArrayList<Import>();

	private void addModule(Module module) {
		_modules.add(module);
	}

	public void addImport(Import importFile) {
		_imports.add(importFile);
	}

	public List<Module> getModules() {
		return _modules;
	}
	
	public List<Import> getImports() {
		return _imports;
	}

}
