package otherDirectory;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/*
 * AF：
 * number是输入数字的一种形式
 */

public class number {
	public BigDecimal value;
	public number(String s){
		value=new BigDecimal(s);
	}
	
	@Override
	public boolean equals(Object o) {
		if(this.getClass()==o.getClass()) {
			number temp=(number)o;
			return value.equals(temp.value);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return value.hashCode();
	}
	
	public String toString() {
		return ""+value;
	}
	
	
	public static BigDecimal sqrt(BigDecimal value, int scale){
	    BigDecimal num2 = BigDecimal.valueOf(2);
	    int precision = 100;
	    MathContext mc = new MathContext(precision, RoundingMode.HALF_UP);
	    BigDecimal deviation = value;
	    int cnt = 0;
	    while (cnt < precision) {
	        deviation = (deviation.add(value.divide(deviation, mc))).divide(num2, mc);
	        cnt++;
	    }
	    deviation = deviation.setScale(scale, BigDecimal.ROUND_HALF_UP);
	    return deviation;
	}

}
