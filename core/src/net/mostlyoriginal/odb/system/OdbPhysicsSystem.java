package net.mostlyoriginal.odb.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
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

	@Override
	protected void process(Entity e) {
		final OdbPos pos = mPos.get(e);
		final OdbVelocity velocity = mVelocity.get(e);

		pos.x += velocity.x * world.delta * 100f;
		pos.y += velocity.y * world.delta * 100f;
	}
}
