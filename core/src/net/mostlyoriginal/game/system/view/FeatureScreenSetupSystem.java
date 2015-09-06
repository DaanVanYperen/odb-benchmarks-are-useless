package net.mostlyoriginal.game.system.view;

import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import net.mostlyoriginal.api.component.graphics.Tint;
import net.mostlyoriginal.api.system.core.PassiveSystem;
import net.mostlyoriginal.game.component.detection.OdbFeatureComponent;
import net.mostlyoriginal.game.system.detection.OdbFeatureDetectionSystem;
import net.mostlyoriginal.game.system.logic.TransitionSystem;
import net.mostlyoriginal.game.util.Anims;
import net.mostlyoriginal.game.util.MyEntityEditor;
import net.mostlyoriginal.odb.Odb;

import static net.mostlyoriginal.api.operation.OperationFactory.*;
import static net.mostlyoriginal.api.utils.Duration.seconds;

/**
 * @author Daan van Yperen
 */
@Wire
public class FeatureScreenSetupSystem extends PassiveSystem {

	public static final int FEATURE_BORDER_MARGIN = 1;
	public static final Tint COLOR_FEATURE_FADED = new Tint(0.8f, 1.0f, 1.0f, 0.3f);
	private final Tint TINT_FEATURE_FADED = new Tint(COLOR_FEATURE_FADED);
	public static final Tint COLOR_FEATURE_OFF = new Tint(0.8f, 1.0f, 1.0f, 0.0f);
	private final Tint TINT_FEATURE_OFF = new Tint(COLOR_FEATURE_OFF);
	public static final Tint COLOR_FEATURE_ON_OFF_COLOR = new Tint(0.8f, 1.0f, 1.0f, 1.0f);
	private final Tint TINE_FEATURE_ON_OFF = new Tint(COLOR_FEATURE_ON_OFF_COLOR);
	public static final Tint COLOR_FEATURE_ON = new Tint(1.0f, 1.0f, 1.0f, 1.0f);
	private final Tint TINT_FEATURE_ON = new Tint(COLOR_FEATURE_ON);
	public static final Tint COLOR_LOGO_FADED = new Tint(1.0f, 1.0f, 1.0f, 0.0f);
	public static final Tint COLOR_LOGO_FULL = new Tint(1.0f, 1.0f, 1.0f, 1.0f);
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
			MyEntityEditor.instance()
					.edit(entity)
					.tint(COLOR_FEATURE_OFF)
					.schedule(
							sequence(
									delay(seconds(0.5f + iconIndex * 0.1f)),
									tween(TINT_FEATURE_OFF, TINE_FEATURE_ON_OFF, seconds(2)),
									tween(TINE_FEATURE_ON_OFF, TINT_FEATURE_ON, seconds(2))
							));
		} else {
			MyEntityEditor.instance()
					.edit(entity)
					.tint(COLOR_FEATURE_OFF)
					.schedule(
							sequence(
									delay(seconds(0.5f + iconIndex * 0.1f)),
									tween(TINT_FEATURE_OFF, TINT_FEATURE_FADED, seconds(2))
							));
		}
	}

	public void addLogo() {

		// approximate percentage of screen size with logo. Use rounded numbers to keep the logo crisp.

		final Entity entity = Anims.createCenteredAt(world,
				FeatureScreenAssetSystem.LOGO_WIDTH,
				FeatureScreenAssetSystem.LOGO_HEIGHT,
				"logo",
				Anims.scaleToScreenRounded(0.8f, FeatureScreenAssetSystem.LOGO_WIDTH));

		MyEntityEditor.instance().edit(entity)
				.tint(COLOR_LOGO_FADED)
				.schedule(
						tween(new Tint(COLOR_LOGO_FADED), new Tint(COLOR_LOGO_FULL), 2f, Interpolation.fade)
				);
	}

	public static final int DISPLAY_SECONDS = 2;


	private void scheduleTransitionToGameScreen() {
		world.getSystem(TransitionSystem.class).transition(Odb.class, DISPLAY_SECONDS);
	}

}
