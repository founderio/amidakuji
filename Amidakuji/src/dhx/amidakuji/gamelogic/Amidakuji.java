package dhx.amidakuji.gamelogic;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Random;

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
 * @date 16.01.2014
 * 
 */
public class Amidakuji {

	private ArrayList<Log> loglist = new ArrayList<Log>();
	private String[][] result;
	Random rand = new Random(GregorianCalendar.getInstance().getTimeInMillis());
	
	public Amidakuji() {
	}
	
	public void addLog(Log obj) {
		loglist.add(obj);
	}
	
	public void connectLogs(Log leftLog, Log rightLog, byte stairNumber) {
		leftLog.addStair(stairNumber, rightLog);
	}
	
	public ArrayList<Log> getLogList() {
		return loglist;
	}
	
	public byte getMaxStairCount(){
		byte max = 0;
		
		for(Log log: loglist){
			if(log.getAPMap().isEmpty()) {
				throw new AmidakujiException(log.getIndex() + "/" + log.getTop() + "/" + log.getBottom() + " is not connected to any other Log object.");
			}
			
			byte test = (byte) (log.getAPMap().lastKey() + 1);
			if(test > max)
				max = test;
		}
		
		return max;
	}
	
	public String[][] calculate() {
		
		result = new String[loglist.size()][2];
		
		for(int counter = 0; counter < loglist.size(); counter++) {
			Log e = loglist.get(counter).calculate((byte) 0);
//			System.out.println(loglist.get(counter).getTop() + ": " + e.getBottom());
			result[counter][0] = loglist.get(counter).getTop();
			result[counter][1] =  e.getBottom();
		}
		
		return result;
	}
	
	public void randomGenerator(byte count) {

		ArrayList<ArrayList<Byte>> max = new ArrayList<ArrayList<Byte>>();
		
		ArrayList<Byte> filly;
		for(byte iterLogs = 0; iterLogs < loglist.size(); iterLogs++) {
			filly = new ArrayList<Byte>();
			for(byte c = 0; c < count; c++) {
				filly.add(c);
			}
			max.add(filly);
		}
		
		int aps;
		int tempRand;
		
		for(byte iterLogs = 0; iterLogs < loglist.size() - 1; iterLogs++) {
			// DESC-> calculate random number based on open Access Points
			aps = rand.nextInt(max.get(iterLogs).size());
			if(aps < 1) aps = 1;
			for(int fill = 0; fill < aps; fill++) {
				tempRand = rand.nextInt(max.get(iterLogs).size());	// DESC-> choose a random
				
				byte dabu = max.get(iterLogs).get(tempRand);
				
				loglist.get(iterLogs).addStair(dabu, loglist.get(iterLogs + 1));
				
				max.get(iterLogs).remove((Byte) dabu);
				max.get(iterLogs + 1).remove((Byte) dabu);
			}
		}
	}
	
	@Override
	public String toString() {
		String output = "";
		for(Log log: loglist) {
			output += log.toString();
		}
		
		return output;
	}
}
