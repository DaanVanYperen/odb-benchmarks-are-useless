package net.mostlyoriginal.game.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import net.mostlyoriginal.game.GdxArtemisGame;
import net.mostlyoriginal.game.Shared;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(Shared.VP_WIDTH, Shared.VP_HEIGHT);
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new GdxArtemisGame();
        }
}