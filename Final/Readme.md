Tools and Libraries used for JAVA Parser:
1.	Javaparser-1.0.8.jar is used to parse Java source and that will be converted into AST. Javaparser-1.0.8.jar  “parser.ast.body.* “ and it includes classes like TypeDeclaration, BodyDeclaration, MethodDeclaration to parse java code and generates intermediate code.
2.	Plantuml.jar is used to generate final Class Diagram.
3.	graphviz.msi is installed that is used to generate and view the class diagram image generated in the above step.
4.	Eclipse Kepler used as JAVA IDE for creating parser.
Steps to Run code
1. Open command prompt.
2. Go to path where the <class>.jar is located.
3. To run jar file: java -jar <classjarfile><testcases_sourcefolder_path>< output_path /image.svg>
Ex- java -jar Umlparser.jar C:\Users\DES\Desktop\SJSU\202\UMLParser\testcase\test4 C:\Users\DES\Desktop\SJSU\202\UMLParser\testcase\test4\image4.svg


