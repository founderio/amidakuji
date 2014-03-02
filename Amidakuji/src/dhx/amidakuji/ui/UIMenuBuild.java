package dhx.amidakuji.ui;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

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
 * 
 */

public class UIMenuBuild implements Screen {

	private Stage stage;
	private Table table;
	private TextureAtlas texAtlas;
	private Skin skin;
	
	private ScrollPane scrollPane;
	private TextButton start;
	private TextButton back;
	private TextButton clear;
//	private TextButton debug;
	
	private boolean clearTextFieldTop;
	private boolean clearTextFieldBottom;
	private boolean focusLost = true;

	private SpriteBatch batch;
	private AmidakujiMain game;
	
	private ArrayList<MenuLine> menuLines = new ArrayList<MenuLine>();
	
	private int countLevelValues = 0;
	
	private TextField textTop;
	private TextField textBottom;
	private TextureRegionDrawable drawableDeleteBtn;
	private TextFieldListener valueElementListener;

	public UIMenuBuild(BitmapFont bitmapFont, AmidakujiMain game) {
		this.batch = new SpriteBatch();
		this.game = game;
	}

	public void dispose() {

		if (batch != null)
			batch.dispose();
		if (stage != null)
			stage.dispose();
		if (texAtlas != null)
			texAtlas.dispose();
		if (skin != null)
			skin.dispose();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);	
		
		stage.act(delta);
		stage.draw();
		Table.drawDebug(stage);
		
//		batch.begin();
//		AmidakujiMain.drawFrame(batch, AmidakujiMain.tile);
//		batch.end();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
		// start //
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		clearTextFieldTop = true;
		clearTextFieldBottom = true;
		
		texAtlas = new TextureAtlas("ui/menuAtlas.pack");
		skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), texAtlas);
		
		float startY = Gdx.graphics.getHeight() - AmidakujiMain.startY;
		
		Label enterTop = new Label("PLEASE ENTER A TOP-LEVEL VALUE", skin);
		startY -= enterTop.getHeight();
		enterTop.setPosition(30, startY);
		stage.addActor(enterTop);
		
		
		textTop = new TextField("PLEASE ENTER TEXT HERE", skin);
		startY -= textTop.getHeight();
		textTop.setPosition(40, startY);
		textTop.setWidth(Gdx.graphics.getWidth()/2);
		textTop.setTextFieldListener(new TextFieldListener() {

			@Override
			public void keyTyped(TextField textField, char key) {
				if(clearTextFieldTop) {
					clearTextFieldTop = false;
				}
					
				if(key == 13 || key == 10) {
					onEnterNewEntry();
				}
			}
			
		});
		textTop.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				
				if(focusLost) focusLost = false;
				
				if(clearTextFieldTop) {
					textTop.setText("");
					clearTextFieldTop = false;
				}
			}
		});
		stage.addActor(textTop);
		
		
		
		Label enterBottom = new Label("PLEASE ENTER A BOTTOM-LEVEL VALUE", skin);
		startY -= enterBottom.getHeight() + AmidakujiMain.lineYPadding;
		enterBottom.setPosition(30, startY);
		
		
		stage.addActor(enterBottom);
		
		textBottom = new TextField("PLEASE ENTER TEXT HERE", skin);
		startY -= textBottom.getHeight();
		textBottom.setPosition(40, startY);
		textBottom.setWidth(Gdx.graphics.getWidth()/2);
		textBottom.setTextFieldListener(new TextFieldListener() {

			@Override
			public void keyTyped(TextField textField, char key) {
				if(clearTextFieldBottom) {
					clearTextFieldBottom = false;
				}
				
				
				if(key == 13 || key == 10) {
					onEnterNewEntry();
				}
				
			}
			
		});
		textBottom.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(focusLost) focusLost = false;
				
				if(clearTextFieldBottom) {
					textBottom.setText("");
					clearTextFieldBottom = false;
				}
			}
		});
		stage.addActor(textBottom);
		
		
		start = new TextButton("START", skin);
		start.setPosition(30, AmidakujiMain.startY);
		start.addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {			
				
				if(countLevelValues <= 1 || menuLines.size() <= 1) {
					return;
				}
				
				String[][] data = new String[countLevelValues][2];
				
				for(int counter = 0; counter < menuLines.size(); counter++) {
					data[counter][0] = menuLines.get(counter).getTop().getText();
					data[counter][1] = menuLines.get(counter).getBottom().getText();
				}
				
				if(!focusLost) focusLost = true;
				
				UIMenuBuild.this.game.getGameBuild().setData(data);
				UIMenuBuild.this.game.getGameBuild().totalWidth = 0;
				UIMenuBuild.this.game.getGameBuild().calcAmidakuji();
				UIMenuBuild.this.game.setScreen(UIMenuBuild.this.game.getGameBuild());
				
			}
			
		});
		
		back = new TextButton("BACK", skin);
		back.setPosition(30 + start.getWidth() + AmidakujiMain.lineYPadding, AmidakujiMain.startY);
		back.addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(!focusLost) focusLost = true;
				UIMenuBuild.this.game.setScreen(new UISplash(UIMenuBuild.this.game));
			}
			
		});
		
		clear = new TextButton("CLEAR ALL", skin);
		clear.setPosition(30 + start.getWidth() + AmidakujiMain.lineYPadding * 2 + back.getWidth(), AmidakujiMain.startY);
		clear.addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(menuLines.size() == 0) {
					return;
				}
				table.clearChildren();
				menuLines.clear();
				countLevelValues = 0;
			}
			
		});
		
