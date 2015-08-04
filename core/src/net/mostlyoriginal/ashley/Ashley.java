package net.mostlyoriginal.ashley;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.Color;
import net.mostlyoriginal.Shared;
import net.mostlyoriginal.api.system.render.ClearScreenSystem;
import net.mostlyoriginal.ashley.common.AshleyScreen;
import net.mostlyoriginal.ashley.system.AshleyCameraSystem;
import net.mostlyoriginal.ashley.system.AshleyClearScreenSystem;

/**
 * @author Daan van Yperen
 */
public class Ashley extends AshleyScreen {

	@Override
	protected void setupEngine(Engine engine) {
		engine.addSystem(new AshleyCameraSystem(Shared.ZOOM));
		engine.addSystem(new AshleyClearScreenSystem(Color.valueOf(Shared.BACKGROUND_COLOR_HEX)));
	}
}
