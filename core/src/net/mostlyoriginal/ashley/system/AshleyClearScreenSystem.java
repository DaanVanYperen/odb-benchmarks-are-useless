package net.mostlyoriginal.ashley.system;

import com.artemis.BaseSystem;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;

/**
 * Clearing the screenc color buffer with GL.
 *
 * @author Daan van Yperen
 */
public class AshleyClearScreenSystem extends EntitySystem {

	private final Color color;

	public AshleyClearScreenSystem() {
		this(Color.BLACK);
	}

	public AshleyClearScreenSystem(Color color) {
		this.color = color;
	}

	@Override
	public void update(float deltaTime) {
		Gdx.gl.glClearColor(color.r, color.g,color.b,color.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

}
