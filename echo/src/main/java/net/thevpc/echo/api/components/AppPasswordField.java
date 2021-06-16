package net.thevpc.echo.api.components;

import net.thevpc.common.props.WritableBoolean;
import net.thevpc.common.props.WritableValue;

public interface AppPasswordField extends AppTextControl {
    WritableBoolean unmasked();
    WritableBoolean unmaskable();
    WritableBoolean enableStrengthEvaluation();
    WritableValue<AppPasswordStrengthEvaluator> strengthEvaluator();
}
