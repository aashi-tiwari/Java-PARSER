import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.GeneratedImage;
import net.sourceforge.plantuml.SourceStringReader;

public class PlantUmlGenerator {
	 String sourcefile;
	 int flag = 0;
	public void createUML(ArrayList<String> file1, String output) {
		for(String file : file1){
			if(flag == 0){
				sourcefile = file + "\n";
				flag = 1;
			}else{
				sourcefile = sourcefile + file + "\n";
			}
		}
		SourceStringReader read = new SourceStringReader(sourcefile);
		try {
			FileOutputStream classDiagram = new FileOutputStream(new File(output));
		    try {
				read.generateImage(classDiagram, new FileFormatOption(FileFormat.SVG, false));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
