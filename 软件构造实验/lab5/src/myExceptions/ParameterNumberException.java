package myExceptions;


//触发条件：txt文件传入的参数数量不正确
public class ParameterNumberException extends Exception {

  private static final long serialVersionUID = 1L;

  public ParameterNumberException() { 
    
  } 

  // 提供一个有参数的构造方法，可自动生成
  public ParameterNumberException(String message) { 
    super(message);// 把参数传递给Throwable的带String参数的构造方法 
  } 
}
