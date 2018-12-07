package prc.autodoc;

/**
 * An absolutely critical part of the Document Creator, not. Prints a spinning line
 * for the user to look at while the program is working.
 */
public final class Spinner{
	private char[] states = new char[]{'|','/','-','\\'};
	private int curState = 0;
	private boolean active = true;
	
	
	/**
	 * Spins the spinner.
	 */
	public void spin(){
		if(active) System.out.print(states[curState = ++curState % states.length] + "\u0008");
	}
	
	
	/**
	 * Turns the spinner off.
	 */
	public void disable(){ active = false; }
}