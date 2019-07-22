package otherDirectory;

/*
 * AF:label是输入的一种形式
 */

public class label {
	String L;
	public label(String s){
		L=s;
	}
	
	public String toString() {
		return new String(L);
	}
	

	
	@Override
	public boolean equals(Object o) {
		if(this.getClass()==o.getClass()) {
			label temp=(label)o;
			return L.equals(temp.L);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return L.hashCode();
	}
}
