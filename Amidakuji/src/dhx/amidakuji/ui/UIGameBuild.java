package dhx.amidakuji.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import dhx.amidakuji.gamelogic.AmidakujiDesign;

/**
 * Copyright 2014 Benjamin Lösch
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
 * @author Benjamin Lösch
 * @version 0.4
 * 
 */

public class UIGameBuild implements Screen {

	private AmidakujiDesign amidakujiDesign;
	private boolean showControls = true;
	
	private SpriteBatch batch;

	private InputAdapter gameInputProcessor;

	private boolean keyDown = false;

	private int apBlockCount;
	private String[][] data;
	
	private float xOffset = 0;
	private float lastPosition;
	
	protected float totalWidth;
	
	private AmidakujiMain game;
	
	private float amidakujiViewport = 1000;	// TODO: currently only estimated
	
	// scrollbar values
	private float sbWidth;
	private float sbHeight = 16;
	private float sbX;
	private final float sbY = 130;
	private boolean scroll = false;
	private boolean showScrollBar = false;
	
	public UIGameBuild(BitmapFont bitmap, AmidakujiMain game) {
		this.batch = new SpriteBatch();
		this.game = game;

		gameInputProcessor = new InputAdapter() {

			@Override
			public boolean keyDown(int keycode) {
				if (!keyDown) {
					if (keycode == Input.Keys.R) {
						amidakujiDesign = calcAmidakuji();
						return true;
					} else if (keycode == Input.Keys.S) {
						// TODO: shuffle (switch top texts among each other as
						// well as bottom texts) and calculate some amidakuji
						// and on random time stop calculating
						return true;
					} else if (keycode == Input.Keys.ESCAPE) {
						UIGameBuild.this.game.setScreen(UIGameBuild.this.game.getMenuBuild());
						return true;
					} else if (keycode == Input.Keys.H) {
						if (showControls) {
							showControls = false;
						} else {
							showControls = true;
						}
						return true;
					}
					keyDown = true;
				}

				return false;
			}

			@Override
			public boolean keyUp(int keycode) {
				if(keyDown != false) {
					keyDown = false;
					return true;
				}
				return false;
			}
			
			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {
				if(!showScrollBar) {
					return false;
				}
				
				scroll = false;
				return true;
			}
			
			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				
				if(!showScrollBar) {
					return false;
				}
				
				screenY = Gdx.graphics.getHeight() - screenY;	// has to be converted, because of different y origins
				
				Rectangle rec = new Rectangle(sbX, sbY, sbWidth, sbHeight);
				
				if(rec.contains(screenX, screenY)) {
					scroll = true;
				}
				
				lastPosition = screenX;
				
				return true;
			}

			
			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {

				if(!showScrollBar) {
					return false;
				}
				
				if(scroll) {
					// DESC-> on click and drag on scroll knob
					xOffset -= (screenX - lastPosition)/(amidakujiViewport / totalWidth);
					sbX += (screenX - lastPosition);
				} else {
					// DESC-> on click and drag NOT on scroll knob
					xOffset += (screenX - lastPosition);
					sbX -= (screenX - lastPosition)*(amidakujiViewport / totalWidth);
				}
				
				lastPosition = screenX;
				
				if((xOffset) <= (totalWidth - amidakujiViewport)*-1f) {
					// DESC-> scroll to the right
					xOffset = (totalWidth - amidakujiViewport)*-1f;
					sbX = xOffset*(amidakujiViewport / totalWidth)*-1f;
					return false;
				} else if (xOffset > 0) {
					// DESC-> scroll to the left
					xOffset = 0;
					sbX = 0;
					return false;
				}				
				return true;
			}
		};
	}
	
	public void setData(String[][] data) {
		this.data = data;
	}

	public AmidakujiDesign calcAmidakuji() {
		amidakujiDesign = new AmidakujiDesign();
		amidakujiDesign.setBoxBounds(AmidakujiMain.tile.getWidth(), AmidakujiMain.tile.getHeight());
		amidakujiDesign.setTextPadding(15);
		amidakujiDesign.setFontHeight(AmidakujiMain.bFontPressStart.getCapHeight());

		amidakujiDesign.design((byte) 5, data);

		float highest = 0;
		float sndHighest = 0;

		String tempKey;
		String tempValue;
		float tempWidth = 0;
		
		for (int counter = 0; counter < data.length; counter++) {
			tempKey = data[counter][0];
			tempValue = data[counter][1];
			
			if (AmidakujiMain.bFontPressStart.getBounds(tempKey).width > AmidakujiMain.bFontPressStart
					.getBounds(tempValue).width) {
				tempWidth = AmidakujiMain.bFontPressStart.getBounds(tempKey).width;
			} else {
				tempWidth = AmidakujiMain.bFontPressStart.getBounds(tempValue).width;
			}

			if (tempWidth > highest) {
				sndHighest = highest;
				highest = tempWidth;
			} else if (tempWidth > sndHighest) {
				sndHighest = tempWidth;
			}
		}

		float maxStrWidth = highest / 2;
		apBlockCount = 1;
		if (maxStrWidth > amidakujiDesign.getBoxWidth()) {
			// DESC-> get the length X of the string part, which goes beyond the box borders (maxStrWidth - box.width/2)
			// DESC-> multiply by 2, because the calculated length X covers at the utmost half of the gap between this box and the next one
			// DESC-> divide by box.width, then you get the minimum box count to prevent overlapping texts
			apBlockCount += (int) ((maxStrWidth - amidakujiDesign.getBoxWidth() / 2) * 2 / amidakujiDesign.getBoxWidth());
		}
		
		if (Math.abs(highest - sndHighest) < 15) {
			// DESC-> Math.abs(highest - sndHighest) serves as a buffer, if the longest and second longest are almost the same length, they might be too close
			apBlockCount++;
		}

		return amidakujiDesign;
	}

	public void drawAmidakuji(float x, float y) {
		
		byte[][] map = amidakujiDesign.getMap();

		float startX = x + amidakujiDesign.getPadding();
		// DESC-> if the first text on the left is too long, shift x by
		// (text.length/2 - box.width * 1.5)
		
		float max = 0;
		
		if(AmidakujiMain.bFontPressStart.getBounds(amidakujiDesign.getAmidakuji().getLogList().get(0).getTop()).width
				> AmidakujiMain.bFontPressStart.getBounds(amidakujiDesign.getAmidakuji().getLogList().get(0).getBottom()).width) {
			max = AmidakujiMain.bFontPressStart.getBounds(amidakujiDesign.getAmidakuji().getLogList().get(0).getTop()).width;
		} else {
			max = AmidakujiMain.bFontPressStart.getBounds(amidakujiDesign.getAmidakuji().getLogList().get(0).getBottom()).width;
		}
		
		if (amidakujiDesign.getBoxWidth() * 1.5 < max / 2) {
			startX += max / 2 - amidakujiDesign.getBoxWidth() / 2;
		}

		float startY = y + amidakujiDesign.getPadding();

		// --------------------- //

		float drawStringsX = (float) (startX + amidakujiDesign.getBoxWidth() * 1.5);

		CharSequence tempCharSeq;
		for (int counter = 0; counter < data.length; counter++) {
			tempCharSeq = data[counter][1];
			float tempFontXPos = drawStringsX - AmidakujiMain.bFontPressStart.getBounds(tempCharSeq).width / 2;
			
			AmidakujiMain.bFontPressStart.draw(
					batch,
					tempCharSeq,
					tempFontXPos,
					startY + amidakujiDesign.getTextPadding()
							+ AmidakujiMain.bFontPressStart.getCapHeight());
			drawStringsX += amidakujiDesign.getBoxWidth() * (apBlockCount + 1);
			
			if((tempFontXPos + AmidakujiMain.bFontPressStart.getBounds(tempCharSeq).width) > totalWidth) {
				// DESC -> to define total width necessary to depict amidakuji
				totalWidth = tempFontXPos + AmidakujiMain.bFontPressStart.getBounds(tempCharSeq).width;
			}
		}

		// --------------------- //

		float tempStartX = startX;
		float tempStartY = startY + amidakujiDesign.getMargin()
				+ amidakujiDesign.getBoxHeight()
				* (amidakujiDesign.getBlockDown() - 1);

		for (int col = 0; col < amidakujiDesign.getBlockSide(); col++) {
			tempStartY = startY + amidakujiDesign.getMargin()
					+ amidakujiDesign.getBoxHeight()
					* (amidakujiDesign.getBlockDown() - 1);
			for (int row = 0; row < amidakujiDesign.getBlockDown(); row++) {
				if (map[row][col] == 1) {
					if (col > 0 && col % 2 == 0) {
						float apX = tempStartX;
						for (int c = 0; c < apBlockCount; c++) {
							batch.draw(AmidakujiMain.tile, apX, tempStartY);
							apX += amidakujiDesign.getBoxWidth();
						}
					} else {
						batch.draw(AmidakujiMain.tile, tempStartX, tempStartY);
					}
				}
				tempStartY -= amidakujiDesign.getBoxHeight();
			}
			if (col > 0 && col % 2 == 0) {
				tempStartX += amidakujiDesign.getBoxWidth() * apBlockCount;
			} else {
				tempStartX += amidakujiDesign.getBoxWidth();
			}
		}

		// --------------------- //

		drawStringsX = (float) (startX + amidakujiDesign.getBoxWidth() * 1.5);
		float drawStringsY = y + amidakujiDesign.getTotalHeight()
				- amidakujiDesign.getTextPadding()
				- amidakujiDesign.getFontHeight();

		for (int counter = 0; counter < data.length; counter++) {
			tempCharSeq = data[counter][0];
			float tempFontXPos = drawStringsX - AmidakujiMain.bFontPressStart.getBounds(tempCharSeq).width / 2;
			
			AmidakujiMain.bFontPressStart.draw(batch,
					tempCharSeq,
					tempFontXPos,
					drawStringsY + AmidakujiMain.bFontPressStart.getCapHeight());
			drawStringsX += amidakujiDesign.getBoxWidth() * (apBlockCount + 1);

			if((tempFontXPos + AmidakujiMain.bFontPressStart.getBounds(tempCharSeq).width) > totalWidth) {
				// DESC -> to define total width necessary to depict amidakuji
				totalWidth = tempFontXPos + AmidakujiMain.bFontPressStart.getBounds(tempCharSeq).width;
			}
		}
		
		if(!(totalWidth < amidakujiViewport)) {
			showScrollBar = true;
			sbWidth =  (amidakujiViewport) * (amidakujiViewport / totalWidth);
		}

	}

	public void drawResults(float x, float y) {
		String[][] results = amidakujiDesign.getResult();
		float fontHeight = amidakujiDesign.getFontHeight();

		CharSequence resultLine;
		
		AmidakujiMain.bFontPressStart.draw(batch, "RESULTS", x, y);
		
		y -= fontHeight + AmidakujiMain.lineYPadding;
		float startY = y;
		float longest = 0.0f;
		
		for (int counter = 0; counter < results.length; counter++) {
			if((counter) % 3 == 0 && counter > 0) {
				x += AmidakujiMain.lineYPadding + longest;
				y = startY;
			}
			
			resultLine = results[counter][0].toUpperCase() + ": " + results[counter][1].toUpperCase();
			
			if (longest < AmidakujiMain.bFontPressStart.getBounds(resultLine).width) {
				longest = AmidakujiMain.bFontPressStart.getBounds(resultLine).width;
			}
			
			AmidakujiMain.bFontPressStart.draw(batch, resultLine, x, y);
			y -= fontHeight + AmidakujiMain.lineYPadding;
		}	
		
	}

	public void drawControls() {
		String[] controls = new String[4];
		controls[0] = "Controls";
		controls[1] = "R:\t REDO";
//		controls[2] = "S:\t SHUFFLE";
		controls[2] = "H:\t HIDE CONTROLS";
		controls[3] = "ESC:\t OPEN MENU";
		
		float x = Gdx.graphics.getWidth() - AmidakujiMain.bFontPressStart.getBounds("FRAMES PER SECOND: 00").width - AmidakujiMain.lineYPadding*2;
		float y = Gdx.graphics.getHeight() - AmidakujiMain.startY;
		for (int counter = 0; counter < controls.length; counter++) {
			AmidakujiMain.bFontPressStart.draw(batch, controls[counter], x, y);
			y -= AmidakujiMain.bFontPressStart.getCapHeight() + AmidakujiMain.lineYPadding;
		}
		
		AmidakujiMain.bFontPressStart.draw(batch, "FRAMES PER SECOND: " + Gdx.graphics.getFramesPerSecond(), AmidakujiMain.lineYPadding*2, Gdx.graphics.getHeight() - AmidakujiMain.startY);
		
//		y -= AmidakujiMain.bFontPressStart.getCapHeight() + AmidakujiMain.padding;
//		AmidakujiMain.bFontPressStart.draw(batch, controls[controls.length - 1], x, y);
	}

	public void dispose() {
		if (batch != null)
			batch.dispose();
	}

	public boolean getShowControls() {
		return showControls;
	}

	public void setShowControls(boolean showControls) {
		this.showControls = showControls;
	}
	
	public AmidakujiDesign getAmidakujiDesign() {
		return amidakujiDesign;
	}

	public void setAmidakujiDesign(AmidakujiDesign amidakujiDesign) {
		this.amidakujiDesign = amidakujiDesign;
	}

	public InputAdapter getGameInputProcessor() {
		return gameInputProcessor;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		AmidakujiMain.bFontDpCompic.draw(batch, "AMIDAKUJI",
				Gdx.graphics.getWidth()/2 - AmidakujiMain.bFontDpCompic.getBounds("AMIDAKUJI").width/2,
				Gdx.graphics.getHeight() - AmidakujiMain.startY);
		
		this.drawAmidakuji(xOffset, 150);
		this.drawResults(AmidakujiMain.tile.getWidth(), 120);
		if(this.getShowControls()) {
			this.drawControls();
		}
		
		batch.draw(AmidakujiMain.scrollknob, sbX + 30, sbY, sbWidth, sbHeight);
		
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this.getGameInputProcessor());
		sbX = 0;
		xOffset = 0;
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}
}
