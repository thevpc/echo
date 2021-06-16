/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo;

import net.thevpc.common.i18n.Str;
import net.thevpc.common.props.PropertyType;
import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableList;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.api.Action;
import net.thevpc.echo.api.ActionEvent;

/**
 *
 * @author vpc
 */
public class ValuesMenu<T> extends Menu {

    private WritableList<T> values;
    private WritableValue<ValuesMenuRenderer<T>> renderer;
    private WritableValue<Action> action;
    private ActionImpl2<T> actionImpl;

    public ValuesMenu(PropertyType valueType, Action action, Str text, Application app) {
        super(text, app);
        init(valueType, action);
    }

    public ValuesMenu(PropertyType valueType, Action action, String id, Str text, Application app) {
        super(id, text, app);
        init(valueType, action);
    }

    public ValuesMenu(PropertyType valueType, Action action, Application app) {
        super(app);
        init(valueType, action);
    }

    private void init(PropertyType valueType, Action action) {
        this.values = Props.of("values").listOf(valueType);
        this.action = Props.of("action").valueOf(PropertyType.of(Action.class), action);
        this.renderer = Props.of("renderer").valueOf(PropertyType.of(ValuesMenuRenderer.class,valueType));
        this.actionImpl = new ActionImpl2(this);
        this.values().onChange(e -> {
            switch (e.eventType()) {
                case ADD: {
                    updateButtons();
                    rerenderButtons((int) e.index(), values().size());
                    break;
                }
                case REMOVE: {
                    updateButtons();
                    rerenderButtons((int) e.index(), values().size());
                    break;
                }
                case UPDATE: {
                    rerenderButtons((int) e.index(), (int) e.index() + 1);
                    break;
                }
                case REFRESH: {
                    rerenderButtons((int) e.index(), (int) e.index() + 1);
                    break;
                }
            }
        });
        locale().onChange(e -> rerenderButtons(0, values().size()));
        iconSet().onChange(e -> rerenderButtons(0, values().size()));
        iconConfig().onChange(e -> rerenderButtons(0, values().size()));
    }

    public WritableValue<ValuesMenuRenderer<T>> renderer() {
        return renderer;
    }

    public WritableList<T> values() {
        return values;
    }

    public WritableValue<Action> action() {
        return action;
    }

    private void updateButtons() {
        while (children().size() > values().size()) {
            Button oldButton = (Button) children().removeAt(children().size() - 1);
            oldButton.action().unset();
            oldButton.value().set(null);
        }
        while (children().size() < values().size()) {
            Button t = new Button(null, actionImpl, app());
            children().add(t);
        }
    }

    private void rerenderButtons(int from, int to) {
        ValuesMenuRenderer<T> r = renderer().get();
        if (r == null) {
            r = DefaultValuesMenuRenderer.defaultRenderer();
        }
        for (int index = from; index < to; index++) {
            Button b = (Button) children().get(index);
            T value = values().get(index);
            b.value().set(value);
            ValuesMenuRendererContext c = new ValuesMenuRendererContextImpl(this, index, value, b);
            r.render(c);
        }
    }

    private static class ActionImpl2<T> implements Action {

        private ValuesMenu<T> m;

        public ActionImpl2(ValuesMenu<T> m) {
            this.m = m;
        }

        @Override
        public void run(ActionEvent e) {
            Action act = m.action().get();
            if (act != null) {
                act.run(e);
            }
        }
    }

}
