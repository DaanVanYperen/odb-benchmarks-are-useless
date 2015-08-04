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
import net.mostlyoriginal.odb.component.OdbTint;
import net.mostlyoriginal.odb.component.OdbVelocity;

/**
 * @author Daan van Yperen
 */
@Wire
public class OdbTintSystem extends EntityProcessingSystem {

	protected ComponentMapper<OdbPos> mPos;
	protected ComponentMapper<OdbVelocity> mVelocity;
	protected ComponentMapper<OdbTint> mTint;

	public OdbTintSystem() {
		super(Aspect.all(OdbTint.class, OdbParticle.class, OdbPos.class, OdbVelocity.class));
	}

 	Vector2 tmp = new Vector2();

	@Override
	protected void process(Entity e) {
		final OdbPos pos = mPos.get(e);
		final OdbVelocity velocity = mVelocity.get(e);
		final OdbTint tint = mTint.get(e);

		tmp.set(velocity.x,velocity.y);

		tint.a = MathUtils.clamp(tmp.len() / 5f, 0.3f, 1f);
	}
}
