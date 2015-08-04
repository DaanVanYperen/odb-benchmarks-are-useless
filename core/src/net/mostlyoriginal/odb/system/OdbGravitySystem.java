package net.mostlyoriginal.odb.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import net.mostlyoriginal.Shared;
import net.mostlyoriginal.odb.component.*;

/**
 * @author Daan van Yperen
 */
@Wire
public class OdbGravitySystem extends EntityProcessingSystem {

	protected ComponentMapper<OdbPos> mPos;
	protected ComponentMapper<OdbVelocity> mVelocity;
	protected ComponentMapper<OdbScale> mScale;
	protected ComponentMapper<OdbTint> mOdbTint;

	protected OdbQtSystem qtSystem;
	private Entity fly;

	public OdbGravitySystem() {
		super(Aspect.all(OdbParticle.class, OdbPos.class, OdbVelocity.class));
	}

	protected IntBag overlappingEntities = new IntBag(1024);

	@Override
	protected void initialize() {
		super.initialize();
		fly = createFlyweightEntity();
	}

	public static float invSqrt(float x) {
		float xhalf = 0.5f * x;
		int i = Float.floatToIntBits(x);
		i = 0x5f3759df - (i >> 1);
		x = Float.intBitsToFloat(i);
		x = x * (1.5f - xhalf * x * x);
		return x;
	}

	Vector2 tmp = new Vector2();

	@Override
	protected void process(Entity e) {
		final OdbPos pos = mPos.get(e);
		final OdbVelocity velocity = mVelocity.get(e);
		final float diameter = mScale.get(e).scale / 2f;

		overlappingEntities.setSize(0);
		qtSystem.getQuadTree().get(overlappingEntities,
				pos.x - Shared.GRAVITY_DISTANCE - diameter,
				pos.y - Shared.GRAVITY_DISTANCE - diameter,
				Shared.GRAVITY_DISTANCE * 2f + diameter * 2f,
				Shared.GRAVITY_DISTANCE * 2f + diameter * 2f);

		// tint by proximity.
		final OdbTint tint = mOdbTint.get(e);
		tint.g=0;
		tint.b=1f;


		final int[] data = overlappingEntities.getData();
		for (int i = 0, s = overlappingEntities.size(); i < s; i++) {
			fly.id = data[i];

			// don't influence self.
			if ( fly.id == e.id )
				continue;

			final OdbPos pos2 = mPos.get(fly);
			final float diameter2 = mScale.get(e).scale / 2f;
			tmp.set(pos2.x, pos2.y).sub(pos.x, pos.y);
			final float dist = tmp.len();

			tint.b -= 0.01f/dist;

			if (dist < Shared.GRAVITY_DISTANCE) {
				tmp.nor().scl(invSqrt(dist)*diameter2 * 0.01f)
						 .scl(world.delta)
  		  				 .add(velocity.x,velocity.y)
 						 .clamp(0f,10f);
				velocity.x = tmp.x;
				velocity.y = tmp.y;
			}
		}

		if ( tint.b < 0.2f ) tint.b = 0.2f;
		tint.r = 1f - tint.b;

	}
}
