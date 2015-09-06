package net.mostlyoriginal.odb.component;

import com.artemis.Component;
import com.artemis.PooledComponent;
import com.artemis.annotations.PooledWeaver;

/**
 * @author Daan van Yperen
 */
public class OdbPos extends PooledComponent {
	public float x;
	public float y;
	public float dirty =0;

	@Override
	protected void reset() {
		x=0;y=0;dirty=0;
	}
}
