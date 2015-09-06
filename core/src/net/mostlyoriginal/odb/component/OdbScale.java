package net.mostlyoriginal.odb.component;

import com.artemis.Component;
import com.artemis.PooledComponent;
import com.artemis.annotations.PooledWeaver;

/**
 * @author Daan van Yperen
 */
public class OdbScale extends PooledComponent {
	public float scale = 1;

	@Override
	protected void reset() {
		scale=1;
	}
}
