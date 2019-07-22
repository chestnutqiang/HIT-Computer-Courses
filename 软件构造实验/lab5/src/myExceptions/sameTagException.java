package myExceptions;

//触发条件：存在标签完全一样的元素
public class sameTagException extends Exception {

  private static final long serialVersionUID = 1L;

  public sameTagException() { 
    
  } 

  // 提供一个有参数的构造方法，可自动生成
  public sameTagException(String message) { 
    super(message);// 把参数传递给Throwable的带String参数的构造方法 
  } 
}
