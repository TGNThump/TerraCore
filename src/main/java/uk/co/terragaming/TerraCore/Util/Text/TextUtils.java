package uk.co.terragaming.TerraCore.Util.Text;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextBuilder;
import org.spongepowered.api.text.Texts;


public class TextUtils {
	
	public static Text repeat(Text string, int times){
		
		TextBuilder builder = Texts.builder();
				
		for (int i = 0; i < times; i++){
			builder.append(string);
		}
		
		return builder.toText();
	}
	
	public static Text repeat(String string, int times){
		return repeat(Texts.of(string), times);		
	}
	
}
