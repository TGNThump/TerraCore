package uk.co.terragaming.TerraCore.Util.Logger;

import org.slf4j.Logger;
import org.slf4j.Marker;

import uk.co.terragaming.TerraCore.TerraPlugin;
import uk.co.terragaming.TerraCore.Util.Text.MyText;

public class TerraLogger implements org.slf4j.Logger {
	
//	private static final String prefix = "[<l>TerraCraft<r>]";
//	private final String info = "[<l>INFO<r>] ";
//	private final String debug = "[<l>DEBUG<r>] ";
//	private final String warn = "[<l>WARN<r>] ";
//	private final String error = "[<l>ERROR<r>] ";
//	private final String trace = "[<l>TRACE<r>] ";
	
	private static final String prefix = "";
	private final String info = "";
	private final String debug = "";
	private final String warn = "";
	private final String error = "";
	private final String trace = "";
	private final String reset = "<r>";
	
	Logger base = TerraPlugin.instance.baseLogger;
	
	public TerraLogger(){
		
	}
	
	public TerraLogger(Logger base){
		this.base = base;
	}
	
	@Override
	public String getName() {
		return base.getName();
	}
	
	@Override
	public boolean isTraceEnabled() {
		return base.isTraceEnabled();
	}
	
	@Override
	public void trace(String msg) {
		base.trace(MyText.of(true, prefix + trace + msg + reset));
	}
	
	@Override
	public void trace(String format, Object arg) {
		base.trace(MyText.of(true, prefix + trace + format + reset, arg));
	}
	
	@Override
	public void trace(String format, Object arg1, Object arg2) {
		base.trace(MyText.of(true, prefix + trace + format + reset, arg1, arg2));
	}
	
	@Override
	public void trace(String format, Object... arguments) {
		base.trace(MyText.of(true, prefix + trace + format + reset, arguments));
	}
	
	@Override
	public void trace(String msg, Throwable t) {
		base.trace(MyText.of(true, prefix + trace + msg + reset), t);
	}
	
	@Override
	public boolean isTraceEnabled(Marker marker) {
		return base.isTraceEnabled(marker);
	}
	
	@Override
	public void trace(Marker marker, String msg) {
		base.trace(marker, MyText.of(true, prefix + trace + msg + reset));
	}
	
	@Override
	public void trace(Marker marker, String format, Object arg) {
		
	}
	
	@Override
	public void trace(Marker marker, String format, Object arg1, Object arg2) {
		
	}
	
	@Override
	public void trace(Marker marker, String format, Object... argArray) {
		
	}
	
	@Override
	public void trace(Marker marker, String msg, Throwable t) {
		
	}
	
	@Override
	public boolean isDebugEnabled() {
		return base.isDebugEnabled();
	}
	
	@Override
	public void debug(String msg) {
		base.debug(MyText.of(true, prefix + debug + msg + reset));
	}
	
	@Override
	public void debug(String format, Object arg) {
		base.trace(MyText.of(true, prefix + debug + format + reset, arg));
	}
	
	@Override
	public void debug(String format, Object arg1, Object arg2) {
		base.trace(MyText.of(true, prefix + debug + format + reset, arg1, arg2));

	}
	
	@Override
	public void debug(String format, Object... arguments) {
		base.trace(MyText.of(true, prefix + debug + format + reset, arguments));

	}
	
	@Override
	public void debug(String msg, Throwable t) {
		base.trace(MyText.of(true, prefix + debug + msg + reset), t);
	}
	
	@Override
	public boolean isDebugEnabled(Marker marker) {
		return base.isDebugEnabled(marker);
	}
	
	@Override
	public void debug(Marker marker, String msg) {
		base.debug(marker, MyText.of(true, prefix + debug + msg + reset));
	}
	
	@Override
	public void debug(Marker marker, String format, Object arg) {
		
	}
	
	@Override
	public void debug(Marker marker, String format, Object arg1, Object arg2) {
		
	}
	
