package net.mostlyoriginal.odb.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import net.mostlyoriginal.odb.component.OdbParticle;
import net.mostlyoriginal.odb.component.OdbPos;
import net.mostlyoriginal.odb.component.OdbVelocity;

/**
 * @author Daan van Yperen
 */
@Wire
public class OdbPhysicsSystem extends EntityProcessingSystem {

	protected ComponentMapper<OdbPos> mPos;
	protected ComponentMapper<OdbVelocity> mVelocity;

	public OdbPhysicsSystem() {
		super(Aspect.all(OdbParticle.class, OdbPos.class, OdbVelocity.class));
	}

	Vector2 tmp = new Vector2();

	@Override
	protected void process(Entity e) {
		final OdbPos pos = mPos.get(e);
		final OdbVelocity velocity = mVelocity.get(e);

		final float vx = velocity.x * world.delta * 25f;
		final float vy = velocity.y * world.delta * 25f;

		final float len = tmp.set(vx, vy).len();
		tmp.nor().scl(MathUtils.log2(len)).rotate(velocity.angularMomentum *world.delta);

		pos.x += tmp.x;
		pos.y += tmp.y;
		pos.dirty+=tmp.len();
	}
}
