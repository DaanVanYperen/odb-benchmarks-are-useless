package net.mostlyoriginal.odb.system;

import com.artemis.annotations.Wire;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import net.mostlyoriginal.Shared;
import net.mostlyoriginal.api.system.core.PassiveSystem;
import net.mostlyoriginal.odb.archetype.Particle;

/**
 * @author Daan van Yperen
 */
@Wire
public class OdbSetupSystem extends PassiveSystem {

	Particle particle;

	Vector2 tmp = new Vector2();

	@Override
	protected void initialize() {
		final int w = Gdx.graphics.getWidth();
		final int h = Gdx.graphics.getHeight();

		for (int i = 0; i < Shared.PARTICLE_COUNT; i++) {

			tmp
					.set(MathUtils.random(-Shared.VP_WIDTH*0.4f, Shared.VP_WIDTH*0.4f), 0)
					.rotate(MathUtils.random(360f))
					.add(Shared.VP_WIDTH * 0.5f, Shared.VP_HEIGHT * 0.5f);

			particle
					.pos(tmp.x, tmp.y)
					.scale(MathUtils.random(0.5f, 4f)).create();

		}
	}
}
