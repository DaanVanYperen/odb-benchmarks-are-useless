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
					.set(MathUtils.random(-Shared.VP_WIDTH * 0.4f, Shared.VP_WIDTH * 0.4f), 0)
					.rotate(MathUtils.random(360f))
					.add(Shared.VP_WIDTH * 0.5f, Shared.VP_HEIGHT * 0.5f);

			final Particle particle = this.particle
					.pos(tmp.x, tmp.y);

			tmp.sub(Shared.VP_WIDTH * 0.5f, Shared.VP_HEIGHT * 0.5f).rotate90(-1).nor();

			final float len = tmp.len();
			//tmp.nor().scl(len*len);

			particle.velocity(tmp.x,tmp.y)
					.scale(randomScale()).create();


		}
	}

	private float randomScale() {
		if (MathUtils.random(100f) < 0.1f )
			return MathUtils.random(8f,16f);
		if (MathUtils.random(100) < 1 )
			return MathUtils.random(5f,8f);
		if (MathUtils.random(100) < 5 )
			return MathUtils.random(3f,5f);
		return MathUtils.random(1f, 3f);
	}
}
