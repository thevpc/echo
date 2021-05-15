package net.thevpc.echo.swing.peers;

import net.thevpc.common.props.PropertyEvent;
import net.thevpc.common.props.PropertyListener;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppPanel;
import net.thevpc.echo.constraints.*;

import javax.swing.*;
import java.awt.*;

public class SwingPanelPeer implements SwingPeer {
    private JPanel jcomponent;
    private AppPanel panel;

    public SwingPanelPeer() {
    }

    public void install(AppComponent component) {
        if(jcomponent!=null){
            return;
        }
        this.panel = (AppPanel) component;
        this.jcomponent = new JPanel();
        this.panel.constraints().listeners().add(new PropertyListener() {
            @Override
            public void propertyUpdated(PropertyEvent event) {
                relayout();
            }
        });
        relayout();
    }

    protected void relayout(){
        ParentLayout parentLayout = resolveLayout();
        switch (parentLayout){
            case BORDER:{
                jcomponent.setLayout(new BorderLayout());
                break;
            }
            case FLOW:{
                jcomponent.setLayout(new FlowLayout());
                break;
            }
            case LINEAR:{
                ParentDirection dir = panel.constraints().get(ParentDirection.class);
                if(dir==null || dir==ParentDirection.VERTICAL) {
                    jcomponent.setLayout(new BoxLayout(jcomponent,BoxLayout.Y_AXIS));
                }else{
                    jcomponent.setLayout(new BoxLayout(jcomponent,BoxLayout.X_AXIS));
                }
                break;
            }
            case GRID:{
                jcomponent.setLayout(new GridBagLayout());
                break;
            }
            case STACK:{
                throw new UnsupportedOperationException("not supported yet");
            }
        }
//        for (AppComponent child : panel.children()) {
//
//        }
    }

    public ParentLayout resolveLayout() {
        ParentLayout pl = panel.constraints().get(ParentLayout.class);
        if(pl==null){
            //guess
            return ParentLayout.FLOW;
        }
        return pl;
    }

    public void addChild(AppComponent other, int index) {
        Component childComponent = (Component) other.peer().toolkitComponent();
        switch (resolveLayout()){
            case BORDER:{
                Anchor a = other.constraints().get(Anchor.class);
                if(a==null){
                    a=Anchor.CENTER;
                }
                switch (a){
                    case CENTER:
                    {
                        jcomponent.add(childComponent,BorderLayout.CENTER);
                        break;
                    }
                    case TOP:{
                        jcomponent.add(childComponent,BorderLayout.PAGE_START);
                        break;
                    }
                    case BOTTOM:{
                        jcomponent.add(childComponent,BorderLayout.PAGE_END);
                        break;
                    }
                    case LEFT:{
                        jcomponent.add(childComponent,BorderLayout.LINE_START);
                        break;
                    }
                    case RIGHT:{
                        jcomponent.add(childComponent,BorderLayout.LINE_END);
                        break;
                    }
                }
                break;
            }
            case FLOW:
            case LINEAR:{
                jcomponent.add(childComponent);
                break;
            }
            case GRID:{
                GridBagConstraints g = new GridBagConstraints();
                Pos pos = other.constraints().get(Pos.class);
                if(pos==null){
                    pos=new Pos(0,0);
                }
                Span span = other.constraints().get(Span.class);
                g.gridx=pos.getX();
                g.gridy=pos.getY();
                if(span!=null){
                    g.gridwidth=pos.getX();
                    g.gridheight=pos.getY();
                }
                GrowX growX = other.constraints().get(GrowX.class);
                if(growX!=null){
                    switch (growX){
                        case NEVER:{
                            g.weightx=0;
                            break;
                        }
                        case ALWAYS:{
                            g.weightx=2;
                            break;
                        }
                        case SOMETIMES:{
                            g.weightx=1;
                            break;
                        }
                    }
                }
                GrowY growY = other.constraints().get(GrowY.class);
                if(growY!=null){
                    switch (growY){
                        case NEVER:{
                            g.weighty=0;
                            break;
                        }
                        case ALWAYS:{
                            g.weighty=2;
                            break;
                        }
                        case SOMETIMES:{
                            g.weighty=1;
                            break;
                        }
                    }
                }
                Padding padding = other.constraints().get(Padding.class);
                if(padding!=null){
                    g.ipadx=Math.max((int) padding.getLeft(),(int) padding.getRight());
                    g.ipady=Math.max((int) padding.getTop(),(int) padding.getBottom());
                }
                Margin insets = other.constraints().get(Margin.class);
                if(insets!=null){
                    g.insets=new Insets((int) insets.getTop(),(int) insets.getLeft(),(int) insets.getBottom(),(int) insets.getRight());
                }
                jcomponent.add(childComponent, g);
            }
        }
    }

    @Override
    public void removeChild(AppComponent other, int index) {
        jcomponent.remove((Component) other.peer().toolkitComponent());
    }

    @Override
    public Object toolkitComponent() {
        return jcomponent;
    }

    @Override
    public Component awtComponent() {
        return (Component) toolkitComponent();
    }

    @Override
    public JComponent jcomponent() {
        return (JComponent) toolkitComponent();
    }
}
