package net.thevpc.echo.swing.peers;

import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppNumberField;
import net.thevpc.echo.spi.peers.AppNumberFieldPeer;
import net.thevpc.echo.swing.SwingPeerHelper;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.JTextComponent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;
import java.math.BigInteger;

public class SwingNumberFieldPeer implements SwingPeer, AppNumberFieldPeer {
    private JComponent jNumberField;
    private AppNumberField appNumberField;

    public SwingNumberFieldPeer() {
    }

    public void install(AppComponent component) {
        appNumberField =(AppNumberField) component;
        boolean prim=appNumberField.valueType().isPrimitive();
        switch (appNumberField.valueType().getName()){
            case "byte":
            case "java.lang.Byte":{
                prepare((byte)0,Byte.MIN_VALUE,Byte.MAX_VALUE,(byte)1,prim);
                break;
            }
            case "short":
            case "java.lang.Short":{
                prepare((short)0,Short.MIN_VALUE,Short.MAX_VALUE,(short)1,prim);
                break;
            }
            case "int":
            case "java.lang.Integer":{
                prepare(0,Integer.MIN_VALUE,Integer.MAX_VALUE,1,prim);
                break;
            }
            case "long":
            case "java.lang.Long":{
                prepare(0L,Long.MIN_VALUE,Long.MAX_VALUE,1L,prim);
                break;
            }
            case "double":
            case "java.lang.Double":{
                prepare(0.0,Double.MIN_VALUE,Double.MAX_VALUE,1.0,prim);
                break;
            }
            case "float":
            case "java.lang.float":{
                prepare(0.0f,Float.MIN_VALUE,Float.MAX_VALUE,1.0f,prim);
                break;
            }
            case "java.math.BigDecimal":{
                prepare(new BigDecimal("0"),null,null,new BigDecimal("1"),prim);
                break;
            }
            case "java.math.BigInteger":{
                prepare(new BigInteger("0"),null,null,new BigInteger("1"),prim);
                break;
            }
            default:{
                throw new IllegalArgumentException("unsupported number type "+appNumberField.valueType().getName());
            }
        }
    }

    private void prepare(Number zero0,Number min0,Number max0,Number step0,boolean prim){
        Number min = (Number) appNumberField.min().get();
        Number max = (Number) appNumberField.max().get();
        Number step = (Number) appNumberField.step().get();
        Number value = (Number) appNumberField.value().get();
        JSpinner jSpinner = new JSpinner(new SpinnerNumberModel(
                value==null?zero0:value,
                (Comparable)(min==null?min0:min),
                (Comparable)(max==null?max0:max),
                step==null?step0:value
        ));
        jSpinner.addChangeListener(e -> {
            Number value1 = (Number) jSpinner.getValue();
            appNumberField.value().set(value1 ==null?
                    (prim?zero0:null): value1.intValue()
            );
        });
        this.jNumberField = jSpinner;
        SwingPeerHelper.installComponent(appNumberField,this.jNumberField);
        Runnable cc=()->{
            JSpinner jSpinner2=(JSpinner) appNumberField;
            Number value2 = (Number) appNumberField.value().get();
            SpinnerNumberModel m2 = new SpinnerNumberModel(
                    value2 == null ? zero0 : value2,
                    (Comparable) (min == null ? min0 : min),
                    (Comparable) (max == null ? max0 : max),
                    step == null ? step0 : value
            );
            jSpinner2.setModel(m2);
        };
        appNumberField.min().onChange(cc);
        appNumberField.max().onChange(cc);
        appNumberField.step().onChange(cc);
    }

    @Override
    public Object toolkitComponent() {
        return jNumberField;
    }

}
