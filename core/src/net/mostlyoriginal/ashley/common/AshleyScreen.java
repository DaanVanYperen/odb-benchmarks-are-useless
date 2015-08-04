package net.mostlyoriginal.ashley.common;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.ashley.core.PooledEngine;

/**
 * @author Daan van Yperen
 */
public abstract class AshleyScreen implements Screen {

	public static final float MIN_DELTA = 1 / 15f;
	public static final int entityPoolInitialSize = 10000;
	public static final int entityPoolMaxSize = 1000000;
	public static final int componentPoolInitialSize = 10000;
	public static final int componentPoolMaxSize = 1000000;

	protected final Engine engine;

	public AshleyScreen() {
		engine = new PooledEngine(entityPoolInitialSize,
				entityPoolMaxSize,
				componentPoolInitialSize,
				componentPoolMaxSize);
		setupEngine(engine);
	}

	protected abstract void setupEngine(Engine engine);

	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		if ( engine == null ) {
			throw new RuntimeException("Engine not initialized.");
		}
		// Prevent spikes in delta from causing insane world updates.
		engine.update(MathUtils.clamp(delta, 0, MIN_DELTA));
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

	}

}
