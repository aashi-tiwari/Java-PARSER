import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.TypeDeclaration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream.GetField;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import org.w3c.dom.NodeList;

public class MethodChanger {   
	static ArrayList<String> class_det = new ArrayList<String>();
	static ArrayList<String> Classes = new ArrayList<String>();
	static ArrayList<String> p_var = new ArrayList<String>();
	static ArrayList<String> methodName = new ArrayList<String>();
	static ArrayList<String> varName = new ArrayList<String>();
	static ArrayList<String> File1 = new ArrayList<String>();
	static StringBuffer uml = new StringBuffer();
	public static void main(String[] args) throws Exception {
		// creates an input stream for the file to be parsed
		FileInputStream in = new FileInputStream(
				"C:\\Users\\DES\\Desktop\\SJSU SE\\202\\Personal project\\umlparser\\A.java");

		// parse the file
		CompilationUnit cu = JavaParser.parse(in);

		// change the methods names and parameters

		// prints the changed compilation unit
		System.out.println(cu.toString());
		Getclass(cu);
		GetmodDetails(cu);
		GetmemberDetails(cu);
		GetMethodDetails(cu);
	}

	// extracting classname
	private static void File() {
		File1.add("Class " + classname[0] + "{\n");
	}
	private static void Getclass(CompilationUnit cu) {
		List<Store> Store = new ArrayList<Store>();
		Store st = new Store();
		Store.add(st);
		System.out.println(Store);
		List<TypeDeclaration> types = cu.getTypes();
		System.out.println(types);
		for (TypeDeclaration type : types) {
			System.out.println(type);
			if (type instanceof ClassOrInterfaceDeclaration) {
				if (((ClassOrInterfaceDeclaration) type).isInterface()) {
					System.out.println("this is interface");
					break;
				} else {
					System.out.println("this is class");        
					System.out.println(type.getName());

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
