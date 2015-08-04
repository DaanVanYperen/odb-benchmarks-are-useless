package net.mostlyoriginal.game;

import com.badlogic.gdx.Game;
import net.mostlyoriginal.ashley.Ashley;
import net.mostlyoriginal.game.screen.detection.OdbFeatureScreen;
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
