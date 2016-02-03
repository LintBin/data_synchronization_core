import java.io.IOException;

import com.lin.core.ConfigHelper;


public class Main {
	public static void main(String[] args) {
		try {
			System.out.println(ConfigHelper.getPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
