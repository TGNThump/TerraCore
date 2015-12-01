package uk.co.terragaming.TerraCore.Util.Language;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.inject.Inject;

import org.slf4j.Logger;

public class Lang {
	
	@Inject Lang(){}
	@Inject Logger logger;
	@Inject File configDir;
	
	public HashMap<Locale, ResourceBundle> bundles = new HashMap<>();
	
	public ResourceBundle getBundle(Locale locale){
		if (bundles.containsKey(locale)) return bundles.get(locale);
		
		try{
			ResourceBundle resources = YamlResourceBundle.getBundle("lang", locale, configDir);
			bundles.put(locale, resources);
			return resources;
			
		} catch (MissingResourceException e){
			logger.error("No Lang Bundle for locale '" + locale + "', defaulting to '" + Locale.getDefault() + "'.");
			return YamlResourceBundle.getBundle("lang", Locale.getDefault(), configDir);
		}
	}
	
//	public String get(Locale lang, String key, boolean parsed, boolean console, Object... args){
//		ResourceBundle bundle = getBundle(lang);;
//		
//		String ret;
//		if (bundle.containsKey(key)){
//			ret = bundle.getString(key);
//		} else{
//			logger.error("No translation for '" + key + "' in locale '" + lang.toString() + "'.");
//			ret = bundles.get(Locale.getDefault()).getString(key);
//		}
//		
//		if (parsed) ret = Text.of(console, ret, args);
//		return ret;
//	}
	
	
}
