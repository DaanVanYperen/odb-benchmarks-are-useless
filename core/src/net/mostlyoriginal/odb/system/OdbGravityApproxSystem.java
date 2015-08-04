package net.mostlyoriginal.odb.system;

import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import net.mostlyoriginal.api.system.physics.GravitySystem;
import net.mostlyoriginal.odb.component.OdbPos;
import net.mostlyoriginal.odb.component.OdbScale;

/**
 * @author Daan van Yperen
 */
@Wire
public class OdbGravityApproxSystem extends BaseSystem {

	public static final int LOW_DETAIL_SCALE = 10;
	protected ComponentMapper<OdbScale> mScale;
	protected ComponentMapper<OdbPos> mOdbPos;

	protected OdbQtSystem qtSystem;
	protected OdbGravitySystem gravitySystem;

	private Entity fly;

	public static final int divX = 80;
	public static final int divXL = divX / LOW_DETAIL_SCALE;
	public static final int divY = 80;
	public static final int divYL = divY / LOW_DETAIL_SCALE;

	public final float[][] gravH = new float[divX][divY];
	public final float[][] gravL = new float[divXL][divYL];

	protected IntBag overlappingEntities = new IntBag(1024);
	public float chunkW;
	public float chunkH;

	Vector2 tmp = new Vector2();
	public float chunkWL;
	public float chunkHL;

	@Override
	protected void processSystem() {

		for (int x = 0; x < divXL; x++) {
			for (int y = 0; y < divYL; y++) {
				gravL[x][y] = 0;
			}
		}
		for (int x = 0; x < divX; x++) {
			for (int y = 0; y < divY; y++) {

				int XL = x / LOW_DETAIL_SCALE;
				int YL = y / LOW_DETAIL_SCALE;

				overlappingEntities.setSize(0);
				final IntBag bag = qtSystem.getQuadTree().get(overlappingEntities,
						chunkW * x,
						chunkH * y,
						chunkW,
						chunkH);

				gravH[x][y] = 0;

				final int[] data = bag.getData();
				for (int eid = 0, size = bag.size(); eid < size; eid++) {
					fly.id = data[eid];
					final OdbPos lPos = mOdbPos.get(fly);

					tmp.set(x*chunkW+chunkW*0.5f, y*chunkH + chunkH*0.5f).sub(lPos.x,lPos.y);

					if ( tmp.len() < chunkW) {
						gravH[x][y] += 1;
					}

					tmp.set(XL*chunkWL+chunkWL*0.5f, YL*chunkHL + chunkHL*0.5f).sub(lPos.x,lPos.y);
					if ( tmp.len() < chunkWL) {
						gravL[XL][YL] += 1;
					}
				}

			}
		}
	}

	@Override
	protected void initialize() {
		fly = gravitySystem.createFlyHack();
		chunkW = Gdx.graphics.getWidth() / divX;
		chunkH = Gdx.graphics.getHeight() / divY;

		chunkWL = Gdx.graphics.getWidth() / divXL;
		chunkHL = Gdx.graphics.getHeight() / divYL;
	}

}
