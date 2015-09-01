package net.mostlyoriginal.game;

import com.badlogic.gdx.Game;
import net.mostlyoriginal.odb.Odb;

public class GdxArtemisGame extends Game {

	private static GdxArtemisGame instance;

	@Override
	public void create() {
		instance = this;
		restart();
	}

	public void restart() {
		setScreen(new Odb());
	}

	public static GdxArtemisGame getInstance()
	{
		return instance;
	}
}
