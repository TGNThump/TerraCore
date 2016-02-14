package uk.co.terragaming.TerraCore.Commands;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.NoSuchElementException;
import java.util.function.Consumer;

public final class Flag<T>{
	
	private T value;
	private boolean present = false;
	
	private Flag(T value){
		if (value != null) present = true;
		this.value = value;
	}
	
	public static <T> Flag<T> empty(){
		return new Flag<T>(null);
	}
	
	public static <T> Flag<T> of(T value){
		checkNotNull(value);
		return new Flag<T>(value);
	}
	
	public static <T> Flag<T> ofNullable(T value){
		return new Flag<T>(value);
	}
	
	public T get(){
		if (!present) throw new NoSuchElementException();
		return value;
	}
	
	public boolean isPresent(){
		return present;
	}
	
	public void ifPresent(Consumer<? super T> consumer){
		if (!present) return;
		checkNotNull(consumer);
		consumer.accept(value);
	}
	
	public T orElse(T other){
		return present ? value : other;
	}
	
	
	
}
