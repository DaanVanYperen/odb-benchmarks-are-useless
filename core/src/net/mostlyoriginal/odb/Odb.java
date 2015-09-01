package net.mostlyoriginal.odb;

import com.artemis.World;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import net.mostlyoriginal.Shared;
import net.mostlyoriginal.api.plugin.common.ArtemisPlugin;
import net.mostlyoriginal.api.plugin.extendedcomponentmapper.ExtendedComponentMapperPlugin;
import net.mostlyoriginal.api.screen.core.WorldScreen;
import net.mostlyoriginal.api.system.camera.CameraSystem;
import net.mostlyoriginal.api.system.render.ClearScreenSystem;
import net.mostlyoriginal.api.utils.builder.WorldConfigurationBuilder;
import net.mostlyoriginal.game.system.view.GameScreenAssetSystem;
import net.mostlyoriginal.game.system.view.GameScreenSetupSystem;
import net.mostlyoriginal.odb.system.*;
import net.mostlyoriginal.plugin.ProfilerPlugin;

/**
 * Example main game screen.
 *
 * @author Daan van Yperen
 */
public class Odb extends WorldScreen {

	@Override
	protected World createWorld() {
	return new World(new WorldConfigurationBuilder()
				.dependsOn(ProfilerPlugin.class)
				.dependsOn(ExtendedComponentMapperPlugin.class)
				.with(
						new CameraSystem(Shared.ZOOM),
						new ClearScreenSystem(Color.valueOf(Shared.BACKGROUND_COLOR_HEX)),
						new OdbSetupSystem(),
						new OdbPhysicsSystem(),

						new OdbGravityApproxSystem(),
						new OdbGravitySystem(),

						new OdbTintSystem(),
						new OdbParticleSystem()
				).build());
	}


}