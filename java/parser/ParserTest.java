package parser;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.comments.BlockComment;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.printer.YamlPrinter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

public class ParserTest {

	private static final String BLANK = "";

  public static void main(String[] args) {
	  File file = new File("E:\\IdeaProgram\\JavaParserT\\src\\main\\java\\test\\doc.java");
	  try {
		  CompilationUnit cu = StaticJavaParser.parse(file);
      	  Optional<SimpleName> optional = cu.findFirst(SimpleName.class);
		  cu.accept(new MethodVisitor(file.getAbsolutePath(), file.getName(), optional.get().getIdentifier()),null);
	  } catch (FileNotFoundException e) {
		  e.printStackTrace();
	  }
  }

  private static class MethodVisitor extends VoidVisitorAdapter<Void> {

  	  private String file_path;
	  private String file_name;
	  private String cast_file_name;

	  public MethodVisitor(String file_path, String file_name, String cast_file_name) {
		  this.file_path = file_path;
		  this.file_name = file_name;
		  this.cast_file_name = cast_file_name;
	  }

	  public void visit(MethodDeclaration n, Void arg) {
		  System.out.println("file_name:" + this.file_name);
		  System.out.println("file_path:" + this.file_path);
		  System.out.println("cast_file_name:" + this.cast_file_name);
          System.out.println("function:"+n.getName());
		  System.out.println("start:"+ n.getBegin().get().line);
		  System.out.println("end:"+ n.getEnd().get().line);
		  String content = getComment(n.getComment());
		  if (content.length() > 0) {
			  System.out.println("comment:" + content);
		  }

		  List<BlockStmt> blockStmts = n.findAll(BlockStmt.class);
		  for (BlockStmt blockStmt : blockStmts) {
			  NodeList<Statement> statements = blockStmt.getStatements();
			  for (Statement statement : statements) {
				  Optional<Comment> optional = statement.getComment();
				  String contentStmt = getComment(optional);
				  if (content.length() > 0) {
					  System.out.println("comment:" + contentStmt);
				  }
			  }
		  }
	  }

	  private String getComment(Optional<Comment> optional) {
		  if (optional.isPresent()) {
			  Comment comment = optional.get();
			  if (comment.getContent().contains("valid")) {
			  	 return comment.getContent();
			  }
			  return BLANK;
		  }
		  return BLANK;
	  }
  }
}
