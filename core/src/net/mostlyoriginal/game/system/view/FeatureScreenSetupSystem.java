package net.mostlyoriginal.game.system.view;

import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.Gdx;
import net.mostlyoriginal.api.component.graphics.Tint;
import net.mostlyoriginal.api.component.script.Schedule;
import net.mostlyoriginal.api.system.core.PassiveSystem;
import net.mostlyoriginal.game.component.detection.OdbFeatureComponent;
import net.mostlyoriginal.odb.Odb;
import net.mostlyoriginal.game.system.detection.OdbFeatureDetectionSystem;
import net.mostlyoriginal.game.system.logic.TransitionSystem;
import net.mostlyoriginal.game.util.Anims;

/**
 * @author Daan van Yperen
 */
@Wire
public class FeatureScreenSetupSystem extends PassiveSystem {

	public static final int FEATURE_BORDER_MARGIN = 1;
	public static final Tint Tint_FEATURE_FADED = new Tint(0.8f, 1.0f, 1.0f, 0.3f);
	public static final Tint Tint_FEATURE_OFF = new Tint(0.8f, 1.0f, 1.0f, 0.0f);
	public static final Tint Tint_FEATURE_ON_OFF_Tint = new Tint(0.8f, 1.0f, 1.0f, 1.0f);
	public static final Tint Tint_FEATURE_ON = new Tint(1.0f, 1.0f, 1.0f, 1.0f);
	public static final Tint Tint_LOGO_FADED = new Tint(1.0f, 1.0f, 1.0f, 0.0f);
	public static final Tint Tint_LOGO_FULL = new Tint(1.0f, 1.0f, 1.0f, 1.0f);
	FeatureScreenAssetSystem assetSystem;
	TagManager tagManager;

	private int iconIndex;


	@Override
	protected void initialize() {
		super.initialize();

		addBackground();
		addLogo();

		final Entity featureEntity = tagManager.getEntity(OdbFeatureDetectionSystem.FEATURES_TAG);
		final OdbFeatureComponent featureComponent = featureEntity.getComponent(OdbFeatureComponent.class);

		addFeatureIcon(featureComponent.isHotspotOptimization, "feature-hotspot");
		addFeatureIcon(featureComponent.isPacked, "feature-packed");
		addFeatureIcon(featureComponent.isPooled, "feature-pooled");
		addFeatureIcon(featureComponent.isFactory, "feature-factory");

		scheduleTransitionToGameScreen();
	}

	private void addBackground() {

		// scale to fit.
		final float widthScale = Gdx.graphics.getWidth() / FeatureScreenAssetSystem.FEATURE_WIDTH;
		final float heightScale = Gdx.graphics.getHeight() / FeatureScreenAssetSystem.FEATURE_HEIGHT;

		Anims.createAnimAt(world,
				0,
				0,
				"background",
				Math.max(heightScale, widthScale));
	}

	private void addFeatureIcon(boolean state, String iconId) {

		final float scale = Anims.scaleToScreenRounded(0.08f, FeatureScreenAssetSystem.FEATURE_WIDTH);
		final float iconBorderMargin = scale * FEATURE_BORDER_MARGIN;
		final float iconOffset = ((scale * FeatureScreenAssetSystem.FEATURE_WIDTH) + iconBorderMargin);
		final Entity entity = Anims.createAnimAt(world,
				(int) (Gdx.graphics.getWidth() - iconOffset * ++iconIndex),
				(int) iconBorderMargin,
				iconId,
				scale);

		if (state) {
			entity.edit()
					.add(new Tint(Tint_FEATURE_OFF))
					.add(new Schedule()
							.wait(0.5f + iconIndex * 0.1f)
							.tween(Tint_LOGO_FADED, Tint_LOGO_FULL, 2f)
							.wait(1.0f / 2.0f)
							.tween(Tint_FEATURE_ON_OFF_Tint, Tint_FEATURE_ON, 4.0f));
		} else {
			entity.edit()
					.add(new Tint(Tint_FEATURE_OFF))
					.add(new Schedule()
							.wait(0.5f + iconIndex * 0.1f)
							.tween(Tint_FEATURE_OFF, Tint_FEATURE_FADED, 2f));
		}
	}

	public void addLogo() {

		// approximate percentage of screen size with logo. Use rounded numbers to keep the logo crisp.

		final Entity entity = Anims.createCenteredAt(world,
				FeatureScreenAssetSystem.LOGO_WIDTH,
				FeatureScreenAssetSystem.LOGO_HEIGHT,
				"logo",
				Anims.scaleToScreenRounded(0.8f, FeatureScreenAssetSystem.LOGO_WIDTH));

		entity.edit()
				.add(new Tint(Tint_LOGO_FADED))
				.add(new Schedule()
						.tween(Tint_LOGO_FADED, Tint_LOGO_FULL,2f)
						.wait(0.5f));
	}

	public static final int DISPLAY_SECONDS = 2;

	private void scheduleTransitionToGameScreen() {
		world.getSystem(TransitionSystem.class).transition(Odb.class, DISPLAY_SECONDS);
	}

}
