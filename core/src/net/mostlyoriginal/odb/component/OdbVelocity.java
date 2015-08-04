package net.mostlyoriginal.odb.component;

import com.artemis.Component;
import com.artemis.annotations.PooledWeaver;
import com.badlogic.gdx.math.MathUtils;

/**
 * @author Daan van Yperen
 */
@PooledWeaver
public class OdbVelocity extends Component {
	public float x;
	public float y;
	public float z;

	public float angularMomentum = MathUtils.random(1f, 90f);
}
