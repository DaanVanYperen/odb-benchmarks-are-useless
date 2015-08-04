package net.mostlyoriginal.odb.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.math.Vector2;
import net.mostlyoriginal.Shared;
import net.mostlyoriginal.odb.component.*;

/**
 * @author Daan van Yperen
 */
@Wire
public class OdbGravitySystem extends EntityProcessingSystem {

	public static final int HIGH_DETAIL_MAX_DISTANCE = 32;
	public static final int LOW_DETAIL_MAX_DISTANCE = 256;
	protected ComponentMapper<OdbPos> mPos;
	protected ComponentMapper<OdbVelocity> mVelocity;
	protected ComponentMapper<OdbScale> mScale;
	protected ComponentMapper<OdbTint> mOdbTint;

	protected OdbGravityApproxSystem gravityApproxSystem;
	private boolean applyLowDetailGravity = true;

	public OdbGravitySystem() {
		super(Aspect.all(OdbParticle.class, OdbPos.class, OdbVelocity.class));
	}

	protected IntBag overlappingEntities = new IntBag(1024);

	@Override
	protected void initialize() {
		super.initialize();
	}

	public Entity createFlyHack() {
		return createFlyweightEntity();
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

	OdbPos tmpPos = new OdbPos();

	@Override
	protected void process(Entity e) {
		final OdbPos pos = mPos.get(e);
		final OdbVelocity velocity = mVelocity.get(e);

		// tint by proximity.
		final OdbTint tint = mOdbTint.get(e);
		tint.g = 0;
		tint.b = 1f;


		// add attraction to center of screen.

		affectParticle(pos, velocity, tint, 400, true, Shared.VP_WIDTH/2,Shared.VP_HEIGHT/2);

		// apply low detail long distance gravity field.

		if (applyLowDetailGravity) {
			int x1 = (int) ((pos.x - LOW_DETAIL_MAX_DISTANCE) / (float)gravityApproxSystem.chunkWL + 0.5f);
			int y1 = (int) ((pos.y - LOW_DETAIL_MAX_DISTANCE) / (float)gravityApproxSystem.chunkHL + 0.5f);
			int x2 = (int) ((pos.x + LOW_DETAIL_MAX_DISTANCE) / (float)gravityApproxSystem.chunkWL + 0.5f);
			int y2 = (int) ((pos.y + LOW_DETAIL_MAX_DISTANCE) / (float)gravityApproxSystem.chunkHL + 0.5f);

			if (x1 < 0) x1 = 0;
			if (y1 < 0) y1 = 0;
			if (x2 > OdbGravityApproxSystem.divXL) x2 = OdbGravityApproxSystem.divXL;
			if (y2 > OdbGravityApproxSystem.divYL) y2 = OdbGravityApproxSystem.divYL;

			for (int x = x1; x < x2; x++) {
				for (int y = y1; y < y2; y++) {
					if (gravityApproxSystem.gravL[x][y] == 0)
						continue;

					tmpPos.x = x * gravityApproxSystem.chunkWL + gravityApproxSystem.chunkWL * 0.5f;
					tmpPos.y = y * gravityApproxSystem.chunkHL + gravityApproxSystem.chunkHL * 0.5f;
					tmp.set(tmpPos.x, tmpPos.y).sub(pos.x, pos.y);
					if (tmp.len() > HIGH_DETAIL_MAX_DISTANCE && tmp.len() <= LOW_DETAIL_MAX_DISTANCE) {
						affectParticle(pos, velocity, tint, gravityApproxSystem.gravL[x][y] * 0.5f, true, tmpPos.x, tmpPos.y);
					}
				}
			}
		}

		{
			int x1 = (int) ((pos.x - HIGH_DETAIL_MAX_DISTANCE) / (float)gravityApproxSystem.chunkW + 0.5f);
			int y1 = (int) ((pos.y - HIGH_DETAIL_MAX_DISTANCE) / (float)gravityApproxSystem.chunkH + 0.5f);
			int x2 = (int) ((pos.x + HIGH_DETAIL_MAX_DISTANCE) / (float)gravityApproxSystem.chunkW + 0.5f);
			int y2 = (int) ((pos.y + HIGH_DETAIL_MAX_DISTANCE) / (float)gravityApproxSystem.chunkH + 0.5f);

			if (x1 < 0) x1 = 0;
			if (y1 < 0) y1 = 0;
			if (x2 > OdbGravityApproxSystem.divX) x2 = OdbGravityApproxSystem.divX;
			if (y2 > OdbGravityApproxSystem.divY) y2 = OdbGravityApproxSystem.divY;

			for (int x = x1; x < x2; x++) {
				for (int y = y1; y < y2; y++) {
					if (gravityApproxSystem.gravH[x][y] == 0)
						continue;

					tmpPos.x = x * gravityApproxSystem.chunkW + gravityApproxSystem.chunkW * 0.5f;
					tmpPos.y = y * gravityApproxSystem.chunkH + gravityApproxSystem.chunkH * 0.5f;
					tmp.set(tmpPos.x, tmpPos.y).sub(pos.x, pos.y);
					if (tmp.len() <= HIGH_DETAIL_MAX_DISTANCE) {
						affectParticle(pos, velocity, tint, gravityApproxSystem.gravH[x][y] * 0.5f, true, tmpPos.x, tmpPos.y);
					}
				}
			}
		}

		if (tint.g > 1f) tint.g = 1f;
		if (tint.b < 0.2f) tint.b = 0.2f;
		tint.r = 1f - tint.b;

	}

	public void affectParticle(OdbPos pos, OdbVelocity velocity, OdbTint tint, float radius2, boolean ignoreDistance, float x, float y) {

		tmp.set(x, y).sub(pos.x, pos.y);

		final float dist = tmp.len();

		final float v = 1f / (float) Math.sqrt((dist/20f));

		if (dist < 32f) {
			tint.b -= (0.025f / dist) * radius2;
		}

		if (dist < 16f) {
			tint.g += (0.005f / dist) * radius2;
		}

		tmp.nor().scl(v * radius2 * world.delta);
		velocity.x += tmp.x;
		velocity.y += tmp.y;
	}
}
