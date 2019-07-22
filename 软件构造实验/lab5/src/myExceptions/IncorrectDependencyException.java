package myExceptions;

//触发条件：依赖关系不正确
public class IncorrectDependencyException extends Exception {

  private static final long serialVersionUID = 1L;

  public IncorrectDependencyException() { 
    
  } 

  // 提供一个有参数的构造方法，可自动生成
  public IncorrectDependencyException(String message) { 
    super(message);// 把参数传递给Throwable的带String参数的构造方法 
  } 
}
