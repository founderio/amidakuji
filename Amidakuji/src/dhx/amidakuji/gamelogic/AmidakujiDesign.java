package dhx.amidakuji.gamelogic;

import java.util.Map.Entry;
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
 * If totalWidth and totalHeight are set, boxWidth and boxHeight will be overridden.
 * Use padding for the space between the Amadkuji and everything outside of the border.
 * Use textPadding to define the space between Text and border and between Text and Logs.
 * Margin is Font height + textPadding*2
 * 
 * @author Benjamin Lšsch
 * @version 0.4
 * @date 16.01.2014
 * 
 */

public class AmidakujiDesign {

	private Amidakuji amidakuji;
	private int padding;
	private int textPadding;

	private int boxWidth;
	private float boxHeight;
	private String[][] result;
	
	private int blockDown;	// DESC-> returns the number of necessary blocks (vertical)
	private int blockSide;	// DESC-> returns the number of necessary blocks (horizontal)
	
	private int totalWidth = 0;
	private float totalHeight = 0;
	
	private byte[][] map;
	
	private String[][] properties;
	private float fontHeight;
	
	public AmidakujiDesign() {
	}
	
	public AmidakujiDesign(int totalwidth, int totalheight) {
		totalWidth = totalwidth;
		totalHeight = totalheight;
	}
	
	public void design(byte count, String[][] props) {
		amidakuji = new Amidakuji();
		properties = props;
		
		for(int counter = 0; counter < properties.length; counter++) {
			Log temp = new Log(properties[counter][0], properties[counter][1]);
			amidakuji.addLog(temp);
		}
		
		amidakuji.randomGenerator(count);
		result = amidakuji.calculate();
		build(count);
	}
	
	private void build(byte totalLength) {
		blockDown = totalLength * 2 + 1;
		blockSide = amidakuji.getLogList().size() * 2 + 1;

		if(totalHeight != 0 && totalWidth != 0) {
			boxWidth = (totalWidth - padding * 2) / blockSide;
			boxHeight = (totalHeight - (padding - getMargin()) * 2) / blockDown;
		} else {
			totalWidth = boxWidth * blockSide + padding * 2;
			totalHeight = boxHeight * blockDown + (padding + getMargin()) * 2;
		}
		
		map = new byte[blockDown][blockSide];
		
		// DESC-> fill spaces which are not allowed to hold a solid block. fill log lines with solid blocks
		for(int row = 0; row < blockDown; row++ ) {
			for(int col = 0; col < blockSide; col++) {
				if (col % 2 != 0) {
					map[row][col] = 1;
				}
			}
		}
		
		for(byte logCounter = 0; logCounter < amidakuji.getLogList().size() - 1; logCounter++) {
			for(Entry<Byte, Log> entry: amidakuji.getLogList().get(logCounter).getAPMap().entrySet()) {
				if(entry.getValue() == amidakuji.getLogList().get(logCounter + 1)) {
					map[2*entry.getKey()+1][2*logCounter+2] = 1;
				}
			}
		}
		
		printSimple(false);
	}

	private void printSimple(boolean print) {
		
		if(!print) return;
		
		for(int row = 0; row < blockDown; row++ ) {
			for(int col = 0; col < blockSide; col++) {
				System.out.print(map[row][col] + "\t");
			}
			System.out.println();
		}
	}

	public Amidakuji getAmidakuji() {
		return amidakuji;
	}

	public void setAmidakuji(Amidakuji ama) {
		this.amidakuji = ama;
	}

	public int getPadding() {
		return padding;
	}

	public void setPadding(int padding) {
		this.padding = padding;
	}
	
	public int getTextPadding() {
		return textPadding;
	}

	public void setTextPadding(int textPadding) {
		this.textPadding = textPadding;
	}

	public float getFontHeight() {
		return fontHeight;
	}

	public void setFontHeight(float f) {
		this.fontHeight = f;
	}

	public float getMargin() {
		return this.textPadding * 2 + fontHeight;
	}
	
	public void setBoxBounds(int width, int height) {
		boxWidth = width;
		boxHeight = height;
	}

	public int getBoxWidth() {
		return boxWidth;
	}

	public void setBoxWidth(int boxWidth) {
		this.boxWidth = boxWidth;
	}

	public float getBoxHeight() {
		return boxHeight;
	}

	public void setBoxHeight(int boxHeight) {
		this.boxHeight = boxHeight;
	}

	public int getBlockSide() {
		return blockSide;
	}

	public void setBlockSide(int blockSide) {
		this.blockSide = blockSide;
	}

	public int getBlockDown() {
		return blockDown;
	}

	public int getTotalWidth() {
		return totalWidth;
	}

	public void setTotalWidth(int totalWidth) {
		this.totalWidth = totalWidth;
	}

	public float getTotalHeight() {
		return totalHeight;
	}

	public void setTotalHeight(int totalHeight) {
		this.totalHeight = totalHeight;
	}

	public byte[][] getMap() {
		return map;
	}

	public String[][] getProperties() {
		return properties;
	}

	public void setProperties(String[][] properties) {
		this.properties = properties;
	}

	public String[][] getResult() {
		return result;
	}
}
