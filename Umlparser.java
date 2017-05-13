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
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.io.File;

import org.w3c.dom.NodeList;

public class Umlparser {
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
	static ArrayList<String> intf = new ArrayList<String>();
	static StringBuffer trying = new StringBuffer();
	static ArrayList<String> getvar = new ArrayList<String>();
	static ArrayList<String> storevar = new ArrayList<String>();
	static ArrayList<String> constructor = new ArrayList<String>();
	static CompilationUnit cu;
	static String[] classname;
	static String[] inter;
	static PlantUmlGenerator plant = new PlantUmlGenerator();
	static String input;
	static String output;

	public static void main(String[] args) throws Exception {
		// creates an input stream for the file to be parsed

	String input = "C:/Users/DES/Desktop/SJSU/202/UMLParser/testcase/test5";
		//input = args[0];
		//output = args[1]; 
	output = "C:/Users/DES/Desktop/SJSU/202/UMLParser/testcase/test5/5.svg";
		File file = new File(input);
		Storing(file);
		Parsing(file);/**/
		plant.createUML(File1, output);
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
			String[] v = va[0].split(" ");
			if (va[1].contains("Collection")) {
				continue;
			}
			if (store.contains(va[1])) {
				continue;
			}
			File1.add(s);

		}
		
		for (String s : methodName) {
			File1.add(s);
          
		}
		for (String s : constructor) {
			File1.add(s);

		}

		File1.add("}\n");
		for (String s : Classes) {
			
			File1.add(s);

		}

		for (String s : Interfaces) {
			
			File1.add(s);

		}

