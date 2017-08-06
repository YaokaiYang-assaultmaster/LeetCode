/**
 * Implement java.lang.Integer.toBinaryString.
 */
public class ConvertBinary {
	public ConvertBinary() {
		super();
	}
	
	/**
	 * @param num The number to be converted to binary string
	 * @return String representation of the 2's complement of the number.
	 */
	public String toBinaryString(int num) {
		Long new_num = Long.valueOf(num); // Case to Long to deal with Integer.MIN_VALUE
		if (num == 0) {
			return "0";
		} else if (num > 0) {
			// the 2's complement of a positive number is the binary representation
			// of the number
			
			return toBaseTwo(new_num, false);
		} else { // num < 0
			// the 2's complement of a negative number is the 1's complement of that number plus 1.
			// the 1's complement of a negative number can be obtained by reverting all the digits
			// of the base-two representation of it's absolute value.
			new_num *= -1;
			String result = toBaseTwo(new_num, true);
			result = revertDigit(result);
			result = addOne(result);
			return result;
		}
	}
	
	/**
	 * @param num The number to be converted to base 2 representation
	 * @param flag Boolean flag to indicates if 0s need to be complemented
	 * 		to make the base 2 representation 32 digits long. This is needed for negative original inputs.
	 * @return String representation of a base 2 representation.
	 */
	private String toBaseTwo(Long num, boolean flag) {
		StringBuilder sb = new StringBuilder();
		while (num > 0) {
			long curr = num % 2;
			num = num / 2;
			sb.append(curr);
		}
		if (flag) {
			while (sb.length() < 32) {
				// add extra 0s to the binary string to make it 32 bits long
				sb.append('0');
			}
		}
		
		return sb.reverse().toString();
	}
	
	private String revertDigit(String num) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < num.length(); i++) {
			sb.append(num.charAt(i) == '0' ? '1' : '0');
		}
		
		return sb.toString();
	}
	
	private String addOne(String num) {
		StringBuilder sb = new StringBuilder();
		int carryOver = 1;
		int i = num.length() - 1;
		for (; i >= 0; i--) {
			int curr = num.charAt(i) - '0';
			curr += carryOver;
			if (curr == 2) {
				carryOver = 1;
				curr = 0;
			} else {
				carryOver = 0;
			}
			sb.append(curr);
		}
		
		return sb.reverse().toString();
	}
}
