import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.body.TypeDeclaration;
import japa.parser.ast.type.ClassOrInterfaceType;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.io.File;

import org.w3c.dom.NodeList;

public class MethodChanger {
	static ArrayList<String> class_det = new ArrayList<String>();
	static ArrayList<String> Classes = new ArrayList<String>();
	static ArrayList<String> Interfaces = new ArrayList<String>();
	static ArrayList<String> p_var = new ArrayList<String>();
	static ArrayList<String> methodName = new ArrayList<String>();
	static ArrayList<String> varName = new ArrayList<String>();
	static ArrayList<String> File1 = new ArrayList<String>();
	static ArrayList<String> store = new ArrayList<String>();
	static ArrayList<String> Associate = new ArrayList<String>();
	static ArrayList<String> duplicate = new ArrayList<String>();
	static ArrayList<String> interdependency = new ArrayList<String>();
	static ArrayList<String> constructassociate = new ArrayList<String>();
	static ArrayList<String> intf = new ArrayList<String>();
	static StringBuffer trying = new StringBuffer();
	static StringBuffer getvar = new StringBuffer();
	static ArrayList<String> storevar = new ArrayList<String>();
	static ArrayList<String> constructor = new ArrayList<String>();
	static CompilationUnit cu;
	static String[] classname;
	static String[] inter;
	static PlantUmlGenerator plant = new PlantUmlGenerator();

	public static void main(String[] args) throws Exception {
		// creates an input stream for the file to be parsed

		String input = "C:/Users/DES/Desktop/SJSU/202/Personal project/test_cases/test3";
		Storing(input);
		Parsing(input);/**/
		plant.createUML(File1);
		for (String s : File1) {
			System.out.println(s);
		}

	}

	private static void File() {
		File1.add("Class " + classname[0] + "{\n");

		// System.out.println(Classes);
		for (String s : varName) {
			// System.out.println(s);
			String[] va = s.split(": ");
			if (va[1].contains("Collection")) {
				continue;
			}
			if (store.contains(va[1])) {
				continue;
			}
			File1.add(s);

		}
		// uml.append("\n");
		for (String s : methodName) {
			// System.out.println(s);
			File1.add(s);

		}
		for (String s : constructor) {
			File1.add(s);

		}

		File1.add("}\n");
		for (String s : Classes) {
			// System.out.println(s);
			File1.add(s);

		}

		for (String s : Interfaces) {
			// System.out.println(s);
			File1.add(s);

		}

		varName.clear();
		methodName.clear();
		Classes.clear();
		Interfaces.clear();
		constructor.clear();

	}

