package antw.multiproject;

public class Main extends org.apache.tools.ant.Main {

	public static void main(String[] args) {
		handleArgs(args);
		String[] antwArgs = new String[args.length + 2];
		System.arraycopy(args, 0, antwArgs, 0, args.length);
		antwArgs[antwArgs.length - 2] = "-f";
		antwArgs[antwArgs.length - 1] = "build.xml";
		start(args, null, null);
	}

	private static void handleArgs(String[] args) {
		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			if (arg.equals("-buildfile") || arg.equals("-file")
					|| arg.equals("-f")) {
				handleBuildFileArg(arg, i);
			}
		}
	}

	private static void handleBuildFileArg(String arg, int i) {
		throw new IllegalArgumentException("-f argument at the moment reserved");
	}
}
