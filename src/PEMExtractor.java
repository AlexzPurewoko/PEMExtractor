
import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
public class PEMExtractor
{
	private static final String BEGIN_CERTIFICATES = "-----BEGIN CERTIFICATE-----",
	END_CERTIFICATES   = "-----END CERTIFICATE-----";
	File pem, outDir;
	public PEMExtractor(File pem, File outDir){
		this.pem = pem;
		this.outDir = outDir;
	}
	public void extract() throws FileNotFoundException, IOException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(pem)));
		String tmp;
		StringBuilder mb = new StringBuilder();
		String nameFile = null;
		File outTo;
		FileOutputStream fout;
		int mode = 0;
		while((tmp = reader.readLine()) != null){
			if(tmp.length() == 0)continue;
			if( tmp.charAt(0) == '#' && tmp.charAt(1) == '#')continue;
			if(mode == 0){
				if(tmp.charAt(0) == '\n')continue;
				else if(tmp.charAt(0) == '=' && tmp.charAt(1) == '=')continue;
				else if(tmp.contains(BEGIN_CERTIFICATES)){
					mode++;
					if(mb.length() != 0)mb.delete(0, mb.length());
					mb.append(tmp);
					mb.append('\n');
					continue;
				}
				else{
					nameFile = tmp;
					System.out.println(nameFile);
				}
			}
			else {
				if(tmp.contains(END_CERTIFICATES)){
					mode = 0;
					mb.append('\n');
					mb.append(tmp);
					nameFile+=".crt";
					outTo = new File(outDir, nameFile);
					fout = new FileOutputStream(outTo);
					fout.write(mb.toString().getBytes());
					fout.close();
					outTo = null;
					fout = null;
					continue;
				}
				else mb.append(tmp);
			}
		}
		
	}
}
