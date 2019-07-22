package myExceptions;

//触发条件：语法不正确
public class SyntaxException extends Exception {

  private static final long serialVersionUID = 1L;

  public SyntaxException() { 
    
  } 

  // 提供一个有参数的构造方法，可自动生成
  public SyntaxException(String message) { 
    super(message);// 把参数传递给Throwable的带String参数的构造方法 
  } 
}
