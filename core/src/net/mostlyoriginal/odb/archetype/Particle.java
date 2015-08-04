package net.mostlyoriginal.odb.archetype;

import com.artemis.EntityFactory;
import com.artemis.annotations.Bind;
import net.mostlyoriginal.api.component.basic.Pos;
import net.mostlyoriginal.odb.component.OdbParticle;
import net.mostlyoriginal.odb.component.OdbPos;
import net.mostlyoriginal.odb.component.OdbVelocity;

/**
 * @author Daan van Yperen
 */
@Bind({OdbPos.class, OdbVelocity.class, OdbParticle.class})
public interface Particle extends EntityFactory<Particle> {

	@Bind(OdbPos.class) Particle pos(float x, float y);
	@Bind(OdbVelocity.class) Particle velocity(float x, float y);
}
