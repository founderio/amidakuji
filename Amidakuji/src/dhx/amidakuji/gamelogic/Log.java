package dhx.amidakuji.gamelogic;

import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * Copyright 2014 Benjamin Lšsch
 * 
 * This file is part of Amidakuji.

   Amidakuji is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   Amidakuji is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with Amidakuji. If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * 
 * @author Benjamin Lšsch
 * @version 0.4
 * @date 15.01.2014
 * 
 */
public class Log {

	private String top;
	private String bottom;
	private byte index;
	private static byte apCount = 0;
	
	private TreeMap<Byte, Log> aPMap = new TreeMap<Byte, Log>();
	
	public Log(byte index) {
		this.index = index;
	}
	
	public Log(String top, String bottom) {
		this.index = apCount;
		this.setTop(top);
		this.setBottom(bottom);
		apCount++;
	}
	
	public boolean addStair(byte index, Log obj) {
		if(this == obj
				|| aPMap.containsKey(index) || (aPMap.containsKey(index) && aPMap.get(index).aPMap.containsKey(index))
				|| Math.abs(obj.getIndex() - this.getIndex()) > 1) {
			// DESC-> cannot connect Log to itself
			// DESC-> access point is already taken
			// DESC-> both Logs are not next to each other
			return false;
		}
		aPMap.put(index, obj);
		obj.aPMap.put(index, this);
		
		return true;
	}
	
	public Log calculate(byte index){
		Entry<Byte, Log> entry;
		
		if(aPMap.containsKey(index)) {
			// DESC-> if the given index is a key, then get its value
			entry = aPMap.get(index).getAPMap().higherEntry(index);
		} else {
			// DESC-> if the given index is NOT a key, then get the next higher key
			entry = aPMap.higherEntry(index);
			if(entry == null) {
				// DESC-> if there is no higher key, return this Log object
				return this;
			} else {
				// DESC->
				index = entry.getKey();
				entry = entry.getValue().getAPMap().higherEntry(index);
			}
		}
		
		// DESC-> get from the Log obj X at given index i the stairTable obj and check if there is a stair in Log X hereafter
		if(entry == null) {
			// DESC-> there is no further stair, thus return 
			return aPMap.get(index);
		} else {
			// DESC-> there is another stair, thus continue recursion with the found key/index
			return aPMap.get(index).calculate(entry.getKey());
		}

		
	}
	
	@Override
	public String toString(){
		String output = "";
		
		output += this.getTop() + " is connected to: " + System.getProperty("line.separator");
		for(Entry<Byte, Log> element: this.aPMap.entrySet()) {
			output += "log " + element.getValue().getTop() + " at access point " + element.getKey() + System.getProperty("line.separator");
		}
		
		return output;
	}
	
	public void setTop(String top){
		this.top = top;
	}
	
	public void setBottom(String bottom){
		this.bottom = bottom;
	}

	public String getTop() {
		return top;
	}

	public String getBottom() {
		return bottom;
	}
	
	public TreeMap<Byte, Log> getAPMap() {
		return aPMap;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(byte index) {
		this.index = index;
	}
}
