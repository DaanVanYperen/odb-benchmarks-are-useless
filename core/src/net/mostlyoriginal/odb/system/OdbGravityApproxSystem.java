package net.mostlyoriginal.odb.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import net.mostlyoriginal.odb.component.OdbPos;
import net.mostlyoriginal.odb.component.OdbScale;

/**
 * @author Daan van Yperen
 */
@Wire
public class OdbGravityApproxSystem extends EntityProcessingSystem {


	public OdbGravityApproxSystem() {
		super(Aspect.all(OdbScale.class, OdbPos.class));
	}

	public static final int LOW_DETAIL_SCALE = 4;
	protected ComponentMapper<OdbScale> mScale;
	protected ComponentMapper<OdbPos> mOdbPos;

	protected OdbGravitySystem gravitySystem;

	private Entity fly;

	public static final int divX = 40;
	public static final int divXL = divX / LOW_DETAIL_SCALE;
	public static final int divY = 40;
	public static final int divYL = divY / LOW_DETAIL_SCALE;

	public final float[][] gravH = new float[divX][divY];
	public final float[][] gravL = new float[divXL][divYL];

	protected IntBag overlappingEntities = new IntBag(1024);
	public float chunkW;
	public float chunkH;

	Vector2 tmp = new Vector2();
	public float chunkWL;
	public float chunkHL;

	boolean initialized=false;


	@Override
	protected boolean checkProcessing() {
		return true;
	}

	@Override
	protected void begin() {
		initialized=true;
		for (int x = 0; x < divXL; x++) {
			for (int y = 0; y < divYL; y++) {
				gravL[x][y] = 0;
			}
		}
		for (int x = 0; x < divX; x++) {
			for (int y = 0; y < divY; y++) {
				gravH[x][y] = 0;
			}
		}
	}

	@Override
	protected void process(Entity e) {

		final OdbPos pos = mOdbPos.get(e);

		int x = (int) pos.x / (int) chunkW;
		int y = (int) pos.y / (int) chunkH;

		if (x < 0 || y < 0 || x >= divX || y >= divY) return;

		final OdbScale scale = mScale.get(e);

		// distance from chunk center.
		tmp.set(x * chunkW + chunkW * 0.5f, y * chunkH + chunkH * 0.5f).sub(pos.x, pos.y);

		if (tmp.len() < chunkW) {
			gravH[x][y] += scale.scale;
		}

		int XL = x / LOW_DETAIL_SCALE;
		int YL = y / LOW_DETAIL_SCALE;

		// distance from large chunk center.
		tmp.set(XL * chunkWL + chunkWL * 0.5f, YL * chunkHL + chunkHL * 0.5f).sub(pos.x, pos.y);
		if (tmp.len() < chunkWL) {
			gravL[XL][YL] += scale.scale;
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
