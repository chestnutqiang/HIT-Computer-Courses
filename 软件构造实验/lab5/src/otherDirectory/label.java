package otherDirectory;

/*
 * AF:label是输入的一种形式
 */

public class label {
  String messsage;
  
  public label(String s) {
    messsage = s;
  }
  
  public String toString() {
    return new String(messsage);
  }
  

  
  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
      
    if (this.getClass() == o.getClass()) {
      label temp = (label)o;
      return messsage.equals(temp.messsage);
    }
    return false;
  }
  
  @Override
  public int hashCode() {
    return messsage.hashCode();
  }
}
