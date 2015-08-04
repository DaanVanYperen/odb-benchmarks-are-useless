package net.mostlyoriginal.odb.system;

import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Gdx;
import net.mostlyoriginal.api.system.physics.GravitySystem;
import net.mostlyoriginal.odb.component.OdbScale;

/**
 * @author Daan van Yperen
 */
@Wire
public class OdbGravityApproxSystem extends BaseSystem {

	protected ComponentMapper<OdbScale> mScale;

	protected OdbQtSystem qtSystem;
	protected OdbGravitySystem gravitySystem;

	private Entity fly;

	public static final int divX = 16;
	public static final int divY = 16;

	public final float[][] grav = new float[divX][divY];

	protected IntBag overlappingEntities = new IntBag(1024);
	public float chunkW;
	public float chunkH;

	@Override
	protected void processSystem() {

		for (int x = 0; x < divX; x++) {
			for (int y = 0; y < divY; y++) {

				overlappingEntities.setSize(0);
				final IntBag bag = qtSystem.getQuadTree().get(overlappingEntities,
						chunkW * x,
						chunkH * y,
						chunkW,
						chunkH);

				grav[x][y] = 0;

				final int[] data = bag.getData();
				for (int eid = 0, size = bag.size(); eid < size; eid++) {
					fly.id = data[eid];
					grav[x][y] += mScale.get(fly).scale;
				}

			}
		}
	}

	@Override
	protected void initialize() {
		fly = gravitySystem.createFlyHack();
		chunkW = Gdx.graphics.getWidth() / divX;
		chunkH = Gdx.graphics.getHeight() / divY;
	}

}
