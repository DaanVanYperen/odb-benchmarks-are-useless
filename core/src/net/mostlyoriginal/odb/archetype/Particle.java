package net.mostlyoriginal.odb.archetype;

import com.artemis.EntityFactory;
import com.artemis.annotations.Bind;
import javafx.scene.transform.Scale;
import net.mostlyoriginal.api.component.basic.Pos;
import net.mostlyoriginal.odb.component.*;

/**
 * @author Daan van Yperen
 */
@Bind({OdbPos.class, OdbVelocity.class, OdbParticle.class, OdbScale.class, OdbTint.class})
public interface Particle extends EntityFactory<Particle> {

	@Bind(OdbPos.class) Particle pos(float x, float y);
	@Bind(OdbVelocity.class) Particle velocity(float x, float y);
	@Bind(OdbScale.class) Particle scale(float scale);
}
