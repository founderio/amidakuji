package dhx.amidakuji.gamelogic;

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

public class AmidakujiException extends RuntimeException {

	private static final long serialVersionUID = 7873396805927363907L;

	public AmidakujiException(String message) {
		super(message);
	}

}
