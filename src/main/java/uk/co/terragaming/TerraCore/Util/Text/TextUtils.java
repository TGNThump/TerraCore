package uk.co.terragaming.TerraCore.Util.Text;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Text.Builder;


public class TextUtils {
	
	public static Text repeat(Text string, int times){
		
		Builder builder = Text.builder();
				
		for (int i = 0; i < times; i++){
			builder.append(string);
		}
		
		return builder.toText();
	}
	
	public static Text repeat(String string, int times){
		return repeat(Text.of(string), times);		
	}
	
}
