package myExceptions;

//触发条件：角度越界，即角度不在0-360度这个范围
public class WrongAngleException extends Exception {
     
  private static final long serialVersionUID = 1L;

  public WrongAngleException() { 
      
  } 

  // 提供一个有参数的构造方法，可自动生成
  public WrongAngleException(String message) { 
    super(message);// 把参数传递给Throwable的带String参数的构造方法 
  } 
}
