package net.mostlyoriginal.odb.system;

import com.artemis.*;
import com.artemis.annotations.Wire;
import com.artemis.io.SaveFileFormat;
import com.artemis.managers.WorldSerializationManager;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import net.mostlyoriginal.api.system.camera.CameraSystem;
import net.mostlyoriginal.game.Shared;
import net.mostlyoriginal.odb.component.*;

import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

/**
 * @author Daan van Yperen
 */
@Wire
public class OdbParticleSystem extends EntityProcessingSystem {

	private SpriteBatch batch;
	protected CameraSystem cameraSystem;
	private TextureRegion particleTexture;

	protected ComponentMapper<OdbPos> mOdbPos;
	protected ComponentMapper<OdbVelocity> mOdbVelocity;
	protected ComponentMapper<OdbScale> mScale;
	protected ComponentMapper<OdbTint> mTint;
	private float age;

	public OdbParticleSystem() {
		super(Aspect.all(OdbParticle.class, OdbPos.class, OdbVelocity.class));
	}

	@Override
	protected void initialize() {
		super.initialize();
		particleTexture = Shared.instanceParticleTexture();
		batch = new SpriteBatch(Shared.PARTICLE_BATCH_SIZE);
	}

	@Override
	protected void end() {
		batch.end();
	}

	private boolean saved;
	private boolean loaded;

	@Override
	protected void begin() {
		batch.setProjectionMatrix(cameraSystem.camera.combined);
		batch.begin();

		if (Gdx.input.isKeyPressed(Input.Keys.S) && !saved) {
			save();
			clear();
			saved = true;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.L) && !loaded) {
			load();
			loaded = true;
		}
	}

	private String toString(EntitySubscription subscription) {
		StringWriter writer = new StringWriter();
		SaveFileFormat save = new SaveFileFormat(subscription.getEntities());
		world.getManager(WorldSerializationManager.class).save(writer, save);
		return writer.toString();
	}

	private void save() {
		EntitySubscription allEntities = world.getManager(AspectSubscriptionManager.class).get(Aspect.all());

		final FileWriter writer;
		try {
			Preferences preferences = Gdx.app.getPreferences("game");
			preferences.putString("world", toString(allEntities));
			preferences.flush();
		} catch (Exception e) {
			Gdx.app.error("MyTag", "Save Failed", e);
		}
	}

	private void clear() {
		EntitySubscription allEntities = world.getManager(AspectSubscriptionManager.class).get(Aspect.all());
		IntBag entities = allEntities.getEntities();
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = world.getEntity(entities.get(i));
			entity.deleteFromWorld();
		}
	}

	private void load() {
		loaded = true;
		try {
			String json = Gdx.app.getPreferences("game").getString("world");
			ByteArrayInputStream is = new ByteArrayInputStream(
					json.getBytes("UTF-8"));
			SaveFileFormat load = world.getManager(WorldSerializationManager.class).load(is, SaveFileFormat.class);
		} catch (Exception e) {
			Gdx.app.error("MyTag", "Load Failed", e);
		}
	}

	@Override
	protected void process(Entity e) {

		final OdbPos p = mOdbPos.get(e);
		//final OdbVelocity v = mOdbVelocity.get(e);

		float scale = mScale.get(e).scale;

		if (mTint.has(e)) {
			final OdbTint tint = mTint.get(e);
			batch.setColor(tint.r, tint.g, tint.b, tint.a);
		}

		batch.draw(particleTexture,
				p.x - scale * 0.5f,
				p.y - scale * 0.5f,
				scale,
				scale);
	}

}
