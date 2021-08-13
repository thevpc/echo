package net.thevpc.echo.swing.peers;

import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppTemporalField;
import net.thevpc.echo.spi.peers.AppTemporalFieldPeer;
import net.thevpc.echo.swing.SwingPeerHelper;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.InternationalFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Time;
import java.text.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

public class SwingTemporalFieldPeer implements SwingPeer, AppTemporalFieldPeer {
    private AppTemporalField appTextField;
    private Mapper mapper;

    public SwingTemporalFieldPeer() {
    }

    private Mapper createMapper(Class cls) {
        switch (cls.getName()) {
            case "java.time.Instant": {
                return new Mapper() {
                    JXDatePicker jxDatePicker;

                    {
                        jxDatePicker = new JXDatePicker();
                        jxDatePicker.setFormats(formats());
                        jxDatePicker.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                Date d = jxDatePicker.getDate();
                                appTextField.value().set(
                                        d == null ? null : toEcho(d)
                                );
                            }
                        });
                    }

                    public void setValue(Temporal t) {
                        jxDatePicker.setDate(t == null ? null : toJX(t));
                    }

                    @Override
                    public JComponent component() {
                        return jxDatePicker;
                    }

                    public String[] formats() {
                        return new String[]{
                                "yyyy-MM-dd HH:mm:ss"
                        };
                    }

                    public Temporal toEcho(Date t) {
                        return t.toInstant();
                    }

                    public Date toJX(Temporal t) {
                        return Date.from((Instant) t);
                    }
                };
            }
            case "java.time.LocalDateTime": {
                return new Mapper() {
                    JXDatePicker jxDatePicker;

                    {
                        jxDatePicker = new JXDatePicker();
                        jxDatePicker.setFormats(formats());
                        jxDatePicker.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                Date d = jxDatePicker.getDate();
                                appTextField.value().set(
                                        d == null ? null : toEcho(d)
                                );
                            }
                        });
                    }

                    public void setValue(Temporal t) {
                        jxDatePicker.setDate(t == null ? null : toJX(t));
                    }

                    @Override
                    public JComponent component() {
                        return jxDatePicker;
                    }

                    public String[] formats() {
                        return new String[]{
                                "yyyy-MM-dd HH:mm:ss"
                        };
                    }

                    public Temporal toEcho(Date t) {
                        ZoneId defaultZoneId = ZoneId.systemDefault();
                        return LocalDateTime.ofInstant(t.toInstant(), defaultZoneId);
                    }

                    public Date toJX(Temporal t) {
                        ZoneId defaultZoneId = ZoneId.systemDefault();
                        return Date.from(((LocalDateTime) t).atZone(defaultZoneId).toInstant());
                    }
                };
            }
            case "java.time.LocalDate": {
                return new Mapper() {
                    private JXDatePicker jxDatePicker;

                    {
                        jxDatePicker = new JXDatePicker();
                        jxDatePicker.setFormats(formats());
                        jxDatePicker.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                Date d = jxDatePicker.getDate();
                                appTextField.value().set(
                                        d == null ? null : toEcho(d)
                                );
                            }
                        });
                    }

                    public void setValue(Temporal t) {
                        jxDatePicker.setDate(t == null ? null : toJX(t));
                    }

                    public JComponent component() {
                        return jxDatePicker;
                    }

                    public String[] formats() {
                        return new String[]{
                                "yyyy-MM-dd", "yyyy/MM/dd"
                        };
                    }

                    public Temporal toEcho(Date t) {
                        ZoneId defaultZoneId = ZoneId.systemDefault();
                        return t.toInstant().atZone(defaultZoneId).toLocalDate();
                    }

                    public Date toJX(Temporal t) {
                        LocalDate localDate = (LocalDate) t;
                        ZoneId defaultZoneId = ZoneId.systemDefault();
                        Instant i = localDate.atStartOfDay(defaultZoneId).toInstant();
                        return Date.from(i);
                    }
                };
            }
            case "java.time.LocalTime": {
                return new LocalTimeMapper();
            }
        }
        throw new IllegalArgumentException(getClass().getName() + ": not supported yet " + cls);
    }

    public void install(AppComponent component) {
        appTextField = (AppTemporalField) component;
        mapper = createMapper(appTextField.value().propertyClass());
        mapper.setValue((Temporal) appTextField.value().get());
        appTextField.value().onChange(() -> {
            mapper.setValue((Temporal) appTextField.value().get());
        });
        SwingPeerHelper.installComponent(appTextField, mapper.component());
    }

    @Override
    public Object toolkitComponent() {
        return mapper.component();
    }

    private interface Mapper {
        void setValue(Temporal t);

        JComponent component();
    }

    private static class MyAbstractFormatter extends JFormattedTextField.AbstractFormatter {
        String[] formats;

        public MyAbstractFormatter(String[] formats) {
            this.formats = formats;
        }

        @Override
        public Object stringToValue(String text) throws ParseException {
            if (text == null || text.trim().isEmpty()) {
                return null;
            }
            for (String s : formats) {
                try {
                    LocalTime time = LocalTime.parse((String) text.trim(), DateTimeFormatter.ofPattern(s));
                    return Time.valueOf(time);
                } catch (Exception ex) {
                    //
                }
            }
            throw new ParseException("invalid time:" + text, 0);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value == null) {
                return "";
            }
            return new SimpleDateFormat(formats[0]).format(value);
//            return DateTimeFormatter.ofPattern(formats[0]).format((TemporalAccessor) value);
        }
    }

    private static class LocalTimeSpinnerModel extends SpinnerDateModel/*AbstractSpinnerModel*/ {
        LocalTime time;
        String[] formats;
        SimpleDateFormat[] oformats;
        int calendarField;

        public LocalTimeSpinnerModel(String[] formats) {
            setCalendarField(Calendar.MINUTE);
            this.formats = formats;
            oformats = new SimpleDateFormat[formats.length];
            for (int i = 0; i < formats.length; i++) {
                oformats[i] = new SimpleDateFormat(formats[i]);
            }
        }

        @Override
        public int getCalendarField() {
            return calendarField;
        }

        public void setCalendarField0(int calendarField) {
            this.calendarField = calendarField;
        }

        public void setCalendarField(int calendarField) {
            this.calendarField = calendarField;
            super.setCalendarField(calendarField);
        }

        public SimpleDateFormat getFormat() {
            return oformats[0];
        }

        @Override
        public Object getNextValue() {
            LocalTime time0 = time;
            if (time0 != null) {
                time0 = incTime(time0,1);
                return time0;
            }
            return new Time(System.currentTimeMillis());
//            return time0;
        }

        private LocalTime incTime(LocalTime time0,int count) {
            switch (getCalendarField()){
                case Calendar.HOUR_OF_DAY:
                case Calendar.HOUR:
                {
                    time0 = time0.plusHours(count);
                    break;
                }
                case Calendar.MINUTE:{
                    time0 = time0.plusMinutes(count);
                    break;
                }
                case Calendar.SECOND:{
                    time0 = time0.plusSeconds(count);
                    break;
                }
                case Calendar.MILLISECOND:{
                    time0 = time0.plusMinutes(1);
                    break;
                }
            }
            return time0;
        }

        @Override
        public Object getPreviousValue() {
            LocalTime time0 = time;
            if (time0 != null) {
                time0 = incTime(time0,-1);
                return time0;
            }
            return new Time(System.currentTimeMillis());
//            return time0;
        }

        @Override
        public Object getValue() {
            return time == null ? null : Time.valueOf(time);
        }

        @Override
        public void setValue(Object value) {
            LocalTime newValue = parseObjectValue(value);
            if (!Objects.equals(this.time, newValue)) {
                this.time = newValue;
                fireStateChanged();
            }
        }

        private LocalTime parseObjectValue(Object value) {
            if (value == null) {
                return null;
            } else if (value instanceof LocalTime) {
                return (LocalTime) value;
            } else if (value instanceof Date) {
                return new Time(((Date) value).getTime()).toLocalTime();
            } else if (value instanceof String) {
                for (String s : formats) {
                    try {
                        return LocalTime.parse((String) value, DateTimeFormatter.ofPattern(s));
                    } catch (Exception ex) {
                        //
                    }
                }
                throw new IllegalArgumentException("unable to parse time : " + value);
            } else {
                throw new IllegalArgumentException("unsupported time from type : " + (value.getClass().getName()));
            }
        }
    }

    private class LocalTimeMapper implements Mapper {
        JSpinner spinner;

        LocalTimeMapper() {
            spinner = new LocalTimeSpinner(new String[]{"HH:mm:ss", "HH:mm:ss.SSS", "HH:mm"});
//                        JFormattedTextField timeField = new JFormattedTextField(new SimpleDateFormat("HH:mm:ss"));
//                        spinner.setEditor(timeField);
            spinner.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    Date dd = (Date) spinner.getValue();
                    if (dd != null) {
                        Time t = new Time(dd.getTime());
                        appTextField.value().set(t.toLocalTime());
                    } else {
                        appTextField.value().set(null);
                    }
                }
            });
        }

        public void setValue(Temporal t) {
            spinner.setValue(t);
        }

        public JComponent component() {
            return spinner;
        }

        private class LocalTimeSpinner extends JSpinner {
            private String[] formats;

            public LocalTimeSpinner(String[] formats) {
                super(new LocalTimeSpinnerModel(formats));
                this.formats = formats;
            }

            protected JComponent createEditor(SpinnerModel model) {
                DateEditor d = new DateEditor(this) {
                    @Override
                    public SimpleDateFormat getFormat() {
                        return new SimpleDateFormat(formats[0]);
                    }
                };
                LocalTimeSpinnerModel dmodel=(LocalTimeSpinnerModel)model;
                JFormattedTextField ftf = d.getTextField();
                ftf.addCaretListener(new CaretListener() {
                    @Override
                    public void caretUpdate(CaretEvent e) {
                        int d = e.getDot();
                        int ln = ((JFormattedTextField) e.getSource()).getDocument().getLength();
                        if(d>=0 && d< ln) {
                            try {
                                DateFormat.Field field = DateFormat.Field.ofCalendarField(dmodel.getCalendarField());
                                if (field != null) {
                                    try {
                                        int q = detectedField(ftf, d);
                                        if(q>=0){
                                            dmodel.setCalendarField0(q);
                                            return;
                                        }
                                    } catch (IllegalArgumentException iae) {
                                        //
                                    }
                                }
                            } catch (Exception ex) {
                                //
                            }
                        }
                        //dmodel.setCalendarField0(Calendar.MINUTE);
                    }

                    private int detectedField(JFormattedTextField ftf,int pos) {
//                        int max = ftf.getDocument().getLength();
                        AttributedCharacterIterator iterator=null;
                        for (String format : formats) {
                            try {
                                final SimpleDateFormat sdf = new SimpleDateFormat(format);
                                iterator= sdf.formatToCharacterIterator(sdf.parse(ftf.getText()));
                                break;
                            }catch (Exception ex){
                                //
                            }
                        }
                        if(iterator==null){
                            return -1;
                        }
                        iterator.first();
                        do {
                            Map<Object,Object> attrs = (Map) iterator.getAttributes();

                            if (attrs != null){
                                int[] _allFields = {
                                        Calendar.HOUR,
                                        Calendar.HOUR_OF_DAY,
                                        Calendar.MINUTE,
                                        Calendar.SECOND,
                                        Calendar.MILLISECOND,
                                };
                                for (int i = 0; i < _allFields.length; i++) {
                                    DateFormat.Field field = DateFormat.Field.ofCalendarField(_allFields[i]);
                                    if(attrs.containsKey(field)) {
                                        int start = iterator.getRunStart(field);
                                        int end = iterator.getRunLimit(field);

                                        if (start != -1 && end != -1 &&
                                                start <= pos && end >= pos
                                        ) {
                                            return field.getCalendarField();
                                        }
                                    }
                                }
                            }
                        } while (iterator.next() != CharacterIterator.DONE);
                        return -1;
                    }
                });
                ftf.setEditable(true);
                ftf.setHorizontalAlignment(JTextField.LEADING);
                ftf.addPropertyChangeListener("horizontalAlignment",new PropertyChangeListener() {
                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        if(evt.getNewValue()!=null){
                            int n = (int) evt.getNewValue();
                            if(n!=JTextField.LEADING){
                                ftf.setHorizontalAlignment(JTextField.LEADING);
                            }
                        }
                    }
                });
                ftf.setFormatterFactory(new JFormattedTextField.AbstractFormatterFactory() {
                    @Override
                    public JFormattedTextField.AbstractFormatter getFormatter(JFormattedTextField tf) {
                        return new MyAbstractFormatter(formats);
                    }
                });

                return d;
            }
        }
    }
}
