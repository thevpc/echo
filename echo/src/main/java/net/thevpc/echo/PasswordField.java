package net.thevpc.echo;

import net.thevpc.common.props.PropertyType;
import net.thevpc.common.props.WritableBoolean;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.api.components.AppPasswordField;
import net.thevpc.echo.api.components.AppPasswordStrengthEvaluator;
import net.thevpc.echo.impl.components.TextBase;
import net.thevpc.echo.spi.peers.AppPasswordFieldPeer;

public class PasswordField extends TextBase implements AppPasswordField {
    private WritableBoolean unmasked;
    private WritableBoolean unmaskable;
    private WritableBoolean enableStrengthEvaluation;
    private WritableValue<AppPasswordStrengthEvaluator> strengthEvaluator;

    public PasswordField(String id, Application app) {
        super(id, null, app, AppPasswordField.class, AppPasswordFieldPeer.class);
        unmasked = AppProps.of("unmasked", app()).booleanOf(false);
        unmaskable = AppProps.of("unmaskable", app()).booleanOf(false);
        enableStrengthEvaluation = AppProps.of("enableStrengthEvaluation", app()).booleanOf(false);
        strengthEvaluator = AppProps.of("strengthEvaluator", app())
                .valueOf(PropertyType.of(AppPasswordStrengthEvaluator.class), null);
        propagateEvents(unmasked, unmaskable, enableStrengthEvaluation, strengthEvaluator);
    }

    public PasswordField(Application app) {
        this(null, app);
    }

    @Override
    public WritableBoolean unmasked() {
        return unmasked;
    }

    @Override
    public WritableBoolean unmaskable() {
        return unmaskable;
    }

    @Override
    public WritableBoolean enableStrengthEvaluation() {
        return enableStrengthEvaluation;
    }

    @Override
    public WritableValue<AppPasswordStrengthEvaluator> strengthEvaluator() {
        return strengthEvaluator;
    }

}