//		debug = new TextButton("DEBUG", skin);
//		debug.setPosition(30 + start.getWidth() + AmidakujiMain.lineYPadding * 2 + back.getWidth() + AmidakujiMain.lineYPadding * 2 + clear.getWidth(), AmidakujiMain.startY);
//		debug.addListener(new ClickListener() {
//			
//			@Override
//			public void clicked(InputEvent event, float x, float y) {			
//				
//				String[][] data = new String[9][2];
//				
//				String player = "player ";
//				String win = "wins";
//				String lose = "loses";
//				
//				for(int counter = 0; counter < data.length; counter++) {
//					data[counter][0] = player + (counter + 1);
//					if(counter < data.length - 1) {
//						data[counter][1] = win;
//					} else {
//						data[counter][1] = lose;
//					}
//				}
//				
//				if(!focusLost) focusLost = true;
//				
//				UIMenuBuild.this.game.getGameBuild().setData(data);
//				UIMenuBuild.this.game.getGameBuild().calcAmidakuji();
//				UIMenuBuild.this.game.setScreen(UIMenuBuild.this.game.getGameBuild());
//				
//			}
//			
//		});
		
		Table container = new Table(skin);
		float x = 30;
		float y = start.getHeight() + start.getY() + AmidakujiMain.lineYPadding*4;
		container.setBounds(x, y, Gdx.graphics.getWidth() - x*2, startY - y - AmidakujiMain.lineYPadding);
//		container.debug();
		
		table = new Table(skin);
//		table.debug();
		table.defaults().spaceBottom(5);
		table.top();
		
		
		valueElementListener = new TextFieldListener() {

			@Override
			public void keyTyped(TextField textField, char key) {
				
//				if(key == 13 || key == 10) {
//					textField.setText(textField.getText().toUpperCase());
//				}
			}
			
		};
		
		drawableDeleteBtn = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/delete.png"))));
		
		scrollPane = new ScrollPane(table);
		
		container.add("TOP-LEVEL VALUES").width(container.getWidth()/2).left().padBottom(15);
		container.add("BOTTOM-LEVEL VALUES").width(container.getWidth()/2).left().padBottom(15).row();
		container.add(scrollPane).expand().fill().colspan(2);	// adding the scrollpane (including sub-table)
		
		stage.addActor(container);
		stage.addActor(start);
		stage.addActor(back);
		stage.addActor(clear);
//		stage.addActor(debug);
		
		rebuild();

	}

	protected void onEnterNewEntry() {
		
		if(focusLost) {
			return;
		}
		
		String tempTopText = textTop.getText().toUpperCase().trim(); 
		String tempBottomText = textBottom.getText().toUpperCase().trim();
		
		if(tempTopText.length() == 0
					|| tempBottomText.length() == 0) {
			return;
		}
		
		for(MenuLine line: menuLines) {
			if(line.getTop().getText().compareToIgnoreCase(tempTopText) == 0) {
				return;
			}
		}
		
		TextField top = new TextField(tempTopText, skin);
		top.setTextFieldListener(valueElementListener);
		
		TextField bottom = new TextField(tempBottomText, skin);
		bottom.setTextFieldListener(valueElementListener);
		
		MenuLine temp = new MenuLine(top, bottom, new ImageButton(drawableDeleteBtn));
		
		menuLines.add(temp);
		
		table.add(temp.getTop()).expandX().padLeft(15).fillX().left();
		table.add(temp.getBottom()).expandX().padLeft(15 + 32).fillX().left();
		table.add(temp.getDelete()).left().row();
		
		countLevelValues++;
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
	
	public class MenuLine {
		
		private TextField top;
		private TextField bottom;
		private ImageButton delete;
		
		public MenuLine(TextField top, TextField bottom, ImageButton delete) {
			this.top = top;
			this.bottom = bottom;
			this.delete = delete;
			
			this.delete.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					UIMenuBuild.this.menuLines.remove(MenuLine.this);
					countLevelValues--;
					UIMenuBuild.this.rebuild();
				}
			});
		}

		public TextField getTop() {
			return top;
		}

		public void setTop(TextField top) {
			this.top = top;
		}

		public TextField getBottom() {
			return bottom;
		}

		public void setBottom(TextField bottom) {
			this.bottom = bottom;
		}

		public ImageButton getDelete() {
			return delete;
		}

		public void setDelete(ImageButton delete) {
			this.delete = delete;
		}
	}

	protected void rebuild() {
		table.clearChildren();
		
		for(MenuLine line: menuLines) {

			table.add(line.getTop()).expandX().padLeft(15).fillX().left();
			table.add(line.getBottom()).expandX().padLeft(15 + 32).fillX().left();
			table.add(line.getDelete()).left().row();
		}
		
	}
}
