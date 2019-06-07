/**
 * 121 Comp.Prob.Solv.II
 * Prof. Ante Poljicak
 *
 * Group 03 - Final  Project
 * @author Jorge Olavarria, Nikica Kovacevic, Marko Golemovic, Ivan Jerkovic
 * 
 */

import java.io.Serializable;

public class Move implements Serializable, Info {

	protected static final long serialVersionUID = 1112122200L;

	private String info;

	public Move(String info) {
		this.info = info;
	}

	public String getInfo() {
		return info;
	}
}
