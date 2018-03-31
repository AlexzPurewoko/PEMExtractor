import java.util.*;
import java.io.File;
import java.io.IOException;

public class Main
{
	public static void main(String[] args)
	{
		File outDir = new File("/sdcard/Android/PermOut");
		outDir.mkdirs();
		File pem = new File("/sdcard/Download", "cacert.pem");
		PEMExtractor ex = new PEMExtractor(pem, outDir);
		try
		{
			ex.extract();
		}
		catch (IOException e)
		{}
	}
}
