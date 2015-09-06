package net.mostlyoriginal.odb.component;

import com.artemis.Component;
import com.artemis.PooledComponent;
import com.artemis.annotations.PooledWeaver;

/**
 * @author Daan van Yperen
 */
public class OdbTint extends PooledComponent {
	public float r=1f;
	public float g=1f;
	public float b=1f;
	public float a=1f;

	@Override
	protected void reset() {
		r=1f;g=1f;b=1f;a=1f;
	}
}