	private static void Parsing(String input) {
		File file = new File(input);
		File1.add("@startuml\n");
		File[] files = file.listFiles();

		for (File f : files) {
			System.out.println(f);
		}
		System.out.println("***********************");
		for (File f : files) {
			if (f.getName().contains(".java")) {
				classname = f.getName().split("\\.");
				System.out.println(f.getName());
			try {
					cu = JavaParser.parse(f);
				//	System.out.println(cu);
					JavaParser.setCacheParser(false);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Getclass(cu);
				// GetmodDetails(cu);
				GetmemberDetails(cu);
				GetMethodDetails(cu);
				File();}}

	// extracting classname
		for (String s : Associate) {
			// System.out.println(s);

			File1.add(s);

		}
		for (String s : interdependency) {
			// System.out.println(s);

			File1.add(s);

		}
		File1.add("@enduml");
	}
	private static void File() {
		File1.add("Class " + classname[0] + "{\n");
	}private static void Parsing(String input) {
		File file = new File(input);
		File1.add("@startuml\n");
		File[] files = file.listFiles();

		for (File f : files) {
		}
		// System.out.println("***********************");
		for (File f : files) {
			if (f.getName().contains(".java")) {
				classname = f.getName().split("\\.");
				// System.out.println(f.getName());
				try {
					cu = JavaParser.parse(f);
					// System.out.println(cu);
					JavaParser.setCacheParser(false);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Getclass(cu);
				// GetmodDetails(cu);
				GetmemberDetails(cu);
				GetMethodDetails(cu);
				GetConstructorDetails(cu);
				File();

				// varName = (ArrayList<String>) GetmemberDetails(cu);
				// methodName = (ArrayList<String>) GetMethodDetails(cu);
			}

		}
		for (String s : Associate) {
			// System.out.println(s);

			File1.add(s);

		}
		for (String s : constructassociate) {
			// System.out.println(s);

			File1.add(s);

		}
		for (String s : interdependency) {
			// System.out.println(s);
			String[] s1 = s.split(" ");
			if (intf.contains(s1[0]) == false && intf.contains(s1[2])) {
				File1.add(s);
			}
			// if(intf.contains(as[0].toString()) == false &&
			// intf.contains(as[3].toString()))

		}
		File1.add("@enduml");
	}

	private static void Storing(String input) {
		File file = new File(input);
		File[] files = file.listFiles();
		// System.out.println("***********************");
		for (File f : files) {
			if (f.getName().contains(".java")) {
				classname = f.getName().split("\\.");
				String st = classname[0].toString();
				// System.out.println(st);
				store.add(st);
			}
		}

	}

	// extracting classname
	private static List Getclass(CompilationUnit cu) {
	List<ClassOrInterfaceType> classtype;
		List<TypeDeclaration> types = cu.getTypes();
		StringBuffer sb = new StringBuffer();
		for (TypeDeclaration type : types) {
			// System.out.println(type);
			if (type instanceof ClassOrInterfaceDeclaration) {
				if (((ClassOrInterfaceDeclaration) type).isInterface()) {
					// System.out.println("this is interface");
					sb.append("interface ");
					sb.append(((ClassOrInterfaceDeclaration) type).getName());
					Interfaces.add(sb.toString());
					if (intf.contains(((ClassOrInterfaceDeclaration) type)
							.getName()) == false) {
						intf.add(((ClassOrInterfaceDeclaration) type).getName());
					}

					sb.setLength(0);

					break;
				} else {
					if (((ClassOrInterfaceDeclaration) type).getImplements() != null) {
						classtype = ((ClassOrInterfaceDeclaration) type)
								.getImplements();
						for (int i = 0; i < classtype.size(); i++) {
							String text = classtype.get(i).toString()
									.replace("[", "").replace("]", "");
							Classes.add((sb.append(text + "<|.. "
									+ classname[0] + "\n")).toString());
							sb.setLength(0);
						}
			}
		}
	}

	// extract access specifier name
	private static void GetmodDetails(CompilationUnit cu) {
		List<Store> MStore = new ArrayList<MStore>();
		MStore st = new MStore();
		System.out.println(st);
		List<TypeDeclaration> types = cu.getTypes();
		for (TypeDeclaration type : types) {
			if (type instanceof ClassOrInterfaceDeclaration) {
				int i = type.getModifiers();
				String s = Modifier.toString(i);
				System.out.println("this is class Access specifier: " + s);
			}
		}
	}

	private static void GetmemberDetails(CompilationUnit cu) {
		List<Store> MemStore = new ArrayList<MemStore>();
		MemStore st = new MemStore();
		List<TypeDeclaration> types = cu.getTypes();
		for (TypeDeclaration type : types) {
			List<BodyDeclaration> members = type.getMembers();
			for (BodyDeclaration member : members) {
				if (member instanceof FieldDeclaration) {
				String st = ((FieldDeclaration) member).getVariables().toString();
                    System.out.println(st);
					System.out.println(((FieldDeclaration) member)
							.getModifiers());
					int a = ((FieldDeclaration) member).getModifiers();
					if (a == 1) {
						System.out.println("public variable");
					}
					if (a == 2) {
						System.out.println("private Variable");
					}
				}
			
			}
        
		}
	}

	private static void GetMethodDetails(CompilationUnit cu) {
		List<TypeDeclaration> types = cu.getTypes();
		for (TypeDeclaration type : types) {
			List<BodyDeclaration> members = type.getMembers();
			for (BodyDeclaration member : members) {
				if (member instanceof MethodDeclaration) {
					System.out.println(((MethodDeclaration) member).getName());
					System.out.println(((MethodDeclaration) member).getType());
					int m =((MethodDeclaration) member)
							.getModifiers();
					if (m == 1) {
						System.out.println("public variable");
					}
					if (m == 2) {
						System.out.println("private Variable");
					}
				}
			}
		}
	}
}
