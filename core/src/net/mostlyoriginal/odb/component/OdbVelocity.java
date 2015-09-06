package net.mostlyoriginal.odb.component;

import com.artemis.Component;
import com.artemis.PooledComponent;
import com.artemis.annotations.PooledWeaver;
import com.badlogic.gdx.math.MathUtils;

/**
 * @author Daan van Yperen
 */
public class OdbVelocity extends PooledComponent {
	public float x;
	public float y;
	public float z;

	public float angularMomentum = MathUtils.random(-90f, 90f);

	@Override
	protected void reset() {
		x=0;
		y=0;
		z=0;
		angularMomentum= MathUtils.random(-90f, 90f);
	}
}
