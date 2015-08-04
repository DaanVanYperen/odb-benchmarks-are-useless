package net.mostlyoriginal.odb.system;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import net.mostlyoriginal.Shared;
import net.mostlyoriginal.api.system.core.PassiveSystem;
import net.mostlyoriginal.odb.archetype.Particle;

/**
 * @author Daan van Yperen
 */
@Wire
public class OdbSetupSystem extends PassiveSystem {

	Particle particle;

	@Override
	protected void initialize() {
		final int w = Gdx.graphics.getWidth();
		final int h = Gdx.graphics.getHeight();

		for(int i=0;i< Shared.PARTICLE_COUNT; i++) {
			particle.pos(MathUtils.random(0, w),
					MathUtils.random(0, h)).velocity(MathUtils.random(-0.5f, 0.5f),
					MathUtils.random(-0.5f, 0.5f)).create();
		}
	}
}
