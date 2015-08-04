package net.mostlyoriginal.odb.component;

import com.artemis.Component;
import com.artemis.annotations.PooledWeaver;

/**
 * @author Daan van Yperen
 */
@PooledWeaver
public class OdbPos extends Component {
	public float x;
	public float y;
}
