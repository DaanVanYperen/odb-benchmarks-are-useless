package net.mostlyoriginal.odb.component;

import com.artemis.Component;
import com.artemis.annotations.PooledWeaver;

/**
 * @author Daan van Yperen
 */
@PooledWeaver
public class OdbTint extends Component {
	public float r=1f;
	public float g=1f;
	public float b=1f;
	public float a=1f;
}