	@Override
	public void debug(Marker marker, String format, Object... arguments) {
		
	}
	
	@Override
	public void debug(Marker marker, String msg, Throwable t) {
		
	}
	
	@Override
	public boolean isInfoEnabled() {
		return base.isInfoEnabled();
	}
	
	@Override
	public void info(String msg) {
		base.info(MyText.of(true, prefix + info + msg + reset));
	}
	
	@Override
	public void info(String format, Object arg) {
		info(String.format(format, arg));
	}
	
	@Override
	public void info(String format, Object arg1, Object arg2) {
		info(String.format(format, arg1, arg2));
	}
	
	@Override
	public void info(String format, Object... arguments) {
		info(String.format(format, arguments));	
	}
	
	@Override
	public void info(String msg, Throwable t) {
		base.info(MyText.of(true, prefix + info + msg + reset), t);
	}
	
	@Override
	public boolean isInfoEnabled(Marker marker) {
		return base.isInfoEnabled(marker);
	}
	
	@Override
	public void info(Marker marker, String msg) {
		base.info(marker, MyText.of(true, prefix + info + msg + reset));
	}
	
	@Override
	public void info(Marker marker, String format, Object arg) {
		
	}
	
	@Override
	public void info(Marker marker, String format, Object arg1, Object arg2) {
		
	}
	
	@Override
	public void info(Marker marker, String format, Object... arguments) {
		
	}
	
	@Override
	public void info(Marker marker, String msg, Throwable t) {
		
	}
	
	@Override
	public boolean isWarnEnabled() {
		return base.isWarnEnabled();
	}
	
	@Override
	public void warn(String msg) {
		base.warn(MyText.of(true, prefix + warn + msg + reset));
	}
	
	@Override
	public void warn(String format, Object arg) {
		
	}
	
	@Override
	public void warn(String format, Object... arguments) {
		
	}
	
	@Override
	public void warn(String format, Object arg1, Object arg2) {
		
	}
	
	@Override
	public void warn(String msg, Throwable t) {
		
	}
	
	@Override
	public boolean isWarnEnabled(Marker marker) {
		return base.isWarnEnabled(marker);
	}
	
	@Override
	public void warn(Marker marker, String msg) {
		base.warn(marker, MyText.of(true, prefix + warn + msg + reset));
	}
	
	@Override
	public void warn(Marker marker, String format, Object arg) {
		
	}
	
	@Override
	public void warn(Marker marker, String format, Object arg1, Object arg2) {
		
	}
	
	@Override
	public void warn(Marker marker, String format, Object... arguments) {
		
	}
	
	@Override
	public void warn(Marker marker, String msg, Throwable t) {
		
	}
	
	@Override
	public boolean isErrorEnabled() {
		return base.isErrorEnabled();
	}
	
	@Override
	public void error(String msg) {
		base.error(MyText.of(true, prefix + error + msg + reset));
	}
	
	@Override
	public void error(String format, Object arg) {
		
	}
	
	@Override
	public void error(String format, Object arg1, Object arg2) {
		
	}
	
	@Override
	public void error(String format, Object... arguments) {
		
	}
	
	@Override
	public void error(String msg, Throwable t) {
		
	}
	
	@Override
	public boolean isErrorEnabled(Marker marker) {
		return base.isErrorEnabled(marker);
	}
	
	@Override
	public void error(Marker marker, String msg) {
		base.error(marker, MyText.of(true, prefix + error + msg + reset));
	}
	
	@Override
	public void error(Marker marker, String format, Object arg) {
		
	}
	
	@Override
	public void error(Marker marker, String format, Object arg1, Object arg2) {
		
	}
	
	@Override
	public void error(Marker marker, String format, Object... arguments) {
		
	}
	
	@Override
	public void error(Marker marker, String msg, Throwable t) {
		
	}
	
	public void blank() {
		this.info(" ");
	}
}