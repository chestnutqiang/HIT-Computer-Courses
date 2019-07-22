package myExceptions;

public class IntimacyOutOfRangeException extends Exception{

  private static final long serialVersionUID = 1L;

  public IntimacyOutOfRangeException() { 
    
  } 

  // 提供一个有参数的构造方法，可自动生成
  public IntimacyOutOfRangeException(String message) { 
    super(message);// 把参数传递给Throwable的带String参数的构造方法 
  } 
}