		varName.clear();
		methodName.clear();
		Classes.clear();
		Interfaces.clear();
		constructor.clear();

	}

	private static void Parsing(File file) {
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
		
		for (String s : interdependency) {
			// System.out.println(s);
			String[] s1 = s.split(" ");
		//	System.out.println(intf);
			if (intf.contains(s1[0]) == false && intf.contains(s1[2])) {
				File1.add(s);
			}
			// if(intf.contains(as[0].toString()) == false &&
			// intf.contains(as[3].toString()))

		}
		File1.add("@enduml");
	}

	private static void Storing(File file) {
		//File file = new File(input);
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

		// System.out.println(types);
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
					if (((ClassOrInterfaceDeclaration) type).getExtends() != null) {
						classtype = ((ClassOrInterfaceDeclaration) type)
								.getExtends();
						for (int i = 0; i < classtype.size(); i++) {
							String text = classtype.get(i).toString()
									.replace("[", "").replace("]", "");
							Classes.add((sb.append(text + "<|--" + classname[0]
									+ "\n")).toString());
							sb.setLength(0);
							// System.out.println(text + " <|.. " + parts[0]);
						}
						// System.out.println();
					}
				}
			}

		}
		// System.out.println(intf);
		return Classes;
	}

	// }
	// }

	// extract access specifier name
	private static void GetmodDetails(CompilationUnit cu) {
		List<TypeDeclaration> types = cu.getTypes();
		for (TypeDeclaration type : types) {
			if (type instanceof ClassOrInterfaceDeclaration) {
				int i = type.getModifiers();
				String s = Modifier.toString(i);
			
			}
		}
	}

	private static void GetmemberDetails(CompilationUnit cu) {
		List<TypeDeclaration> types = cu.getTypes();
		ArrayList<String> Var = new ArrayList<String>();
		String rev = null;
		int flag = 0;
		// System.out.println(getvar);
		StringBuffer sb = new StringBuffer();
		for (TypeDeclaration type : types) {
			List<BodyDeclaration> members = type.getMembers();
			for (BodyDeclaration member : members) {
				if (member instanceof FieldDeclaration) {
					String st = (((FieldDeclaration) member).getType()
							.toString());

					storevar.add(((FieldDeclaration) member).getVariables()
							.toString());
					Var.add(st);

					int a = ((FieldDeclaration) member).getModifiers();
					if (a == 1) {

						sb.append("+ ");
						for (String s : store) {
							if (Associate.contains(st)) {
								break;
							}
							if (s.equals(st)) {
								String str = classname[0] + "--" + s;
								 rev = s + "--" + classname[0];
								if (duplicate.contains(rev) == false
										&& duplicate.contains(str) == false) {
									Associate.add(classname[0] + "--" + s);
									duplicate.add(classname[0] + "--" + s);
								}
								// reverse.add(s + "--" + classname[0]);
							//	if (Associate.contains(s + "--" + classname[0])) {
							//		Associate.remove(s + "--" + classname[0]);
									// System.out.println("removed!!!");
								//}

							} else if (st.contains("<" + s + ">")) {
                                String str = classname[0] + "-- " + "\"*\""
										+ s;
								rev = 	s + "-- " + "\"*\"" + classname[0];
								if (duplicate.contains(rev) == false
										&& duplicate.contains(str) == false)
								Associate.add(classname[0] + "-- " + "\"*\""
										+ s);
                                duplicate.add(classname[0] + "--" + s);
                                duplicate.add(classname[0] + "-- " + "\"*\""
										+ s);
                                flag = 1;
        
                                if(flag ==1 && duplicate.contains(rev)){
                                	Associate.remove(rev);
                                	Associate.remove(str);
                                	Associate.add(classname[0] + "\"*\""+"-- " + "\"*\""
    										+ s);
                                	duplicate.add(classname[0] + "\"*\""+"-- " + "\"*\""
    										+ s);
                                	flag=2;
                                	if(flag == 2 && duplicate.contains(s+ "\"*\""+"-- " + "\"*\""
    										+ classname[0]))
                                	{
                                		//System.out.println("aashi");
                                		Associate.remove(s+ "\"*\""+"-- " + "\"*\""
        										+ classname[0]);
                                	}
                                	
                                }
								

							}

						}
					}
					

					else	if (a == 2) {
						// System.out.println("private Variable");
						sb.append("- ");
						for (String s : store) {
							if (Associate.contains(st)) {
								break;
							}
							if (s.equals(st)) {
								String str = classname[0] + "--" + s;
								 rev = s + "--" + classname[0];
								if (duplicate.contains(rev) == false
										&& duplicate.contains(str) == false) {
									Associate.add(classname[0] + "--" + s);
									duplicate.add(classname[0] + "--" + s);
								}
								
							} else if (st.contains("<" + s + ">")) {
                                String str = classname[0] + "-- " + "\"*\""
										+ s;
								rev = 	s + "-- " + "\"*\"" + classname[0];
								if (duplicate.contains(rev) == false
										&& duplicate.contains(str) == false)
								Associate.add(classname[0] + "-- " + "\"*\""
										+ s);
                                duplicate.add(classname[0] + "--" + s);
                                duplicate.add(classname[0] + "-- " + "\"*\""
										+ s);
                                flag = 1;
        
                                if(flag ==1 && duplicate.contains(rev)){
                                	Associate.remove(rev);
                                	Associate.remove(str);
                                	Associate.add(classname[0] + "\"*\""+"-- " + "\"*\""
    										+ s);
                                	duplicate.add(classname[0] + "\"*\""+"-- " + "\"*\""
    										+ s);
                                	flag=2;
                                	if(flag == 2 && duplicate.contains(s+ "\"*\""+"-- " + "\"*\""
    										+ classname[0]))
                                	{
                                		//System.out.println("aashi");
                                		Associate.remove(s+ "\"*\""+"-- " + "\"*\""
        										+ classname[0]);
                                	}
                                	
                                }
								// reverse.add(s+"-- " + "\"*\"" +classname[0]);
							//	if (Associate.contains(s + "--" + classname[0])) {
								//	Associate.remove(s + "--" + classname[0]);
									// System.out.println("removed!!!");
							//	}

							}

						}
					} else {
						if (store.contains(st)) {
							Associate.add(classname[0] + "--" + st);
						}
						continue;
					}
					sb.append(
							((FieldDeclaration) member).getVariables().get(0)
									.toString()).append(" : ");
					sb.append(((FieldDeclaration) member).getType());
					varName.add(sb.toString());
					
					sb.setLength(0);
				}

			}
			
		}
		
	}

	// return Variables;

	private static void GetMethodDetails(CompilationUnit cu) {
		List<TypeDeclaration> types = cu.getTypes();
		ArrayList<String> methods = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		for (TypeDeclaration type : types) {
			List<BodyDeclaration> members = type.getMembers();
			for (BodyDeclaration member : members) {
				if (member instanceof MethodDeclaration) {
					int flag = 0;
					int m = ((MethodDeclaration) member).getModifiers();
					String mname = ((MethodDeclaration) member).getName()
							.toString();
					int sy = ((MethodDeclaration) member).getModifiers();
					for (String f : storevar) {
						if (mname.equalsIgnoreCase("set"
								+ f.substring(1, f.length() - 1))
								|| mname.equalsIgnoreCase("get"
										+ f.substring(1, f.length() - 1))) {
							flag = 1;
							getvar.add(f);
							
							for(int n = 0;n<=varName.size()-1;n++){
								String[] spilt = varName.get(n).split(" ");
								if(mname.substring(3).equalsIgnoreCase(spilt[1])){
								String temp = varName.get(n).substring(1);
								String merge = "+" + temp;
								varName.set(n, merge);
							}}
							
							
							
						}
					}
					if (((MethodDeclaration) member).getBody() != null) {
						if (((MethodDeclaration) member).getBody().getStmts() != null) {
							for (int i = 0; i < ((MethodDeclaration) member)
									.getBody().getStmts().size(); i++) {

								String state = ((MethodDeclaration) member)
										.getBody().getStmts().get(i).toString();
								if (state.contains("=new")
										|| state.contains("= new")) {

									String[] n = state.split("=");

									for (String c : store) {

										if (n[0].toString().contains(c)) {

											interdependency.add(classname[0]
													+ " ..> " + c);

										}
									}

								}
							}
						}
					}
					// ++++++++++++++++++++++
					if (((MethodDeclaration) member).getParameters() != null)// for
																				// interface
																				// object
					{
						// System.out.println(classname[0]);
						for (int i = 0; i < ((MethodDeclaration) member)
								.getParameters().size(); i++) {
							String paramtype = ((MethodDeclaration) member)
									.getParameters().get(i).getType()
									.toString();
							if (store.contains(paramtype)) {
								String def = classname[0] + " ..> " + paramtype;
								if (interdependency.contains(def) == false)
									interdependency.add(def);
							}
						}
					}
					if (m == 9
							&& ((MethodDeclaration) member).getParameters() != null
							&& flag != 1) // for static methods
					{

						sb.append("+ " + "{static}");
						sb.append(((MethodDeclaration) member).getName())
								.append("(");
						for (int i = 0; i < ((MethodDeclaration) member)
								.getParameters().size(); i++) {
							sb.append(
									((MethodDeclaration) member)
											.getParameters().get(i).getId())
									.append(":");
							sb.append(((MethodDeclaration) member)
									.getParameters().get(i).getType());

						}
						sb.append(")");
						sb.append(":");
						sb.append(((MethodDeclaration) member).getType());
						methodName.add(sb.toString());
						sb.setLength(0);
					}

					else if (m == 1
							&& ((MethodDeclaration) member).getParameters() != null
							&& flag != 1) // methods with parameters
					{
						String cls = ((MethodDeclaration) member)
								.getParameters().toString();
						sb.append("+ ");
						sb.append(((MethodDeclaration) member).getName())
								.append("(");
						for (int i = 0; i < ((MethodDeclaration) member)
								.getParameters().size(); i++) {
							sb.append(
									((MethodDeclaration) member)
											.getParameters().get(i).getId())
									.append(":");
							sb.append(((MethodDeclaration) member)
									.getParameters().get(i).getType());

						}

						sb.append(")");
						sb.append(":");
						sb.append(((MethodDeclaration) member).getType());
						methodName.add(sb.toString());
					//	System.out.println(methodName);
						sb.setLength(0);
					} else if ((flag != 1) && (m == 1) || (m == 1025))// else of
																		// 1st
																		// if if
																		// parameter
																		// not
																		// equal
																		// to
																		// null
					{
						// System.out.println("entered in other part");
						sb.append("+ ");
						sb.append(((MethodDeclaration) member).getName())
								.append("(");
						sb.append(")");
						sb.append(":");
						sb.append(((MethodDeclaration) member).getType());
						// System.out.println(((MethodDeclaration) member)
						// .getType());
						flag = 1;
						methodName.add(sb.toString());
						sb.setLength(0);
					}
				}
			}
		}
	}

	private static void GetConstructorDetails(CompilationUnit cu) {
		List<TypeDeclaration> types = cu.getTypes();
		StringBuffer sb = new StringBuffer();
		for (TypeDeclaration type : types) {
			List<BodyDeclaration> members = type.getMembers();
			for (BodyDeclaration member : members) {
				if (member instanceof ConstructorDeclaration) {
					int c = ((ConstructorDeclaration) member).getModifiers();
					String cname = ((ConstructorDeclaration) member).getName()
							.toString();

					if (c == 1
							&& ((ConstructorDeclaration) member)
									.getParameters() != null) {

						sb.append("+ " + cname + "(");
						for (int i = 0; i < ((ConstructorDeclaration) member)
								.getParameters().size(); i++) {

							String con = ((ConstructorDeclaration) member)
									.getParameters().get(i).getType()
									.toString();
							if (store.contains(con)) {
								interdependency.add(classname[0] + " ..> " + con);
							}
							sb.append(
									((ConstructorDeclaration) member)
											.getParameters().get(i).getId())
									.append(":");
							sb.append(((ConstructorDeclaration) member)
									.getParameters().get(i).getType()
									+ " ");

						}
						sb.append(")");
						constructor.add(sb.toString());
						sb.setLength(0);

					} else {
						sb.append("+ ");
						sb.append(((ConstructorDeclaration) member).getName())
								.append("(");
						sb.append(")");
						constructor.add(sb.toString());

					}
				}
			}
		}
	}
}
// return methods;

