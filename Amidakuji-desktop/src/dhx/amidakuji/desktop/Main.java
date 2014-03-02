package dhx.amidakuji.desktop;

import java.awt.Toolkit;

import com.apple.eawt.Application;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import dhx.amidakuji.ui.AmidakujiMain;

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

public class Main {
	public static void main(String[] args) {
		AmidakujiMain amidakuji = new AmidakujiMain();
		AmidakujiMain.width = 1080;
		AmidakujiMain.height = 720;
		
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Amidakuji Version 0.4.3";
		cfg.useGL20 = true;
		cfg.width = AmidakujiMain.width;
		cfg.height = AmidakujiMain.height;
		
		
		if(System.getProperty("os.name").toLowerCase().indexOf("mac os x") != -1) {
			System.setProperty("apple.laf.useScreenMenuBar", "true");
			System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Amidakuji");
			
			Application app = Application.getApplication();
			app.setDockIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/amidakuji_mac.png")));
		} else {
			cfg.addIcon("amidakuji.png", FileType.Internal);
			cfg.addIcon("amidakuji_lin.png", FileType.Internal);
		}
		
		new LwjglApplication(amidakuji, cfg);
	}
}
