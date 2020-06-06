import animations.Animation;

public interface Menu<T> extends Animation {
	public void addSelection(String key, String message, T returnVal);
	
	public T getStatus();

}
